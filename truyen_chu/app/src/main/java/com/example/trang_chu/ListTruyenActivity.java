package com.example.trang_chu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Adapter.AdapterTruyen_horizonal;
import Model.Chuong;
import Model.TheLoai;
import Model.Truyen;
import SQL.ChuongDAO;
import SQL.TacGiaDAO;
import SQL.TheLoaiDAO;
import SQL.TruyenDAO;

public class ListTruyenActivity extends AppCompatActivity {
    String DATABASE_NAME="qltruyen.db";
    ListView listView;
    TextView textView;
    AdapterTruyen_horizonal adapter;
    List<Truyen> truyenList;
    TextView textThongBao;
    ImageButton imageButtonQuit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_truyen);

        textView=findViewById(R.id.textTenTruyVan_listTruyen_act);
        listView=findViewById(R.id.listview_listTruyenAct);
        textThongBao=findViewById(R.id.textThongbao_listruyen);
        imageButtonQuit=findViewById(R.id.btn_quit_listtruyen);

        getData();
        imageButtonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ListTruyenActivity.this,InfoTruyenActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("MATRUYEN",truyenList.get(position).getMaTruyen());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void getData() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
           String loai=bundle.getString("LOAI");
            String ma=bundle.getString("MA");
            if(Objects.equals(loai, "TheLoai")){
                //title
                textView.setText(new TheLoaiDAO(this).getTheLoaiDAO("Select * from tTheLoai where MaTheLoai=?",new String[]{ma}).get(0).getTenTheLoai());
                // list truyện
                truyenList=new ArrayList<Truyen>();
                truyenList=new TruyenDAO(this).getTruyenForItemHorizonal("SELECT tTruyen.MaTruyen,TenTruyen,MaTacGia,Anh from tTruyen" +
                        " join tTruyen_TheLoai on tTruyen.MaTruyen=tTruyen_TheLoai.MaTruyen where tTruyen_TheLoai.MaTheLoai=?",new String[]{ma});
            }
            else if(Objects.equals(loai, "TacGia")){
                String matruyen=bundle.getString("MATRUYEN");
                textView.setText(new TacGiaDAO(this).getTacGia("Select * from tTacGia where MaTacGia=?",new String[]{ma}).get(0).getTenTacGia());

                truyenList=new ArrayList<Truyen>();
                truyenList=new TruyenDAO(this).getTruyenForItemHorizonal("SELECT MaTruyen,TenTruyen,MaTacGia,Anh FROM tTruyen where MaTacGia=? and MaTruyen!= '"+matruyen+"'",new String[]{ma});
            }
            else if(Objects.equals(loai, "DeCu")){
                textView.setText("Truyện Đề Cử");

                truyenList=new ArrayList<Truyen>();
                truyenList=new TruyenDAO(this).getTruyenForItemHorizonal("SELECT MaTruyen,TenTruyen,MaTacGia,Anh FROM tTruyen ORDER BY TenTruyen ASC LIMIT 10",null);

            }
            else if(Objects.equals(loai, "HoanThanh")){
                textView.setText("Truyện Hoàn Thành");

                truyenList=new ArrayList<Truyen>();
                truyenList=new TruyenDAO(this).getTruyenForItemHorizonal("SELECT MaTruyen,TenTruyen,MaTacGia,Anh FROM tTruyen where TrangThai=?",new String[]{"1"});

            }
            adapter=new AdapterTruyen_horizonal(ListTruyenActivity.this,R.layout.horizontal_listview_layout,truyenList);
            listView.setAdapter(adapter);
            if(truyenList.size()==0){
                textThongBao.setVisibility(View.VISIBLE);
            }
            else{
                textThongBao.setVisibility(View.GONE);
            }
        }
        else{
            //làm message báo lỗi không có dữ liệu
        }
    }
}