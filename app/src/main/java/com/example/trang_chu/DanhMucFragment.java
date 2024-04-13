package com.example.trang_chu;

import static android.view.Gravity.CENTER;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Model.TheLoai;
import SQL.TheLoaiDAO;

public class DanhMucFragment extends Fragment {
    GridLayout gridLayout;
    Context myContext;
    View myView;
    List<TheLoai> theLoaiList;
    public DanhMucFragment(){
    }
    public DanhMucFragment(Context context) {
        this.myContext=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            theLoaiList = bundle.getParcelableArrayList("danhmucfragment_listtheloai");
        } else {
            theLoaiList = new ArrayList<>();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(myView==null ){
            myView=inflater.inflate(R.layout.fragment_danh_muc,container,false);
            gridLayout=myView.findViewById(R.id.gridlayout);

          //  theLoaiList=new TheLoaiDAO(myContext).getTheLoaiALL();

            try {
                for (TheLoai theLoai : theLoaiList) {
                    GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                            new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                    );
                    layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);

                    float paddingInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());
                    //hiển thị
                    TextView textView = new TextView(myContext);
                    textView.setText(theLoai.getTenTheLoai());
                    textView.setBackgroundResource(R.drawable.style_button);
                    textView.setLayoutParams(layoutParams);
                    textView.setPadding((int) paddingInPx, (int) paddingInPx,(int) paddingInPx,(int) paddingInPx);
                    layoutParams.setMargins(0,(int) paddingInPx,(int) paddingInPx,0);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextColor(Color.WHITE);
                    // add sự kiện
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(myContext,ListTruyenActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("LOAI","TheLoai");
                            bundle.putString("MA",theLoai.getMaTheLoai());
                            intent.putExtras(bundle);
                            myContext.startActivity(intent);

                        }
                    });
                    gridLayout.addView(textView);
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

            return myView;
        }
}