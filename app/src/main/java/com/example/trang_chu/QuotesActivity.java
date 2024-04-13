package com.example.trang_chu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import Adapter.QuotesFragment_Adapter;

public class QuotesActivity extends AppCompatActivity {
    ImageView btnQuit;
    ViewPager viewPager;
    TabLayout tabLayout;
    QuotesFragment_Adapter adapter;
    int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);
        anhXa();
        getData();
        setActions();
    }
    private void setActions() {
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            index=bundle.getInt("POSITION");
        }
        adapter=new QuotesFragment_Adapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(index);
    }

    private void anhXa() {
        btnQuit=findViewById(R.id.btn_quit_quotes);
        viewPager=findViewById(R.id.view_pager_quotes);
        tabLayout=findViewById(R.id.tabLayout_qoutes);
    }
}