package com.example.login;

import androidx.appcompat.app.AppCompatActivity;


import android.content.SharedPreferences;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login.Menu.About.Activity_About;
import com.example.login.Menu.CheckIn.CheckIn;
import com.example.login.Menu.Grade.Activity_Grade;
import com.example.login.Menu.Info.Activity_Info;
import com.example.login.Menu.TKB.Activity_TKB;
import com.squareup.picasso.Picasso;


public class Activity_Menu<card_checkin> extends AppCompatActivity  implements View.OnClickListener {
    SharedPreferences sharedPreferences;
    TextView tvID, tvName;
    ImageView ivAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__menu);
        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);

        initViews();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardview_checkin:
                Intent intent_cardview_checkin = new Intent(Activity_Menu.this, CheckIn.class);
                intent_cardview_checkin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent_cardview_checkin);
                break;
            case R.id.cardview_grades:
                Intent intent_cardview_grades = new Intent(Activity_Menu.this, Activity_Grade.class);
                intent_cardview_grades.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent_cardview_grades);
                break;
            case R.id.cardview_timetable:
                Intent intent_cardview_timeTable = new Intent(Activity_Menu.this, Activity_TKB.class);
                intent_cardview_timeTable.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent_cardview_timeTable);
                break;
            case R.id.cardview_infor:
                Intent cardview_infor = new Intent(Activity_Menu.this, Activity_Info.class);
                cardview_infor.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(cardview_infor);
                break;
            case R.id.cardview_tuitionfee:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
                startActivity(Intent.createChooser(intent, "Send Email"));
                break;
            case R.id.cardview_deadline:
                Intent cardview_deadline = new Intent(Activity_Menu.this, Activity_About.class);
                cardview_deadline.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(cardview_deadline);
                break;
            default:
                break;

        }
        overridePendingTransition(R.anim.anim_enter,R.anim.anim_out);
    }
    private void initViews(){
        findViewById(R.id.cardview_checkin).setOnClickListener(this);
        findViewById(R.id.cardview_grades).setOnClickListener(this);
        findViewById(R.id.cardview_deadline).setOnClickListener(this);
        findViewById(R.id.cardview_infor).setOnClickListener(this);
        findViewById(R.id.cardview_timetable).setOnClickListener(this);
        findViewById(R.id.cardview_tuitionfee).setOnClickListener(this);

        tvID = findViewById(R.id.tvStudentID);
        tvName = findViewById(R.id.tvStudentName);
        ivAvatar = findViewById(R.id.ivAvatar);
        tvName.setText(sharedPreferences.getString("NAME",""));
        tvID.setText(sharedPreferences.getString("ID",""));
        String img =sharedPreferences.getString("IMAGE","");
        String imageUri = getString(R.string.domain) + img;
        ivAvatar=  findViewById(R.id.ivAvatar);


        Picasso.get().load(imageUri)
                .placeholder(R.drawable.human)
                .error(R.drawable.human)
                .into(ivAvatar);

    }
}
