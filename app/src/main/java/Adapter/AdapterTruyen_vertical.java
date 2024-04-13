package Adapter;

//import static androidx.appcompat.graphics.drawable.DrawableContainerCompat.Api21Impl.getResources;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.trang_chu.R;

import java.util.ArrayList;
import java.util.List;

import Model.Truyen;

public class AdapterTruyen_vertical  extends BaseAdapter {
    Context myContext;
    int myLayout;
    LayoutInflater inflater;
    ArrayList<Truyen> listTruyen;

    public AdapterTruyen_vertical(Context myContext, int myLayout, ArrayList<Truyen> listTruyen) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.listTruyen = listTruyen;
    }

    @Override
    public int getCount() {
        return listTruyen.size();
    }

    @Override
    public Object getItem(int position) {
        return listTruyen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater=(LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(myLayout,null);

        //ánh xạ
        ImageView imageView=convertView.findViewById(R.id.image_vertical);
       // TextView textStatus=convertView.findViewById(R.id.textStatus_vertical);
        TextView textName=convertView.findViewById(R.id.textName_vertical);


//        String tenAnh = listTruyen.get(position).getAnh();
//        int resID = getResources(tenA).getIdentifier(tenAnh, "drawable", getPackageName());
//        int resID=getResources(myContext.getTheme()).getIdentifier(tenAnh,"drawable", myContext.getPackageName());
//        imageView.setImageResource(resID);
        String tenAnh = listTruyen.get(position).getAnh();
        @SuppressLint("DiscouragedApi") int resID = myContext.getResources().getIdentifier(tenAnh, "drawable", myContext.getPackageName());
        if(resID!=0){
            imageView.setImageResource(resID);
        }
        /*if(listTruyen.get(position).getTrangThai()==0)
        textStatus.setBackground(null);
        else{
            textStatus.setBackgroundResource(R.drawable.baseline_bookmark_24);
            int color = ContextCompat.getColor(myContext, R.color.bookmark_color);
            textStatus.setBackgroundTintList(ColorStateList.valueOf(color));

        }*/
        textName.setText(listTruyen.get(position).getTenTruyen());

        return convertView;
    }
}
