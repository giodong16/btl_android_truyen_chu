package com.example.trang_chu;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Adapter.AdapterTruyen_horizonal;
import Adapter.Adapter_Slider;
import Adapter.ListTruyen_HorizontalScrollView;
import Model.SlideModel;
import Model.TacGia;
import Model.Truyen;
import SQL.TheLoaiDAO;
import SQL.TruyenDAO;
import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    View myView;
    ListView listView;
    AdapterTruyen_horizonal adapterTruyenHorizonal;
    List<Truyen> listTruyen;
    Context myContext;
    ListTruyen_HorizontalScrollView listTruyenHorizontalScrollView_decu,listTruyen_horizontalScrollView_full;
    ListTruyen_HorizontalScrollView listTruyenHorizontalScrollView_ngontinh,listTruyen_horizontalScrollView_tutien;
    TextView textThemTruyen_decu,textThemTruyen_full;
    TextView textThemTruyen_ngontinh,textThemTruyen_tutien;

    //slider
    ViewPager viewPager_slider;
    CircleIndicator circleIndicator;
    Adapter_Slider adapterSlider;
    private Handler handler;
    private int currentPage = 0;
    private static final long TimeDelay = 3000;
    final String DATABASE_NAME = "qltruyen.db";
    List<SlideModel> slideModelList;
    public HomeFragment() {
    }
    public HomeFragment(Context myContext) {

        this.myContext=myContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(myView==null && !SettingFragment.isSwitch){

        }
        myView=inflater.inflate(R.layout.fragment_home, container, false);
        textThemTruyen_decu=myView.findViewById(R.id.textThemTruyenDeCu);
        textThemTruyen_ngontinh=myView.findViewById(R.id.textThemTruyenNgonTinh);
        textThemTruyen_tutien=myView.findViewById(R.id.textThemTruyenTuTien);
        textThemTruyen_full=myView.findViewById(R.id.textThemTruyenHoanThanh);

        listTruyenHorizontalScrollView_decu=myView.findViewById(R.id.linear_layout_decu);
        listTruyen_horizontalScrollView_full=myView.findViewById(R.id.linear_layout_hoanthanh);
        listTruyenHorizontalScrollView_ngontinh=myView.findViewById(R.id.linear_layout_ngontinh);
        listTruyen_horizontalScrollView_tutien=myView.findViewById(R.id.linear_layout_tutien);

        circleIndicator=myView.findViewById(R.id.dots_indicator);
        viewPager_slider=myView.findViewById(R.id.view_pager_slider);
        slideModelList=getListSlideModel();
        adapterSlider=new Adapter_Slider(myContext,slideModelList);
        viewPager_slider.setAdapter(adapterSlider);
        circleIndicator.setViewPager(viewPager_slider);
        adapterSlider.registerDataSetObserver(circleIndicator.getDataSetObserver());
        // Khởi tạo Handler
        handler = new Handler(Looper.getMainLooper());
        // Chạy mã tự động chuyển trang
        setTruyenDeCu();
        setTruyenFull();
        setTruyenTienHiep();
        setTruyenNgonTinh();
        return myView;
    }

    private List<SlideModel> getListSlideModel() {
        List<SlideModel> slideModelList=new ArrayList<>();
        slideModelList.add(new SlideModel(R.drawable.slide_01,"Những câu nói hay trong các tác phẩm: Phần 01"));
        slideModelList.add(new SlideModel(R.drawable.slide_02,"Những câu nói hay trong các tác phẩm: Phần 02"));
        slideModelList.add(new SlideModel(R.drawable.slide_03,"Những câu nói hay trong các tác phẩm: Phần 03"));
        slideModelList.add(new SlideModel(R.drawable.slide_04,"Những câu nói hay trong các tác phẩm: Phần 04"));

        return slideModelList;
    }
private void startAutoPager() {
    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            int currentItem = viewPager_slider.getCurrentItem();
            int totalItem = adapterSlider.getCount() - 1;
            if (currentItem < totalItem) {
                currentItem++;
                viewPager_slider.setCurrentItem(currentItem);
            } else {
                viewPager_slider.setCurrentItem(0);
            }
            // Lặp lại sau khoảng thời gian TimeDelay
            handler.postDelayed(this, TimeDelay);
        }
    }, TimeDelay);
}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hủy bỏ mã tự động chuyển trang khi Fragment bị hủy
        handler.removeCallbacksAndMessages(null);
    }
    private void setTruyenNgonTinh() {
        List<Truyen> truyenListNgonTinh=new ArrayList<>();
        String ma=new TheLoaiDAO(myContext).getMaTheLoaiDAO("select MaTheLoai from tTheLoai where tentheloai=?",new String[]{"Ngôn tình"});
       // truyenListNgonTinh=new TruyenDAO(myContext).getTruyenByTheLoai(ma);
        truyenListNgonTinh=new TruyenDAO(myContext).getTruyenForItemVertical("Select tTruyen.MaTruyen,TenTruyen,Anh from tTruyen join tTruyen_TheLoai on tTruyen.MaTruyen=tTruyen_TheLoai.MaTruyen where tTruyen_TheLoai.MaTheLoai=? LIMIT 8",new String[]{ma});

        int size=truyenListNgonTinh.size();
        if(size>8) size=8;
        for(int i=0;i<size;i++){
            Truyen truyen=truyenListNgonTinh.get(i);
            listTruyenHorizontalScrollView_ngontinh.AddItemToScrollView(truyen);
        }
        textThemTruyen_ngontinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myContext,ListTruyenActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("LOAI","TheLoai");
                bundle.putString("MA",ma);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void setTruyenTienHiep() {
        List<Truyen> truyenListTuTien=new ArrayList<>();
        String ma=new TheLoaiDAO(myContext).getMaTheLoaiDAO("select MaTheLoai from tTheLoai where tentheloai=?",new String[]{"Tiên hiệp"});
       // truyenListTuTien=new TruyenDAO(myContext).getTruyenByTheLoai(ma);
        truyenListTuTien=new TruyenDAO(myContext).getTruyenForItemVertical("Select tTruyen.MaTruyen,TenTruyen,Anh from tTruyen join tTruyen_TheLoai on tTruyen.MaTruyen=tTruyen_TheLoai.MaTruyen where tTruyen_TheLoai.MaTheLoai=? LIMIT 8",new String[]{ma});
        int size=truyenListTuTien.size();
        if(size>8) size=8;
        for(int i=0;i<size;i++){
            Truyen truyen=truyenListTuTien.get(i);
            listTruyen_horizontalScrollView_tutien.AddItemToScrollView(truyen);
        }
        textThemTruyen_tutien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myContext,ListTruyenActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("LOAI","TheLoai");
                bundle.putString("MA",ma);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void setTruyenFull() {
        List<Truyen> truyenListFull=new ArrayList<>();
        truyenListFull=new TruyenDAO(myContext).getTruyenForItemVertical("Select MaTruyen,TenTruyen,Anh from tTruyen where TrangThai=? LIMIT 8",new String[]{"1"});
        int size=truyenListFull.size();
        if(size>8) size=8;
        for(int i=0;i<size;i++){
            Truyen truyen=truyenListFull.get(i);
            listTruyen_horizontalScrollView_full.AddItemToScrollView(truyen);
        }
        textThemTruyen_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myContext,ListTruyenActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("LOAI","HoanThanh");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void setTruyenDeCu() {
        List<Truyen> truyenListDeCu=new ArrayList<>();
        truyenListDeCu=new TruyenDAO(myContext).getTruyenForItemVertical("Select MaTruyen,TenTruyen,Anh from tTruyen order by TenTruyen DESC LIMIT 8",null);
        int size=truyenListDeCu.size();
        if(size>8) size=8;
        for(int i=0;i<size;i++){
            Truyen truyen=truyenListDeCu.get(i);
            listTruyenHorizontalScrollView_decu.AddItemToScrollView(truyen);
        }
        textThemTruyen_decu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myContext,ListTruyenActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("LOAI","DeCu");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        // Bắt đầu mã tự động chuyển trang khi Fragment được hiển thị
        startAutoPager();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Dừng mã tự động chuyển trang khi Fragment không còn hiển thị
        handler.removeCallbacksAndMessages(null);
    }

}