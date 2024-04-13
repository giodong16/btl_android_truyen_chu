package com.example.trang_chu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import Model.TaiKhoan;
import SQL.TaiKhoanDAO;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText username;
    TextInputEditText password;
    Button btnLogin;
    TextView textView_dangki;
    TextInputLayout usernameContainer,password1Container;
    TaiKhoan taiKhoan=new TaiKhoan();
    public static final String SHARED_PREFS_NAME = "login";
    public static final String USER_ID = "user_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        if (isLoggedIn()) {
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            startActivity(intent);
//            finish();
//            return;
//        }
        anhXa();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText()!=null && username.getText().length()!=0 && password.getText()!=null && password.getText().length()!=0){
                    String tendangnhap=username.getText().toString();
                    String matkhau=password.getText().toString();
                    TaiKhoanDAO taiKhoanDAO=new TaiKhoanDAO(LoginActivity.this);
                    taiKhoan=taiKhoanDAO.isTaiKhoanByUserNameAndPassword(tendangnhap,matkhau);
                    if(taiKhoan!=null){
                        saveLoggedInID(taiKhoan.getId());
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        String mes="Tên đăng nhập hoặc mật khẩu sai";
                        MyDialog.showSuccessDialog(LoginActivity.this,mes,MyDialog.ERROR);
                    }
                }
                else{
                    String mes="";
                    if(username.getText()==null || username.getText().length()==0)
                        mes="Tên đăng nhập còn trống!";
                    if(password.getText()==null|| password.getText().length()==0)
                        mes+="\nMật khẩu còn trống!";

                    MyDialog.showSuccessDialog(LoginActivity.this,mes,MyDialog.ERROR);
                    //showDialog(mes);
                }

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

    private void anhXa() {
        username=findViewById(R.id.edittext_username);
        password=findViewById(R.id.edittext_password);
        btnLogin=findViewById(R.id.btnLogin);
        textView_dangki=findViewById(R.id.textview_SignUp);
        usernameContainer=findViewById(R.id.username_container_login);
        password1Container=findViewById(R.id.password_container_login);
    }
    private void showDialog(String mes){
        AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(mes);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }

        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    private boolean isLoggedIn() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.contains(USER_ID);
    }

    private void saveLoggedInID(int user_id) {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(USER_ID, user_id);
        editor.apply();
    }

}