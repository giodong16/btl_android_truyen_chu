package com.example.trang_chu;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;


public class SettingFragment extends Fragment {
    TextView textAboutUs;
    public static boolean isSwitch=false;
    Button btnSang,btnToi;
    Context myContext;
    View myView;


    public SettingFragment() {
        // Required empty public constructor
    }
    public SettingFragment(Context context) {
        // Required empty public constructor
        this.myContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(myView==null ){
           myView= inflater.inflate(R.layout.fragment_setting, container, false);
            @SuppressLint("UseSwitchCompatOrMaterialCode")
            Switch switch1 =myView.findViewById(R.id.switchTheme);
            textAboutUs=myView.findViewById(R.id.textAbouUs);

            if(MainActivity.isDark){
                switch1.setChecked(true);
            }
            else switch1.setChecked(false);
            switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        MainActivity.isDark=true;
                        isSwitch=true;

                    }
                    else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        MainActivity.isDark=false;
                        isSwitch=true;

                    }
                    saveDarkModeSetting(MainActivity.isDark);
                }
            });
            textAboutUs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(myContext,AboutActivity.class);
                    startActivity(intent);
                }
            });

        }
        return myView;
    }
    // Lưu cài đặt chế độ sáng/tối vào SharedPreferences
    private void saveDarkModeSetting(boolean isDarkMode) {
        SharedPreferences sharedPreferences = myContext.getSharedPreferences("AppSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("dark_mode", isDarkMode);
        editor.apply();
    }
}