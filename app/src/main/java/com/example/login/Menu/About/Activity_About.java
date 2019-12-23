package com.example.login.Menu.About;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.login.Activity_Menu;
import com.example.login.Menu.CheckIn.CheckIn;
import com.example.login.R;

public class Activity_About extends AppCompatActivity {
    Button btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        btnback = findViewById(R.id.btn_back_rp);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_About.this, Activity_Menu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_forward,R.anim.anim_back);
            }
        });
    }

//    public void onClick(View v){
//        String url = "";
//        Intent i;
//        switch (v.getId()){
//            case R.id.iv_uit:
//                url = "https://www.uit.edu.vn";
//                i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
//                break;
//            case R.id.iv_oep:
//                url = "https://www.oep.edu.vn";
//                i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
//                break;
//            case R.id.iv_ctsv:
//                url = "https://www.ctsv.edu.vn";
//                i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
//                break;
//            default:
//                break;
//        }
//    }
}
