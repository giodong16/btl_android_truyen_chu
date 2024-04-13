package com.example.trang_chu;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import Adapter.ViewPagerAdapter_Chuong;
import Model.Chuong;


public class ChuongFragment extends Fragment {
    View myView;
    TextView textTenChuong,textNoiDung;
    Chuong chuong;
    ScrollView scrollView;
    FloatingActionButton floatingActionButton;
    SeekBar seekBarFontSize;
    public ChuongFragment(){
    }
    public  ChuongFragment(Chuong chuong,SeekBar seekBar){
        this.chuong=chuong;
        this.seekBarFontSize=(seekBar);

    }
    public static ChuongFragment newInstance(Chuong chuong, SeekBar seekBar) {
        ChuongFragment fragment = new ChuongFragment(chuong, seekBar);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e("Tạo  ","create");
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView= inflater.inflate(R.layout.fragment_chuong, container, false);
        textTenChuong=myView.findViewById(R.id.texttenChuong_Chuong);
        textNoiDung=myView.findViewById(R.id.textNoiDungChuong_Chuong);
        floatingActionButton=myView.findViewById(R.id.floatingBackToTop);
        scrollView=myView.findViewById(R.id.srollview_chuongfragment);
        floatingActionButton.hide();

        String tenChuong="Chương "+chuong.getSoChuong();
        if(chuong.getTenChuong()!=null &&chuong.getTenChuong().length()>0){
            tenChuong+=": "+chuong.getTenChuong();
        }
        textTenChuong.setText(tenChuong);

        textNoiDung.setText(chuong.getNoiDung());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0,0);
            }
        });
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY>oldScrollY){
                    floatingActionButton.hide();
                }
                else if(scrollY<oldScrollY){
                    floatingActionButton.show();
                }
                if(scrollY==0) floatingActionButton.hide();
            }
        });

        return myView;
    }
    public void updateFontSize() {
        if (textNoiDung != null && seekBarFontSize != null) {
            float fontSize = (float) seekBarFontSize.getProgress() / 10 + 10;
            textNoiDung.setTextSize(fontSize);
        }
    }


}