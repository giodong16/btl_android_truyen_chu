package Adapter;



import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.trang_chu.MainActivity;
import com.example.trang_chu.R;

import java.util.ArrayList;
import java.util.List;

import Model.TheLoai;
import Model.Truyen;
import SQL.ChuongDAO;
import SQL.TacGiaDAO;

public class AdapterTruyen_horizonal extends BaseAdapter
{
    Context myContext;
    int myLayout;
    LayoutInflater inflater;
    List<Truyen> listTruyen;


    public AdapterTruyen_horizonal(Context myContext, int myLayout, List<Truyen> listTruyen) {
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
        ImageView imageView=convertView.findViewById(R.id.image_horizonal);
        TextView textName=convertView.findViewById(R.id.textName_horizonal);
        TextView textAuthor=convertView.findViewById(R.id.textAuthorName_horizonal);
        TextView textChap=convertView.findViewById(R.id.textChap_horizonal);
        TextView textTheLoai=convertView.findViewById(R.id.textTheLoai_horizonal);

        //gán
        Truyen truyen=listTruyen.get(position);
        String tenAnh =truyen.getAnh();
        int resID = myContext.getResources().getIdentifier(tenAnh, "drawable", myContext.getPackageName());
        if(resID!=0){
            imageView.setImageResource(resID);
        }
        textName.setText(truyen.getTenTruyen());
        TacGiaDAO tacGiaDAO=new TacGiaDAO(myContext);
        textAuthor.setText(tacGiaDAO.getTacGiaByMaTacGia(truyen.getMaTacGia()).getTenTacGia());
        textChap.setText(new ChuongDAO(myContext).getSoLuongChuongByMaTruyen(truyen.getMaTruyen()) +" chương");
        List<TheLoai> listTheLoai=truyen.getTheLoaiList();
        String theloai=listTheLoai.get(0).getTenTheLoai();
        for(int i=1;i<listTheLoai.size();i++){
            theloai+=", "+listTheLoai.get(i).getTenTheLoai();
        }
        textTheLoai.setText(theloai);

        return convertView;
    }
}
