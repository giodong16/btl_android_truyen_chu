package com.example.trang_chu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Adapter.AdapterTruyen_horizonal;
import Model.TacGia;
import Model.Truyen;
import SQL.TacGiaDAO;
import SQL.TruyenDAO;


public class SearchFragment extends Fragment  {
    SearchView searchView;
    List<Truyen> truyenList,truyenListforSearch;
    AdapterTruyen_horizonal adapter;
    ListView listView;
    View myView;
    Context myContext;
    TextView textViewThongBao;
    public SearchFragment() {
    }
    public SearchFragment(Context context) {
       this.myContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(myView==null && !SettingFragment.isSwitch){
            myView= inflater.inflate(R.layout.fragment_search, container, false);
            textViewThongBao=myView.findViewById(R.id.textThongbao_searchFragment);
            searchView=myView.findViewById(R.id.search_truyen);
            listView=myView.findViewById(R.id.listview_truyenSearch);
            truyenList=new ArrayList<>();
            truyenListforSearch=new ArrayList<>();
            adapter=new AdapterTruyen_horizonal(myContext,R.layout.horizontal_listview_layout,truyenList);
            listView.setAdapter(adapter);
            LoadDataAsyncTask task = new LoadDataAsyncTask();
            task.execute();

           /* if(MainActivity.isDark){
                *//*int backgroundColor = getContext().getResources().getColor(R.color.darklv2);
                searchView.setBackgroundColor(backgroundColor);*//*
                Drawable drawable = myContext.getDrawable(R.drawable.style_searchview_night);

                searchView.setBackground(drawable);
            }*/

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(truyenList.size()>0){
                        Intent intent=new Intent(myContext,InfoTruyenActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("MATRUYEN",truyenList.get(position).getMaTruyen());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                }
            });
            if(truyenList==null|| truyenList.size()==0){
                textViewThongBao.setText("“Nếu bạn không thích đọc, nghĩa là bạn chưa tìm được cuốn sách phù hợp.”");
                textViewThongBao.setVisibility(View.VISIBLE);
            }
            else{
                textViewThongBao.setVisibility(View.GONE);
            }
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    truyenList.clear();
                    adapter.notifyDataSetChanged();
                    textViewThongBao.setVisibility(View.VISIBLE);
                    textViewThongBao.setText("“Nếu bạn không thích đọc, nghĩa là bạn chưa tìm được cuốn sách phù hợp.”");
                    return false;
                }
            });
            //  truyenList.clear();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query1) {
                    String query=query1.toLowerCase();
                    truyenList.clear();
                    if (query.length() == 0) {
                        truyenList.clear();
                    } else {
                        for (Truyen truyen: truyenListforSearch) {
                            String tentacgia=new String();
                            tentacgia=new TacGiaDAO(myContext).getTacGiaByMaTacGia(truyen.getMaTacGia()).getTenTacGia();
                            if (truyen.getTenTruyen().toLowerCase().contains(query) ||
                                    tentacgia.toLowerCase().contains(query)
                            ) {
                                truyenList.add(truyen);
                            }
                        }
                        if(truyenList.size()==0){
                            textViewThongBao.setText("Không có truyện cần tìm!");
                            textViewThongBao.setVisibility(View.VISIBLE);
                        }
                        else{
                            textViewThongBao.setVisibility(View.GONE);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    return false;
                }


                @Override
                public boolean onQueryTextChange(String newText) {
                    String query=newText.toLowerCase();
                    if (query.length() == 0) {
                        truyenList.clear();
                        adapter.notifyDataSetChanged();
                        textViewThongBao.setVisibility(View.VISIBLE);
                        textViewThongBao.setText("“Nếu bạn không thích đọc, nghĩa là bạn chưa tìm được cuốn sách phù hợp.”");
                    }
                    return false;
                }
            });
        }

        return myView;
    }
    private class LoadDataAsyncTask extends AsyncTask<Void, Void, List<Truyen>> {
        @Override
        protected List<Truyen> doInBackground(Void... voids) {
            // Thực hiện các tác vụ nặng ở đây, ví dụ truy vấn cơ sở dữ liệu
         //   List<Truyen> truyenList = new TruyenDAO(myContext).getTruyenAll();
            List<Truyen> truyenList = new TruyenDAO(myContext).getTruyenForItemHorizonal("SELECT MaTruyen,TenTruyen,MaTacGia,Anh FROM tTruyen",null);
            return truyenList;
        }

        @Override
        protected void onPostExecute(List<Truyen> truyenList) {
            // Xử lý kết quả sau khi tác vụ hoàn thành, ví dụ gán dữ liệu vào danh sách và cập nhật giao diện
            truyenListforSearch.addAll(truyenList);
            adapter.notifyDataSetChanged();
        }
    }

}