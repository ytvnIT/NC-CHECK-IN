package com.example.login.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.login.R;
import com.example.login.Retrofit.APIUtils;
import com.example.login.Retrofit.DataClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Forgotpassword extends AppCompatActivity {

    Button btnSend, btnBack;
    EditText etId;
    TextView tvError;
    SharedPreferences sharedPreferences;
    DataClient dataClient= APIUtils.getData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__forgotpassword);

        initViews();
        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        String ID = sharedPreferences.getString("ID", "");
        if( ID!=""){
            etId.setText(ID);
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back = new Intent(Activity_Forgotpassword.this, Activity_Login.class);
                startActivity(intent_back);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Id=etId.getText().toString();
                tvError.setText("");
                if(Id.trim().length()==0)
                    tvError.setText("Required");
                else{
                    tvError.setText("");
                    Call<String> callback = dataClient.reset(Id);
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String message=response.body();
                            if(message.equals("0"))
                                tvError.setText("Mã học viên không tồn tại");
                            else{
                                Intent intent = new Intent(Activity_Forgotpassword.this, Activity_ResetPassword.class);
                                intent.putExtra("ID", Id);
                                startActivity(intent);
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    public void initViews() {
        btnSend=findViewById(R.id.btn_Login);
        etId=findViewById(R.id.et_Password_rp);
        btnBack=findViewById(R.id.btn_back_rp);
        tvError = findViewById(R.id.tv_Error_login);

    }

}
