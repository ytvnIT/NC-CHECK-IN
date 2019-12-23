package com.example.login.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.Activity_Menu;
import com.example.login.MainActivity;
import com.example.login.R;
import com.example.login.Retrofit.APIUtils;
import com.example.login.Retrofit.DataClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class    Activity_Login extends AppCompatActivity implements View.OnClickListener{
    EditText et_user,et_password ;
    TextView tv_forgotPass;
    CheckBox cbRememberMe;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);
        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        initViews();

        if(sharedPreferences.getBoolean("CHECK", false) == true){
            et_user.setText(sharedPreferences.getString("ID", ""));
            et_password.setText(sharedPreferences.getString("PASSWORD", ""));
            cbRememberMe.setChecked(true);
        }
    }

    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.btn_back_rp:
                Intent intent_back_log = new Intent(Activity_Login.this, MainActivity.class);
                startActivity(intent_back_log);
                break;

            case R.id.btn_Login:
                String mahv = et_user.getText().toString(), password=et_password.getText().toString();
                if(mahv.trim().length()==0 || password.trim().length()==0)
                    Toast.makeText(Activity_Login.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                else {
                    getLogin(mahv, password);
                    sharedPreferences.edit().putString("ID", mahv).apply();
                }
                break;
            case R.id.tv_FogotPassword_log:
                Intent intent_forgot = new Intent(Activity_Login.this, Activity_Forgotpassword.class);
                startActivity(intent_forgot);
                break;
            default:
                break;
        }
    }

    private void getLogin(String mahv, String password){
        DataClient dataClient= APIUtils.getData();
        Call<Result> callback = dataClient.login(mahv, password );
        callback.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response != null) {
                    Result message = response.body();
//                    Log.d("BBB", message);
                    if(message.getStatus().equals("1")) {
                        rememberMe();
                        sharedPreferences.edit().putString("NAME", message.getTEN()).apply();
                        sharedPreferences.edit().putString("IMAGE", message.getANH()).apply();
                        Toast.makeText(Activity_Login.this, "Wellcome!!!", Toast.LENGTH_SHORT).show();
                        Intent intent_Menu = new Intent(Activity_Login.this, Activity_Menu.class);
                        intent_Menu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent_Menu);
                        finish();
                    }
                    else
                        Toast.makeText(Activity_Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    et_user.setText("");
                    et_password.setText("");
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }

        });
    }
    private void initViews() {
        findViewById(R.id.btn_back_rp).setOnClickListener(this);
        findViewById(R.id.btn_Login).setOnClickListener(this);
        findViewById(R.id.tv_FogotPassword_log).setOnClickListener(this);
        et_user = findViewById(R.id.et_Password_rp);
        et_password = findViewById(R.id.et_Confirm_rp);
        cbRememberMe = findViewById(R.id.cb_reme);
    }

    private void rememberMe(){
        if(cbRememberMe.isChecked()){
            sharedPreferences.edit().putString("PASSWORD", et_password.getText().toString()).apply();
            sharedPreferences.edit().putBoolean("CHECK", true).apply();
        }
        else
            sharedPreferences.edit().clear().commit();
    }

}
//btn_Login2.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//
//        }
//        });