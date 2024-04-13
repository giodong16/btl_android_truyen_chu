package Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trang_chu.InfoTruyenActivity;
import com.example.trang_chu.MainActivity;
import com.example.trang_chu.R;

import java.util.List;

import Model.TheLoai;
import Model.Truyen;
import SQL.ChuongDAO;
import SQL.TacGiaDAO;
import SQL.TruyenDAO;

public class RecyclerViewAdapter_KeSach extends RecyclerView.Adapter<RecyclerViewAdapter_KeSach.ViewHolder> {
    Context myContext;
    List<Truyen> truyenList;
    private Fragment fragment;
    public RecyclerViewAdapter_KeSach(Fragment fragment,Context context, List<Truyen> truyenList) {
        this.myContext = context.getApplicationContext(); // Tránh rò rỉ bộ nhớ
        this.truyenList = truyenList;
        this.fragment=fragment;
    }
    @NonNull
    @Override
    public RecyclerViewAdapter_KeSach.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(myContext);
        View view=inflater.inflate(R.layout.horizontal_listview_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_KeSach.ViewHolder holder, int position) {
        //gán
        Truyen truyen=truyenList.get(position);
        if(truyen==null){
            return;
        }
        String tenAnh = truyen.getAnh();
        if (tenAnh != null) {
            @SuppressLint("DiscouragedApi") int resID = myContext.getResources().getIdentifier(tenAnh, "drawable", myContext.getPackageName());
            if (resID != 0) {
                holder.imageView.setImageResource(resID);
            } else {
                // Xử lý trường hợp không tìm thấy ID tài nguyên
            }
        }
        holder.textName.setText(truyen.getTenTruyen());
        TacGiaDAO tacGiaDAO=new TacGiaDAO(myContext);
        holder.textAuthor.setText(tacGiaDAO.getTacGiaByMaTacGia(truyen.getMaTacGia()).getTenTacGia());
        holder.textChap.setText(new ChuongDAO(myContext).getSoLuongChuongByMaTruyen(truyen.getMaTruyen()) +" chương");
        List<TheLoai> listTheLoai=truyen.getTheLoaiList();
        String theloai=listTheLoai.get(0).getTenTheLoai();
        for(int i=1;i<listTheLoai.size();i++){
            theloai+=", "+listTheLoai.get(i).getTenTheLoai();
        }
        holder.textTheLoai.setText(theloai);

        if(MainActivity.isDark){
            ColorFilter colorFilter = new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            holder.imageView1.setColorFilter(colorFilter);
            holder.imageView2.setColorFilter(colorFilter);
            holder.imageView3.setColorFilter(colorFilter);
            holder.imageView4.setColorFilter(colorFilter);

            holder.textAuthor.setTextColor(Color.WHITE);
            holder.textChap.setTextColor(Color.WHITE);
            holder.textTheLoai.setTextColor(Color.WHITE);
            holder.textName.setTextColor(Color.WHITE);
        }

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index=holder.getAdapterPosition();
                Intent intent=new Intent(myContext, InfoTruyenActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("MATRUYEN",truyen.getMaTruyen());
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myContext.startActivity(intent);
                /*Truyen truyenMoi= new TruyenDAO(myContext).getTruyenbyMaTruyen(truyen.getMaTruyen());
                if(truyenMoi.getYeuThich()==0){
                    truyenList.remove(index);
                    notifyItemRemoved(index);
                }*/
            }
        });
    }

    @Override
    public int getItemCount() {
        if(truyenList==null) return 0;
        return truyenList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutItem;
        ImageView imageView;
        TextView textName,textTheLoai,textAuthor,textChap;
        ImageView imageView1,imageView2,imageView3,imageView4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             imageView=itemView.findViewById(R.id.image_horizonal);
             textName=itemView.findViewById(R.id.textName_horizonal);
             textAuthor=itemView.findViewById(R.id.textAuthorName_horizonal);
             textChap=itemView.findViewById(R.id.textChap_horizonal);
             textTheLoai=itemView.findViewById(R.id.textTheLoai_horizonal);
             layoutItem=itemView.findViewById(R.id.layout_truyen_horizonal);

             imageView1=itemView.findViewById(R.id.icon1);
             imageView2=itemView.findViewById(R.id.icon2);
             imageView3=itemView.findViewById(R.id.icon3);
             imageView4=itemView.findViewById(R.id.icon4);

        }



    }
}
