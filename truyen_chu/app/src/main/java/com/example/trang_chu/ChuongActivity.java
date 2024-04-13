package com.example.trang_chu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import Adapter.ViewPagerAdapter_Chuong;
import Model.Chuong;
import Model.Truyen;
import SQL.ChuongDAO;
import SQL.TruyenDAO;

public class ChuongActivity extends AppCompatActivity {
    TextView txtTitleTruyen,txtTenChuong,txtNoiDungChuong;
    ViewPager2 viewPager2;
    List<Chuong> chuongList;
    String tenTruyen;
    int index;
    SeekBar seekBar;
    ImageButton imageButtonSetting;
    ImageButton btn_quit;
    int progressSeekBar=50;
    LinearLayout form_setting;
    ViewPagerAdapter_Chuong viewPagerAdapterChuong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuong);
        txtTitleTruyen=findViewById(R.id.textTenTruyen_Chuong);
        viewPager2=findViewById(R.id.view_pager2);
        btn_quit=findViewById(R.id.btn_quit_chuong);
        seekBar=findViewById(R.id.seekbar_fontSize);
        imageButtonSetting=findViewById(R.id.image_setting_fontsize);
        form_setting=findViewById(R.id.setting_form);

        getData();

        viewPagerAdapterChuong=new ViewPagerAdapter_Chuong(this,chuongList,seekBar);
        viewPager2.setAdapter(viewPagerAdapterChuong);
        viewPager2.setCurrentItem(index,false);
        viewPager2.setOffscreenPageLimit(1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressSeekBar = progress;
             //   updateFontSize();
                viewPagerAdapterChuong.updateFontSize();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Handle touch start if needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Handle touch stop if needed
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                int currentItem = viewPager2.getCurrentItem();
                viewPagerAdapterChuong.updateFontSizeNotCurrent(currentItem);
                Log.e("current: ",""+currentItem);
            }
        });
       /* viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateFontSize();
               *//* int currentItem = viewPager2.getCurrentItem();
                // Cập nhật dữ liệu cho fragment bên trái (nếu có)
                int leftItem = currentItem - 1;
                if (leftItem >= 0) {
                    ChuongFragment leftFragment = (ChuongFragment) viewPagerAdapterChuong.createFragment(leftItem);
                    leftFragment.updateData();
                }

                // Cập nhật dữ liệu cho fragment bên phải (nếu có)
                int rightItem = currentItem + 1;
                if (rightItem < viewPagerAdapterChuong.getItemCount()) {
                    ChuongFragment rightFragment = (ChuongFragment) viewPagerAdapterChuong.createFragment(rightItem);
                    rightFragment.updateData();
                }*//*
            }
        });*/


        imageButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(form_setting.getVisibility()==View.VISIBLE){
                    form_setting.setVisibility(View.GONE);
                }
                else{
                    form_setting.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String maTruyen;
        if(bundle!=null){
            index=bundle.getInt("POSTITION");
            maTruyen=bundle.getString("MATRUYEN");
        }
        else{
            maTruyen="001";
            index=0;
        }
        Truyen truyen=new TruyenDAO(this).getTruyenbyMaTruyen(maTruyen);

        tenTruyen=truyen.getTenTruyen();
        txtTitleTruyen.setText(tenTruyen);
        chuongList=truyen.getChuongList();

    }
    private void updateFontSize() {
        int currentItem = viewPager2.getCurrentItem();
        ChuongFragment currentFragment = (ChuongFragment) viewPagerAdapterChuong.createFragment(currentItem);
        currentFragment.updateFontSize();
    }
}