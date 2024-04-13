package com.example.trang_chu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button btnLogin;
    TextView textView_dangki;
    ImageView btnHide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.edittext_username);
        password=findViewById(R.id.edittext_password);
        btnLogin=findViewById(R.id.btnLogin);
        textView_dangki=findViewById(R.id.textview_SignUp);
        btnHide=findViewById(R.id.btnHide_password);


        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getInputType()== InputType.TYPE_TEXT_VARIATION_PASSWORD){
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    btnHide.setImageResource(R.drawable.show);
                }
                else{
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnHide.setImageResource(R.drawable.hide);
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        textView_dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

}