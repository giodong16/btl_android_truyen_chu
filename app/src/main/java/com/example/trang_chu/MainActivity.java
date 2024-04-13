package com.example.trang_chu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import Model.TheLoai;
import Model.Truyen;
import SQL.TheLoaiDAO;
import SQL.TruyenDAO;
import SQL.copyDatabaseFromAssets;

public class MainActivity extends AppCompatActivity {
    private static final int RECORD_REQUEST_CODE = 1000;
    copyDatabaseFromAssets CoppyDataBase;
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment ;
    DanhMucFragment danhMucFragment;
    SearchFragment searchFragment ;
    LibraryFragment libraryFragment=new LibraryFragment(this);
    SettingFragment settingFragment=new SettingFragment(this);
    Toolbar toolbar;



    final String DATABASE_NAME = "qltruyen.db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        yeuCauCapQuyen();
//        CoppyDataBase=new copyDatabaseFromAssets(this);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        getData();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.trangChu){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,homeFragment).commit();
                    return true;
                }
                else if(id==R.id.danhMuc){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,danhMucFragment).commit();
                    return true;
                }
                else if(id==R.id.timKiem){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,searchFragment).commit();
                    return true;
                }
                else if(id==R.id.keSach){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,libraryFragment).commit();
                    return true;
                }
                else if(id==R.id.caiDat){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,settingFragment).commit();
                    return true;
                }

                return false;
            }
        });


        bottomNavigationView.setSelectedItemId(R.id.trangChu);
    }

    private void getData() {
        //search fragment
        List<Truyen> searchFragment_truyenList = new TruyenDAO(this).getTruyenForItemHorizonal("SELECT MaTruyen,TenTruyen,MaTacGia,Anh FROM tTruyen",null);
        Bundle bundle=new Bundle();
        bundle.putParcelableArrayList("searchfragment_listtruyen", (ArrayList<? extends Parcelable>) searchFragment_truyenList);
        searchFragment = new SearchFragment(this);
        searchFragment.setArguments(bundle);

        //danh muc fragment
        List<TheLoai> theLoaiList=new TheLoaiDAO(this).getTheLoaiALL();
        Bundle bundle1=new Bundle();

        bundle1.putParcelableArrayList("danhmucfragment_listtheloai", (ArrayList<? extends Parcelable>) theLoaiList);
        danhMucFragment=new DanhMucFragment(this);
        danhMucFragment.setArguments(bundle1);

        //home fragment
        String ma=new TheLoaiDAO(this).getMaTheLoaiDAO("select MaTheLoai from tTheLoai where tentheloai=?",new String[]{"Ngôn tình"});
        List<Truyen> truyenListNgonTinh=new TruyenDAO(this).getTruyenForItemVertical("Select tTruyen.MaTruyen,TenTruyen,Anh from tTruyen join tTruyen_TheLoai on tTruyen.MaTruyen=tTruyen_TheLoai.MaTruyen where tTruyen_TheLoai.MaTheLoai=? LIMIT 8",new String[]{ma});

        ma=new TheLoaiDAO(this).getMaTheLoaiDAO("select MaTheLoai from tTheLoai where tentheloai=?",new String[]{"Tiên hiệp"});
        List<Truyen>  truyenListTuTien=new TruyenDAO(this).getTruyenForItemVertical("Select tTruyen.MaTruyen,TenTruyen,Anh from tTruyen join tTruyen_TheLoai on tTruyen.MaTruyen=tTruyen_TheLoai.MaTruyen where tTruyen_TheLoai.MaTheLoai=? LIMIT 8",new String[]{ma});

        List<Truyen> truyenListFull=new TruyenDAO(this).getTruyenForItemVertical("Select MaTruyen,TenTruyen,Anh from tTruyen where TrangThai=? LIMIT 8",new String[]{"1"});

        List<Truyen> truyenListDeCu=new TruyenDAO(this).getTruyenForItemVertical("Select MaTruyen,TenTruyen,Anh from tTruyen order by TenTruyen DESC LIMIT 8",null);

        Bundle bundle_home=new Bundle();
        bundle_home.putParcelableArrayList("homefragment_tutien", (ArrayList<? extends Parcelable>) truyenListTuTien);
        bundle_home.putParcelableArrayList("homefragment_ngontinh", (ArrayList<? extends Parcelable>) truyenListNgonTinh);
        bundle_home.putParcelableArrayList("homefragment_decu", (ArrayList<? extends Parcelable>) truyenListDeCu);
        bundle_home.putParcelableArrayList("homefragment_full", (ArrayList<? extends Parcelable>) truyenListFull);
        homeFragment=new HomeFragment(this);
        homeFragment.setArguments(bundle_home);


    }
    void yeuCauCapQuyen(){
        int permission_write_storage = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission_read_storage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission_write_storage != PackageManager.PERMISSION_GRANTED
                || permission_read_storage != PackageManager.PERMISSION_GRANTED
        ) {
            makeRequest();
        }
    }

    private void makeRequest() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.VIBRATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, RECORD_REQUEST_CODE);
    }


}