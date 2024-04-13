package com.example.trang_chu;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import SQL.copyDatabaseFromAssets;

public class MainActivity extends AppCompatActivity {
    public static boolean isDark=false;
    copyDatabaseFromAssets CoppyDataBase;
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment(this);
    DanhMucFragment danhMucFragment = new DanhMucFragment(this);
    SearchFragment searchFragment = new SearchFragment(this);
    LibraryFragment libraryFragment=new LibraryFragment(this);
    SettingFragment settingFragment=new SettingFragment(this);
    Toolbar toolbar;

    final String DATABASE_NAME = "qltruyen.db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CoppyDataBase=new copyDatabaseFromAssets(this);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.trangChu){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,homeFragment).commit();
                    return true;
                }
                else if(id==R.id.danhMuc){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,danhMucFragment).commit();
                    return true;
                }
                else if(id==R.id.timKiem){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,searchFragment).commit();
                    return true;
                }
                else if(id==R.id.keSach){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,libraryFragment).commit();
                    return true;
                }
                else if(id==R.id.caiDat){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,settingFragment).commit();
                    return true;
                }

                return false;
            }
        });


        bottomNavigationView.setSelectedItemId(R.id.trangChu);
        // Tạo một SharedPreferences object
        SharedPreferences sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);

        // Đọc chế độ đã lưu từ SharedPreferences, mặc định là chế độ sáng (false)
      /*  isDark = sharedPreferences.getBoolean("dark_mode", false);
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }*/

        if(SettingFragment.isSwitch){
            bottomNavigationView.setSelectedItemId(R.id.caiDat);
            SettingFragment.isSwitch=false;
        }

    }


}