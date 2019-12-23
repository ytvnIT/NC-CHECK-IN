package com.example.login.Menu.CheckIn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;


import android.Manifest;
import android.content.Context;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.Activity_Menu;
import com.example.login.Database.Database;
import com.example.login.GPS.GPS;
import com.example.login.Menu.Grade.Activity_Grade;
import com.example.login.R;
import com.example.login.Retrofit.APIUtils;
import com.example.login.Retrofit.DataClient;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckIn extends AppCompatActivity {
    private Database database;
    private SharedPreferences sharedPreferences;
    private IntentIntegrator intentIntegrator;
//    private static final double LATITUDE = 10.881712;  //KTX
//    private static final double LONGTITUDE = 106.782122; //KTX
        private static  final double LATITUDE = 10.869486; //UIT
    private static  final double LONGTITUDE = 106.803304; //UIT
    private String ID;
    private String MAMH;
    private static final int REQUEST_LOCATION = 1;
    DataClient dataClient = APIUtils.getData();
    Button btnCheckIn, btnHome;
    TextView tvError;
    GPS gps;
    ArrayList<CheckInList> checkInLists;
    AdapterList adapter;
    ListView lvCheckInList;

    @RequiresApi( api = Build.VERSION_CODES.M )
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        initViews();

        intentIntegrator = new IntentIntegrator(this);
        database.QueryData("CREATE TABLE IF NOT EXISTS DiemDanh(Id INTEGER PRIMARY KEY AUTOINCREMENT, MAMH VARCHAR(10), time datetime, MAHV VARCHAR(10))");

        getDatabase(); // Lấy dự liệu điểm danh để hiển thị lên listview
        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!gps.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    gps.OnGPS();
                }
                else {
                    gps.getLocation();
                }
                if( getInitStatus() && checkLocation()==1 &&gps.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {// Nếu đã mở location và accept access thì mở camara
                    tvError.setText("");
                    intentIntegrator.initiateScan();
                }
                else if(checkLocation() == 0){
                    animation_fail();
                    tvError.setText(getString(R.string.location_checkIn_error));
                }

            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckIn.this, Activity_Menu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_forward,R.anim.anim_back);
            }
        });
    }


    //Kiểm tra trạng thái trả về khi yêu cầu location permission
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sharedPreferences.edit().putBoolean("STATUS", true).apply();
                tvError.setText("");
                if( checkLocation() ==1 )
                    intentIntegrator.initiateScan();

            } else {
                tvError.setText(getString(R.string.gps_error));
                sharedPreferences.edit().putBoolean("STATUS", false).apply();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                String re = result.getContents();
                MAMH = re.split("@")[1];

                api_checkIn(re);// gửi token (re) lên server để điểm danh
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getDatabase(){
        Cursor data = database.GetData("SELECT * FROM DiemDanh ORDER BY time DESC");
        if(data.getCount()==0) {//Nếu SQLite chưa có dữ liệu, thì request lên server để lấy về
            api_getDatabase();
            data = database.GetData("SELECT * FROM DiemDanh");
        }
        final Cursor data2 = data;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(data2.moveToFirst())
                {
                    do{
                        String mamh = data2.getString(1);
                        String time = data2.getString(2);
                        String mahv = data2.getString(3);
                        checkInLists.add(new CheckInList(mamh,time, mahv));
                    }while(data2.moveToNext());
                }
                adapter.notifyDataSetChanged();
            }
        }, 1000);
    }

    private void insertSQLite(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String time =  sdf.format(cal.getTime());

        database.QueryData("INSERT INTO DiemDanh VALUES(null, '" + MAMH + "', '" + time +"', '" + ID +"')" );
        checkInLists.add(0,new CheckInList(MAMH,time, ID));
        adapter.notifyDataSetChanged();
    }

    private void api_getDatabase(){
        Call<ArrayList<CheckInList>> callback = dataClient.getCheckInInfor(ID);
        callback.enqueue(new Callback<ArrayList<CheckInList>>() {
            @Override
            public void onResponse(Call<ArrayList<CheckInList>> call, Response<ArrayList<CheckInList>> response) {
                ArrayList<CheckInList> message = response.body();
                database.insert(message);// Sau khi lấy API điểm danh, tiến hành lưu vào SQLITE
            }
            @Override
            public void onFailure(Call<ArrayList<CheckInList>> call, Throwable t) {
                alert(getString(R.string.error));
            }
        });
    }

    private void api_checkIn(String TOKEN){
        Call<String> callback = dataClient.checkIn(TOKEN, ID,getMacAddr());
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String message = response.body();
                if(message.equals("1")){
                    animation_success();
                    alert(getString(R.string.checkIn_success));
                    insertSQLite();
                }
                else if (message.equals("2")){
                    animation_fail();
                    alert(getString(R.string.checkIned));
                }
                else if (message.equals("3")){
                    animation_fail();
                    alert(getString(R.string.timeout));
                }
                else if (message.equals("4")){
                    animation_fail();
                    alert(getString(R.string.wrong));
                }
                else {
                    animation_fail();
                    alert(getString(R.string.dif));
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                alert(getString(R.string.error));
            }
        });
    }

    private int checkLocation(){
//        return 1;
        double lat2 =gps.getLatitude();
        double lon2 =gps.getLongitude();
        if(lat2 ==0.0)
            return 2;
        double R = 6372.8; // Earth Radius in Kilometers
        double dLat = Deg2Rad(lat2-LATITUDE);
        double dLon = Deg2Rad(lon2-LONGTITUDE);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Deg2Rad(LATITUDE)) * Math.cos(Deg2Rad(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
//         Return Distance in Kilometers
        if(d<0.5)
            return 1;
         return 0;//nằm ngoài khu vực điểm danh
    }

    private double Deg2Rad(double deg){
        return deg * Math.PI / 180;
    }

    public void alert(String measage){
        AlertDialog.Builder alerDialog = new AlertDialog.Builder(this);
        alerDialog.setTitle(getString(R.string.title_alert_checkIn));
        alerDialog.setIcon(R.drawable.nc);
        alerDialog.setMessage(measage);
        alerDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alerDialog.show();

    }

    private boolean getInitStatus(){
        return sharedPreferences.getBoolean("STATUS", false);
    }
    private void animation_fail(){
        final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
        tg.startTone(ToneGenerator.TONE_SUP_ERROR, 1000);

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(800); // for 500 ms
        }
    }
    private void animation_success(){
        final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
        tg.startTone(ToneGenerator.TONE_PROP_BEEP, 2000);

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(300); // for 500 ms
        }
    }
    public  String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }
    private  void initViews(){
        btnCheckIn = findViewById(R.id.btnCheckIn);
        btnHome = findViewById(R.id.btn_home);
        tvError = findViewById(R.id.tvError);
        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        gps = new GPS(this, CheckIn.this, tvError );
        lvCheckInList = findViewById(R.id.lvList);
        checkInLists = new ArrayList<>();
        adapter = new AdapterList(this, R.layout.check_in_list, checkInLists);
        lvCheckInList.setAdapter(adapter);
        ID = sharedPreferences.getString("ID", "");
        database = new Database(this, "diemdanh.sqlite", null, 1);
    }
}
