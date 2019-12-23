package com.example.login.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.Activity_Menu;
import com.example.login.R;
import com.example.login.Retrofit.APIUtils;
import com.example.login.Retrofit.DataClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_ResetPassword extends AppCompatActivity {
    private  SharedPreferences sharedPreferences;
    DataClient dataClient= APIUtils.getData();
    Button btnReset, btnBack, btnResend;
    EditText etPass, etNewPass, etOTP;
    TextView tvError1, tvError2, tvError3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__reset_password);
        initViews();

        Intent intent=getIntent();
        final String ID =intent.getStringExtra("ID");

        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api_resend(ID);
                btnBack.setVisibility(View.GONE);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back = new Intent(Activity_ResetPassword.this, Activity_Forgotpassword.class);
                startActivity(intent_back);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass=etPass.getText().toString();
                String confirmPass = etNewPass.getText().toString();
                String OTP = etOTP.getText().toString();
                if(CheckError(pass, confirmPass, OTP)==0){
                    api_reset(ID, pass, OTP);
                }
            }
        });

    }

    private void api_reset(final String ID, final String pass, String OTP){
        clear();
        Call<String> callback = dataClient.setPassword(ID, pass, OTP );
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String message = response.body();
                if(message.equals("0")) {
                    tvError3.setText("Invalid OTP");
                    btnResend.setVisibility(View.VISIBLE);
                }
                else{
                    sharedPreferences.edit().putString("ID", ID).apply();
                    sharedPreferences.edit().putString("PASSWORD", pass).apply();
                    Toast.makeText(Activity_ResetPassword.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Activity_ResetPassword.this, Activity_Menu.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
//                    finish();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        clear();
    }

    private void api_resend(final String Id ){
        Call<String> callback = dataClient.reset(Id);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String message=response.body();
                    Toast.makeText(Activity_ResetPassword.this, "Đã gửi OTP", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    int CheckError(String pass, String confirm, String OTP){
        int Error1=0,  Error2 = 0,  Error3 = 0;
        clear();
        if(pass.trim().length()==0 ) {
            tvError1.setText("Require");
            Error1 = 1;
        }
        if(confirm.trim().length()==0 ) {
            tvError2.setText("Require");
            Error2 = 1;
        }
        if(OTP.trim().length()==0 ) {
            tvError3.setText("Require");
            Error3 = 1;
        }
        if(!pass.equals(confirm) && Error2==0 ){
            tvError2.setText("Confirm not match");
            Error3=1;
        }
        return Error1 +Error2 + Error3;
    }

    void clear(){
        tvError1.setText(""); tvError2.setText(""); tvError3.setText("");
    }

    private void initViews(){
        btnBack = findViewById(R.id.btn_back_rp);
        btnReset = findViewById(R.id.btn_Login);
        btnResend = findViewById(R.id.btn_ResendOTP);
        etPass = findViewById(R.id.et_Password_rp);
        etNewPass = findViewById(R.id.et_Confirm_rp);
        etOTP = findViewById(R.id.et_OTP);
        tvError1 = findViewById(R.id.tvError1);
        tvError2 = findViewById(R.id.tvError2);
        tvError3 = findViewById(R.id.tvError3);

        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
    }
}