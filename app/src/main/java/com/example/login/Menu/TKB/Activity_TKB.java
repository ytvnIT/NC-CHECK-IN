package com.example.login.Menu.TKB;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.login.Activity_Menu;
import com.example.login.MainActivity;
import com.example.login.Menu.Grade.Activity_Grade;
import com.example.login.R;
import com.example.login.Retrofit.APIUtils;
import com.example.login.Retrofit.DataClient;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_TKB extends AppCompatActivity {
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn;
    ImageView imageView;
    Button btn8;
    List<TKB> tkbs ;
    public DB_TKB db_tkb;
    private SharedPreferences sharedPreferences;
    private  String ID ;
    private  String MAMH;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tkb);
        //get current date to show the timetable
        Calendar c = Calendar.getInstance();
        final int day = c.get(Calendar.DAY_OF_WEEK);
        //Toast.makeText(this, day+"", Toast.LENGTH_SHORT).show();

        //Khoiwr tao view
        Init();
        //

        db_tkb = new DB_TKB(getApplicationContext());

        //db_tkb.clearTable();

        db_tkb.clearTable();


        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        ID = sharedPreferences.getString("ID", "");


        DataClient dataClient = APIUtils.getData();
        Call<List<TKB>> call = dataClient.getTKB(ID);
        call.enqueue(new Callback<List<TKB>>() {
            @Override
            public void onResponse(Call<List<TKB>> call, Response<List<TKB>> response) {
                tkbs = (List<TKB>) response.body();
                //Toast.makeText(Activity_TKB.this, "Hello"+ tkbs, Toast.LENGTH_LONG).show();
                for(int i = 0 ; i < tkbs.size();i++){
                    TKB tkb = new TKB();
                    tkb.setId(tkbs.get(i).getId());
                    tkb.setMamh(tkbs.get(i).getMamh());
                    tkb.setName(tkbs.get(i).getName());
                    tkb.setTiet(tkbs.get(i).getTiet());
                    tkb.setPhong(tkbs.get(i).getPhong());
                    db_tkb.addTKB(tkb);
                }
            }
            @Override
            public void onFailure(Call<List<TKB>> call, Throwable t) {
                Toast.makeText(Activity_TKB.this, "FAIL", Toast.LENGTH_SHORT).show();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                OnClick(day);
            }
        }, 1000);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_cardview_checkin = new Intent(Activity_TKB.this, Activity_Menu.class);
                startActivity(intent_cardview_checkin);
                overridePendingTransition(R.anim.anim_forward,R.anim.anim_back);
            }
        });


    }
    public void AddFragment(View view){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentTKB fragmentTKB = null;
        List<TKB> t =null;
        int[] colors = {Color.parseColor("#2193b0"),Color.parseColor("#6dd5ed")};
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.RIGHT_LEFT, colors);
        switch (view.getId()){
            case R.id.btn_2:
                fragmentTKB = new FragmentTKB(2);
                changeButton();
                btn2.setBackground(gd);
                t = db_tkb.getTKB(2);
                Relax(t);
                break;
            case R.id.btn_3:
                fragmentTKB = new FragmentTKB(3);
                changeButton();
                btn3.setBackground(gd);
                t = db_tkb.getTKB(3);
                Relax(t);
                break;
            case R.id.btn_4:
                fragmentTKB = new FragmentTKB(4);
                changeButton();
                btn4.setBackground(gd);
                t = db_tkb.getTKB(4);
                Relax(t);
                break;
            case R.id.btn_5:
                fragmentTKB = new FragmentTKB(5);
                changeButton();
                btn5.setBackground(gd);
                t = db_tkb.getTKB(5);
                Relax(t);
                break;
            case R.id.btn_6:
                fragmentTKB = new FragmentTKB(6);
                changeButton();
                btn6.setBackground(gd);
                t = db_tkb.getTKB(6);
                Relax(t);
                break;
            case R.id.btn_7:
                fragmentTKB = new FragmentTKB(7);
                changeButton();
                btn7.setBackground(gd);
                t = db_tkb.getTKB(7);
                Relax(t);
                break;
        }
        fragmentTransaction.replace(R.id.frameContent,fragmentTKB);
        fragmentTransaction.commit();
    }
    public void changeButton(){
        btn2.setBackgroundColor(Color.WHITE);
        btn3.setBackgroundColor(Color.WHITE);
        btn4.setBackgroundColor(Color.WHITE);
        btn5.setBackgroundColor(Color.WHITE);
        btn6.setBackgroundColor(Color.WHITE);
        btn7.setBackgroundColor(Color.WHITE);
    }
    public void Relax(List<TKB> t){
        if(t.size()>0){
            imageView.setVisibility(View.INVISIBLE);
        }else{
            imageView.setVisibility(View.VISIBLE);
        }
    }
    public void OnClick(int day) {
        switch (day){
            case 2:
                btn2.performClick();
                break;
            case 3:
                btn3.callOnClick();
                break;
            case 4:
                btn4.callOnClick();
                break;
            case 5:
                btn5.callOnClick();
                break;
            case 6:
                btn6.callOnClick();
                break;
            case 7:
                btn7.callOnClick();
                break;
//            case 8:
//                btn8.callOnClick();
//                break;
        }
    }
    public void Init(){
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);
        btn4 = (Button) findViewById(R.id.btn_4);
        btn5 = (Button) findViewById(R.id.btn_5);
        btn6 = (Button) findViewById(R.id.btn_6);
        btn7 = (Button) findViewById(R.id.btn_7);
        btn = (Button) findViewById(R.id.btn_home);
        imageView = (ImageView)findViewById(R.id.relax);
        //btn8 = (Button) findViewById(R.id.btn_8);
    }
}
