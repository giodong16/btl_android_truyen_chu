package com.example.trang_chu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import SQL.TaiKhoanDAO;
import SQL.copyDatabaseFromAssets;

public class SplashClass extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        copyDatabaseFromAssets CoppyDataBase = new copyDatabaseFromAssets(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLoggedIn()) {
                    Intent intent = new Intent(SplashClass.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashClass.this, LoginActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                finish();
            }
        }, 2000);
    }
    private boolean isLoggedIn() {
        SharedPreferences prefs = getSharedPreferences(LoginActivity.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        int account_id=prefs.getInt(LoginActivity.USER_ID,0);
        return prefs.contains(LoginActivity.USER_ID) && account_id!=0;
    }
}
