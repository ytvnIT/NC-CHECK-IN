package com.example.login.Menu.Info;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.Activity_Menu;
import com.example.login.MainActivity;
import com.example.login.Menu.Grade.Activity_Grade;
import com.example.login.R;
import com.example.login.Retrofit.APIUtils;
import com.example.login.Retrofit.DataClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Info extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private  String ID ;
    TextView tvName, tvMssv, tvMail, tvDate, tvSex, tvDiachi, tvLop;
    ImageView ivSex;
    ImageView ivAvatar;
    String sex;
    Button btnBack, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Init();
        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        ID = sharedPreferences.getString("ID", "");
        String img =sharedPreferences.getString("IMAGE","");
        String imageUri = getString(R.string.domain) + img;
        ivAvatar=  findViewById(R.id.ivAvatar);

        tvName.setText(sharedPreferences.getString("NAME",""));
        Picasso.get().load(imageUri)
                .placeholder(R.drawable.book)
                .error(R.drawable.book)
                .into(ivAvatar);
        tvMssv.setText(ID.toString());
        DataClient dataClient = APIUtils.getData();
        Call<List<Info>> call = dataClient.getInfo(ID);
        call.enqueue(new Callback<List<Info>>() {
            @Override
            public void onResponse(Call<List<Info>> call, Response<List<Info>> response) {
                List<Info> info =  response.body();
                tvMail.setText(info.get(0).getEMAIL());
                tvDate.setText(info.get(0).getNGSINH());
                tvDiachi.setText(info.get(0).getNOISINH());
                tvLop.setText(info.get(0).getMALOP());
                tvSex.setText(info.get(0).getGIOITINH());
                sex = info.get(0).getGIOITINH();
                if (sex.equals("Nam")){
                    ivSex.setBackgroundResource(R.drawable.icon_male);
                }
            }

            @Override
            public void onFailure(Call<List<Info>> call, Throwable t) {
                Toast.makeText(Activity_Info.this,  "FAIL", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_cardview_checkin = new Intent(Activity_Info.this, Activity_Menu.class);
                startActivity(intent_cardview_checkin);
                overridePendingTransition(R.anim.anim_forward,R.anim.anim_back);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert("Are you want to logout?");

            }
        });


    }
    public void Init(){
        tvName = findViewById(R.id.tv_name);
        tvMssv = findViewById(R.id.tv_mssv);
        tvMail = findViewById(R.id.tv_mail);
        tvDate = findViewById(R.id.tv_ngsinh);
        tvSex = findViewById(R.id.tv_sex);
        tvDiachi = findViewById(R.id.tv_diachi);
        tvLop = findViewById(R.id.tv_lop);
        ivSex =  findViewById(R.id.iv_sex);
        ivAvatar =  findViewById(R.id.ivAvatar);
        btnBack = findViewById(R.id.btnBackInfo);
        btnLogout = findViewById(R.id.btn_logout);
    }

    public void alert(String measage){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(getString(R.string.title_alert_checkIn));
        builder1.setIcon(R.drawable.nc);
        builder1.setMessage(measage);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        sharedPreferences.edit().clear().commit();
                        sharedPreferences.edit().remove("PASSWORD").commit();
                        sharedPreferences.edit().remove("ID").commit();
                        Intent intent = new Intent(Activity_Info.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_forward,R.anim.anim_back);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

}
