package com.example.trang_chu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Past_01_QuotesFragment extends Fragment {

    ScrollView scrollView;
    FloatingActionButton floatingActionButton;
    View myView;
    int index;
    TextView textView;
    public Past_01_QuotesFragment() {
        // Required empty public constructor
    }
    public Past_01_QuotesFragment(int index) {
       this.index=index;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView=inflater.inflate(R.layout.fragment_past_01__quotes, container, false);
        floatingActionButton=myView.findViewById(R.id.floatingBackToTop_quotes_01);
        scrollView=myView.findViewById(R.id.srollview_quotes_01);
        textView=myView.findViewById(R.id.textview_quotes_1);
        String content="";
        if(index==0){
            content=getResources().getString(R.string.part_01_quotes);
        }
        else if(index==1){
            content=getResources().getString(R.string.part_02_quotes);
        }
        if(index==2){
            content=getResources().getString(R.string.part_03_quotes);
        }
        if(index==3){
            content=getResources().getString(R.string.part_04_quotes);
        }
        textView.setText(content);
        floatingActionButton.hide();
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
}