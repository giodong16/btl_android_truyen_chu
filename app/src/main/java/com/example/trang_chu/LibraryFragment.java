package com.example.trang_chu;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import Adapter.AdapterTruyen_horizonal;
import Adapter.RecyclerViewAdapter_KeSach;
import Model.Truyen;
import SQL.TruyenDAO;


public class LibraryFragment extends Fragment {

    Context myContext;
    View myView;
    RecyclerView recyclerView;
    RecyclerViewAdapter_KeSach adapter;
    List<Truyen> truyenList;
    TextView textViewThongBao;
    private Activity activity;
    int index = 0;

    public LibraryFragment(){
    }
    public LibraryFragment(Context myContext) {
        this.myContext = myContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            myView = inflater.inflate(R.layout.fragment_library, container, false);
            recyclerView = myView.findViewById(R.id.recyclerview_KeSach);
            textViewThongBao=myView.findViewById(R.id.textThongbao_library);
            truyenList = new ArrayList<>();
            truyenList = new TruyenDAO(myContext).getTruyenForItemHorizonal("SELECT MaTruyen,TenTruyen,MaTacGia,Anh FROM tTruyen where YeuThich=?",new String[]{"1"});
            adapter = new RecyclerViewAdapter_KeSach(LibraryFragment.this, myContext, truyenList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            //tạo đối tượng ItemTouchHelper và gắn nó vào ListView
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }
                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    Truyen truyen = truyenList.get(viewHolder.getAdapterPosition());
                    int position = viewHolder.getAdapterPosition();
                    truyenList.remove(position);
                    adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    if(truyenList==null||truyenList.size()==0){
                        textViewThongBao.setVisibility(View.VISIBLE);
                    }
                    else{
                        textViewThongBao.setVisibility(View.GONE);
                    }
                    TruyenDAO truyenDAO = new TruyenDAO(myContext);
                    truyen.setYeuThich(0);
                    truyenDAO.update(truyen);

                    //quay lại
                    Snackbar.make(recyclerView, "Đã xóa " + truyen.getTenTruyen(), Snackbar.LENGTH_LONG).setAction("Hoàn tác", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            truyenList.add(position, truyen);
                            adapter.notifyItemInserted(position);
                            truyen.setYeuThich(1);
                            truyenDAO.update(truyen);

                        if(truyenList==null||truyenList.size()==0){
                            textViewThongBao.setVisibility(View.VISIBLE);
                        }
                        else{
                            textViewThongBao.setVisibility(View.GONE);
                        }
                        }
                    }).show();
                }
            });
            itemTouchHelper.attachToRecyclerView(recyclerView);
            if(truyenList==null||truyenList.size()==0){
                textViewThongBao.setVisibility(View.VISIBLE);
            }
            else{
                textViewThongBao.setVisibility(View.GONE);
            }



        return myView;
    }

}
