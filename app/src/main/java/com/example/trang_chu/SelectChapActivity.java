package com.example.trang_chu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Model.Chuong;
import SQL.ChuongDAO;
import SQL.TruyenDAO;

public class SelectChapActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    ArrayList<String> arrayTitle_Chuong;
    ArrayList<String> array_Search_Chuong=null;
    String maTruyen;
    List<Chuong> chuongList;
    ListView listView;
    ArrayAdapter<String> adapter;
    SearchView searchView;
    TextView textTitle;
    ImageButton btnQuit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_chap);
        //ánh xạ
        listView=findViewById(R.id.listview_chuong);
        searchView=findViewById(R.id.searchview_chuong);
        textTitle=findViewById(R.id.textTenTruyVan_select_chap);
        btnQuit=findViewById(R.id.btn_quit_select_chap);
        getData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(SelectChapActivity.this,ChuongActivity.class);
                Bundle bundle=new Bundle();
                String content= adapter.getItem(position);
                String indexString = content.replaceAll("[^\\d]", ""); // Loại bỏ tất cả các ký tự không phải số
                int index = Integer.parseInt(indexString);
                bundle.putInt("POSTITION",index-1);
                bundle.putString("MATRUYEN",maTruyen);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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
        arrayTitle_Chuong=new ArrayList<>();
        if(bundle!=null){
            maTruyen=bundle.getString("MATRUYEN");
            textTitle.setText(new TruyenDAO(this).getTruyenbyMaTruyen(maTruyen).getTenTruyen());
            chuongList=new ArrayList<>();
            chuongList=new ChuongDAO(this).getChuongByMaTruyen(maTruyen);
        }
        else{
            chuongList=new ArrayList<>();
            chuongList=new ChuongDAO(this).getChuongByMaTruyen("001");

        }
        for(Chuong chuong:chuongList){
            String tenChuong=chuong.getTenChuong();
            if(tenChuong!=null && tenChuong.length()>0)
                arrayTitle_Chuong.add("Chương "+ chuong.getSoChuong()+": "+chuong.getTenChuong());
            else{
                arrayTitle_Chuong.add("Chương "+ chuong.getSoChuong());
            }
        }
        adapter=new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,arrayTitle_Chuong);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(SelectChapActivity.this);
        array_Search_Chuong=new ArrayList<>();
        array_Search_Chuong.addAll(arrayTitle_Chuong);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String query=newText.toLowerCase();
        arrayTitle_Chuong.clear();
        if (query.length() == 0) {
            arrayTitle_Chuong.addAll(array_Search_Chuong);
        } else {
            for (String title: array_Search_Chuong) {
                if (title.toLowerCase().contains(query)) {
                    arrayTitle_Chuong.add(title);
                }
            }
        }
        adapter.notifyDataSetChanged();
        return false;
    }
}