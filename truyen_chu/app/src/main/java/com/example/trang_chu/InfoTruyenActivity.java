package com.example.trang_chu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapter.CustomLayoutForTheLoai;
import Adapter.ListTruyen_HorizontalScrollView;
import Model.Truyen;
import SQL.TacGiaDAO;
import SQL.TruyenDAO;

public class InfoTruyenActivity extends AppCompatActivity {
    TextView textTacGia,textTrangThai,textSoChuong,textTenTruyen,text_title_gioithieu,textThemTruyenCungTacGia;
    ImageView imageView;
    TextView textGioiThieu;
    Button btnDocTruyen;
    LinearLayout linearLayoutGioiThieu,linearLayoutTheLoai;
    ListTruyen_HorizontalScrollView truyen_horizontalScrollView;
    ImageButton btndownload,btnThemKe;
    Truyen truyen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_truyen);
        //ánh xạ
        text_title_gioithieu=findViewById(R.id.text_title_gioithieu);
        textGioiThieu=findViewById(R.id.textGioiThieu_infoTruyen);
        linearLayoutGioiThieu=findViewById(R.id.btnGioiThieu);

        linearLayoutTheLoai=findViewById(R.id.linear_layout_theloai);

        truyen_horizontalScrollView=findViewById(R.id.linear_layout_cungtacgia);
        btnDocTruyen=findViewById(R.id.btnDocTruyen);
        textTenTruyen=findViewById(R.id.textName_infoTruyen);
        textSoChuong=findViewById(R.id.textChap_infoTruyen);
        textTacGia=findViewById(R.id.textAuthorName_infoTruyen);

        textTrangThai=findViewById(R.id.textStatus_infoTruyen);
        imageView=findViewById(R.id.image_infoTruyen);
        btndownload=findViewById(R.id.btnDownload);
        btnThemKe=findViewById(R.id.btnThemKe);
        textThemTruyenCungTacGia=findViewById(R.id.textThemTruyenCungTacGia);
        // lấy dữ liệu từ sql với mã truyện được truyền vô
        getData();


        //cài đặt ẩn /hieenj cho giới thiệu
        linearLayoutGioiThieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textGioiThieu.getVisibility()==View.VISIBLE){
                    textGioiThieu.setVisibility(View.GONE);
                    Drawable style = getResources().getDrawable(R.drawable.baseline_keyboard_arrow_down_24);
                    text_title_gioithieu.setCompoundDrawablesWithIntrinsicBounds(null, null, style, null);

                }

                else{
                    textGioiThieu.setVisibility(View.VISIBLE);
                    Drawable style = getResources().getDrawable(R.drawable.baseline_keyboard_arrow_up_24);
                    text_title_gioithieu.setCompoundDrawablesWithIntrinsicBounds(null, null, style, null);

                }

            }
        });

        //thêm/bỏ khỏi kệ sách
        btnThemKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(truyen.getYeuThich()==0){
                    btnThemKe.setImageResource(R.drawable.baseline_bookmark_added_24);
                    truyen.setYeuThich(1);
                    Toast.makeText(InfoTruyenActivity.this,"Đã thêm truyện vào kệ",Toast.LENGTH_SHORT).show();
                }
                else{
                    /*btnThemKe.setImageResource(R.drawable.baseline_bookmark_add_24);
                    truyen.setYeuThich(0);
                    isSwitchStatus=true;*/
                    Toast.makeText(InfoTruyenActivity.this,"Truyện đã ở trong kệ sách",Toast.LENGTH_SHORT).show();


                }
                TruyenDAO truyenDAO1=new TruyenDAO(InfoTruyenActivity.this);
                truyenDAO1.update(truyen);
            }
        });

        //đọc truyện
        btnDocTruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(InfoTruyenActivity.this,SelectChapActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("MATRUYEN",truyen.getMaTruyen());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        textThemTruyenCungTacGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InfoTruyenActivity.this, ListTruyenActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("LOAI","TacGia");
                bundle.putString("MA",truyen.getMaTacGia());
                bundle.putString("MATRUYEN",truyen.getMaTruyen());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void getData() {
        TruyenDAO truyenDAO=new TruyenDAO(this);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            /*//thêm result cho trường hợp dữ liệu được mở từ tủ sách
            bundle.putString("IMAGE",uri);
            intent.putExtras(bundle);
            setResult(resultCode_CRU_Activity,intent);*/
            //
            truyen=new Truyen();
            String matruyen=bundle.getString("MATRUYEN");
            truyen=truyenDAO.getTruyenbyMaTruyen(matruyen);

            //tên ,tác giả, số chương
            textTenTruyen.setText(truyen.getTenTruyen());
            textTacGia.setText(new TacGiaDAO(this).getTacGiaByMaTacGia(truyen.getMaTacGia()).getTenTacGia());
            textSoChuong.setText(truyen.getChuongList().size()+" chương");

            //trạng thái
            if(truyen.getTrangThai()==0){
                textTrangThai.setText("Đang cập nhật");
                if(MainActivity.isDark){
                    textTrangThai.setTextColor(Color.WHITE);
                }
                else textTrangThai.setTextColor(Color.BLACK);
            }
            //giới thiệu
            textGioiThieu.setText(truyen.getGioiThieu());


            //image
            String tenAnh =truyen.getAnh();
            int resID = getResources().getIdentifier(tenAnh, "drawable", getPackageName());
            if(resID!=0){
                imageView.setImageResource(resID);
            }

            //thể loại
            CustomLayoutForTheLoai customLayoutForTheLoai=new CustomLayoutForTheLoai(this,truyen.getTheLoaiList());
            linearLayoutTheLoai.addView(customLayoutForTheLoai);

            // đánh dấu ( thêm kệ)
           /* int yeuthich=truyen.getYeuThich();
            if(yeuthich==0){
                btnThemKe.setImageResource(R.drawable.baseline_bookmark_add_24);
            }
            else{
                btnThemKe.setImageResource(R.drawable.baseline_bookmark_added_24);
            }*/
            //thêm truyện cho cùng tác giả
            List<Truyen> truyenList=new ArrayList<>();
            //truyenList=new TruyenDAO(this).getTruyenByTacGia(truyen.getMaTacGia());
            truyenList=new TruyenDAO(this).getTruyenForItemVertical("Select tTruyen.MaTruyen,TenTruyen,Anh from tTruyen where MaTacGia=? and MaTruyen!= '"+truyen.getMaTruyen()+"' LIMIT 8",new String[]{truyen.getMaTacGia()});
            int size=truyenList.size();
            if(size>5){
                size=5;
            }
            for(int i=0;i<size;i++){
                truyen_horizontalScrollView.AddItemToScrollView(truyenList.get(i));
            }
        }
        else{
            Truyen truyen=new Truyen();
            truyen=truyenDAO.getTruyenbyMaTruyen("TR001");
            textGioiThieu.setText(truyen.getGioiThieu());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

}