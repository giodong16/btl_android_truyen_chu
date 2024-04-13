package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.trang_chu.InfoTruyenActivity;
import com.example.trang_chu.MainActivity;
import com.example.trang_chu.R;

import java.util.List;

import Model.Truyen;

public class ListTruyen_HorizontalScrollView extends LinearLayout{
    Context myContext;

    public ListTruyen_HorizontalScrollView(Context myContext) {
        super(myContext);
        this.myContext=myContext;
        setOrientation(HORIZONTAL);

    }
    public ListTruyen_HorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.myContext = context;
        setOrientation(HORIZONTAL);
    }

    public void AddItemToScrollView(Truyen truyen){
        if(truyen==null){
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View itemView =inflater.inflate(R.layout.vertical_listview_layout,null);
        ImageView imageView=itemView.findViewById(R.id.image_vertical);
        TextView textName=itemView.findViewById(R.id.textName_vertical);
        textName.setTextAppearance(R.style.textView_title_style);
        String tenAnh =truyen.getAnh();
        int resID = getResources().getIdentifier(tenAnh, "drawable", myContext.getPackageName());
        if(resID!=0){
            imageView.setImageResource(resID);
        }

        textName.setText(truyen.getTenTruyen());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(myContext, InfoTruyenActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("MATRUYEN",truyen.getMaTruyen());
                intent.putExtras(bundle);
                myContext.startActivity(intent);
            }
        });
        addView(itemView);
        }
    }

