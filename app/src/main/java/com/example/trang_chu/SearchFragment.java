package com.example.trang_chu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
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

//    public SearchFragment(Context context, List<Truyen> searchFragmentTruyenList) {
//        this.myContext=context;
//        truyenList=searchFragmentTruyenList;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            truyenList = bundle.getParcelableArrayList("searchfragment_listtruyen");
        } else {
            truyenList = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(myView==null){
            myView= inflater.inflate(R.layout.fragment_search, container, false);
            textViewThongBao=myView.findViewById(R.id.textThongbao_searchFragment);
            searchView=myView.findViewById(R.id.search_truyen);
            listView=myView.findViewById(R.id.listview_truyenSearch);

            //truyenList=new ArrayList<>();

            truyenListforSearch=new ArrayList<>();
            adapter=new AdapterTruyen_horizonal(myContext,R.layout.horizontal_listview_layout,truyenList);
            listView.setAdapter(adapter);
            truyenListforSearch.addAll(truyenList);


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
            if((searchView.getQuery() == null) || (searchView.getQuery().length() == 0)){
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

}