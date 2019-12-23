package com.example.login.Menu.Grade;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.Activity_Menu;
import com.example.login.MainActivity;
import com.example.login.Menu.CheckIn.CheckIn;
import com.example.login.Menu.TKB.TKB;
import com.example.login.R;
import com.example.login.Retrofit.APIUtils;
import com.example.login.Retrofit.DataClient;


import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;

import android.view.WindowManager;
import android.view.Display;
import android.graphics.Point;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Grade extends AppCompatActivity {
    private static int SCREEN_HEIGHT;
    private static int SCREEN_WIDTH;
    private SharedPreferences sharedPreferences;
    private  String ID ;
    List<Grade> gradeList ;
    ArrayList<Grade> arrayList = new ArrayList<>();
    LinearLayout table;
    TableLayout tableLayout;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        final WebView wv = findViewById(R.id.wvGrade);
        wv.getSettings().setDomStorageEnabled(true);
            DataClient dataClient = APIUtils.getData();
            Call<HTML> callback = dataClient.getGrade("17520001");
            callback.enqueue(new Callback<HTML>() {
                @Override
                public void onResponse(Call<HTML> call, Response<HTML> response) {
                    HTML html = response.body();
                    wv.loadData(html.getHtml(), "text/html", "UTF-8");

                }
                @Override
                public void onFailure(Call<HTML> call, Throwable t) {
                    Toast.makeText(Activity_Grade.this, "loi", Toast.LENGTH_SHORT).show();
                }
            });
        

//        table = (LinearLayout) findViewById(R.id.table);
        btn = (Button) findViewById(R.id.btn_home);
//        tableLayout = new TableLayout(this);
//
//
//        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
//        ID = sharedPreferences.getString("ID", "");
//        //Toast.makeText(this, ID+"", Toast.LENGTH_SHORT).show();
//
//        DataClient dataClient = APIUtils.getData();
//        Call<List<Grade>> call = dataClient.getGrade(ID);
//        call.enqueue(new Callback<List<Grade>>() {
//            @Override
//            public void onResponse(Call<List<Grade>> call, Response<List<Grade>> response) {
//                gradeList = (List<Grade>)response.body();
//                for(int i = 0 ; i< gradeList.size();i++){
//                    Grade grade = new Grade();
//                    grade.setMamh(gradeList.get(i).getMamh());
//                    grade.setTclt(gradeList.get(i).getTclt());
//                    grade.setTcth(gradeList.get(i).getTcth());
//                    grade.setQt(gradeList.get(i).getQt());
//                    grade.setTh(gradeList.get(i).getTh());
//                    grade.setGk(gradeList.get(i).getGk());
//                    grade.setCk(gradeList.get(i).getCk());
//                    grade.setTb(gradeList.get(i).getTb());
//                    arrayList.add(grade);
//                }
//                //Toast.makeText(Activity_Grade.this, arrayList+"", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<List<Grade>> call, Throwable t) {
//                Toast.makeText(Activity_Grade.this, "FAIL", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_cardview_checkin = new Intent(Activity_Grade.this, Activity_Menu.class);
                startActivity(intent_cardview_checkin);
                overridePendingTransition(R.anim.anim_forward,R.anim.anim_back);
            }
        });
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                displayTable(arrayList);
//            }
//        }, 1000);
//        //displayTable(arrayList);

    }

    private void getScreenDimension(){
        WindowManager wm= (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        SCREEN_WIDTH= size.x;
        SCREEN_HEIGHT = size.y;
    }

    public void displayTable(ArrayList<Grade> arrayList) {
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
        for (int i = 0 ; i < arrayList.size() ; i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
            TextView view = new TextView(this);
            TextView view1 = new TextView(this);
            TextView view2 = new TextView(this);
            TextView view3 = new TextView(this);
            TextView view4 = new TextView(this);
            TextView view5 = new TextView(this);
            TextView view6 = new TextView(this);
            view.setText(arrayList.get(i).getMamh());
            view1.setText(arrayList.get(i).getTclt()+arrayList.get(i).getTcth()+"");
            view2.setText(arrayList.get(i).getQt()+"");
            view3.setText(arrayList.get(i).getTh()+"");
            view4.setText(arrayList.get(i).getGk()+"");
            view5.setText(arrayList.get(i).getCk()+"");
            view6.setText(arrayList.get(i).getTb()+"");
            view.setBackgroundResource(R.drawable.border_table);
            view1.setBackgroundResource(R.drawable.border_table);
            view2.setBackgroundResource(R.drawable.border_table);
            view3.setBackgroundResource(R.drawable.border_table);
            view4.setBackgroundResource(R.drawable.border_table);
            view5.setBackgroundResource(R.drawable.border_table);
            view6.setBackgroundResource(R.drawable.border_table);

            //A9Pro
            view.setWidth(146);
            view1.setWidth(146);
            view2.setWidth(96);
            view3.setWidth(96);
            view4.setWidth(96);
            view5.setWidth(95);
            view6.setWidth(94);
//            view.setWidth(260);
//            view1.setWidth(180);
//            view2.setWidth(128);
//            view3.setWidth(128);
//            view4.setWidth(128);
//            view5.setWidth(128);
//            view6.setWidth(128);
            view.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            view1.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            view2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            view3.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            view4.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            view5.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            view6.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            Toast.makeText(this, SCREEN_WIDTH/5+"", Toast.LENGTH_SHORT).show();
            //view.setLayoutParams(new TableRow.LayoutParams(SCREEN_WIDTH/5,TableRow.LayoutParams.WRAP_CONTENT));
            //view1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT));

            tableRow.addView(view);
            tableRow.addView(view1);
            tableRow.addView(view2);
            tableRow.addView(view3);
            tableRow.addView(view4);
            tableRow.addView(view5);
            tableRow.addView(view6);
            tableLayout.addView(tableRow);
        }
        table.addView(tableLayout);

    }
}
