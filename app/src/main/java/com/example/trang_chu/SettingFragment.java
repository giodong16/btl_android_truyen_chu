package com.example.trang_chu;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.Objects;

import Model.TaiKhoan;
import SQL.TaiKhoanDAO;


public class SettingFragment extends Fragment {
    private static final int REQUEST_CODE_UPDATE_PERSONAL_INFORMATION = 100;
    private static final int REQUEST_CODE_UPDATE_PASSWORD = 200;
    TextView textAboutUs,textDangXuat,textUpdate;
    TextView textUpdateInfo,textUpdatePassword;
    LinearLayout container_update_text;
    public static boolean isSwitch=false;
    Context myContext;
    View myView;
    ShapeableImageView imageView_avatar;
    TaiKhoan taiKhoan=new TaiKhoan();
    private TextView tentaikhoan_setting;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences=myContext.getSharedPreferences(LoginActivity.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        int account_id=sharedPreferences.getInt(LoginActivity.USER_ID,0);
        if(account_id!=0)
        taiKhoan=new TaiKhoanDAO(myContext).getTaiKhoanByID(account_id);
        else taiKhoan=new TaiKhoan();
    }

    public SettingFragment(Context context) {
        this.myContext=context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(myView==null ){
            myView= inflater.inflate(R.layout.fragment_setting, container, false);
            anhXa();
            getData();
            textAboutUs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(myContext,AboutActivity.class);
                    startActivity(intent);
                }
            });
            textDangXuat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearLoggedInID();
                    Intent intent = new Intent(myContext, LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                }
            });
            textUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(container_update_text.getVisibility()==View.GONE){
                        container_update_text.setVisibility(View.VISIBLE);
                        @SuppressLint("UseCompatLoadingForDrawables") Drawable style = myContext.getResources().getDrawable(R.drawable.baseline_keyboard_arrow_up_24);
                        textUpdate.setCompoundDrawablesWithIntrinsicBounds(null, null, style, null);
                    }
                    else {
                        container_update_text.setVisibility(View.GONE);
                        @SuppressLint("UseCompatLoadingForDrawables") Drawable style = myContext.getResources().getDrawable(R.drawable.baseline_keyboard_arrow_down_24);
                        textUpdate.setCompoundDrawablesWithIntrinsicBounds(null, null, style, null);
                    }

//                    Intent intent =new Intent(myContext,UpdateActivity.class);
//                    Bundle bundle=new Bundle();
//                    bundle.putInt("ID",taiKhoan.getId());
//                    bundle.putString("TENDANGNHAP",taiKhoan.getTenDangNhap());
//                    bundle.putString("TENTAIKHOAN",taiKhoan.getTenTaiKhoan());
//                    bundle.putString("MATKHAU",taiKhoan.getMatKhau());
//                    bundle.putString("ANH",taiKhoan.getAnh());
//                    intent.putExtras(bundle);
//                    startActivityForResult(intent,REQUEST_CODE_UPDATE);
                }
            });

            textUpdateInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(myContext,Update_Personal_Info_Activity.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt("ID",taiKhoan.getId());
                    bundle.putString("TENTAIKHOAN",taiKhoan.getTenTaiKhoan());
                    bundle.putString("ANH",taiKhoan.getAnh());
                    intent.putExtras(bundle);
                    startActivityForResult(intent,REQUEST_CODE_UPDATE_PERSONAL_INFORMATION);
                }
            });
            textUpdatePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(myContext,Update_Password_Activity.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt("ID",taiKhoan.getId());
                    bundle.putString("TENDANGNHAP",taiKhoan.getTenDangNhap());
                    bundle.putString("MATKHAU",taiKhoan.getMatKhau());
                    intent.putExtras(bundle);
                    startActivityForResult(intent,REQUEST_CODE_UPDATE_PASSWORD);
                }
            });
        }
        return myView;
    }

    private void getData() {
        if(taiKhoan==null ){
            imageView_avatar.setImageResource(R.drawable.login);
        }
        else {
            tentaikhoan_setting.setText(taiKhoan.getTenTaiKhoan());
            String path=taiKhoan.getAnh();
            if(path!=null && path.length()>0){
                if(path.contains("/")){
                    Uri imageUri = Uri.parse(path);
                    imageView_avatar.setImageURI(imageUri);
                }
            }
            else{
                imageView_avatar.setImageResource(R.drawable.login);
            }
        }
    }

    private void anhXa() {
        textAboutUs=myView.findViewById(R.id.textAbouUs);
        textDangXuat=myView.findViewById(R.id.textDangXuat);
        imageView_avatar=myView.findViewById(R.id.setting_avatar);
        tentaikhoan_setting=myView.findViewById(R.id.tentaikhoan_setting);
        textUpdate=myView.findViewById(R.id.textUpdate_thongtin_setting);
        container_update_text=myView.findViewById(R.id.update_thongtin_setting_menu);
        textUpdateInfo=myView.findViewById(R.id.textUpdate_thongtin_setting_info);
        textUpdatePassword=myView.findViewById(R.id.textUpdate_thongtin_setting_password);
    }
    private void clearLoggedInID() {
        SharedPreferences prefs = myContext.getSharedPreferences(LoginActivity.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(LoginActivity.USER_ID);
        editor.apply();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            Bundle bundle=data.getExtras();
            if(bundle!=null){
                if(requestCode==REQUEST_CODE_UPDATE_PERSONAL_INFORMATION && resultCode==Update_Personal_Info_Activity.RESULTCODE_PERSONAL_INFORMATION){
                    taiKhoan.setAnh(bundle.getString("ANH"));
                    taiKhoan.setTenTaiKhoan(bundle.getString("TENDANGNHAP"));
                    TaiKhoanDAO taiKhoanDAO=new TaiKhoanDAO(myContext);
                    if(taiKhoanDAO.updateTaiKhoan(taiKhoan)){
                        getData();
                    }
                }
                else if(requestCode==REQUEST_CODE_UPDATE_PASSWORD && resultCode==Update_Password_Activity.RESULT_CODE_UPDATE_PASSWORD) {
                    taiKhoan.setTenDangNhap(bundle.getString("TENDANGNHAP"));
                    taiKhoan.setMatKhau(bundle.getString("MATKHAU"));
                    TaiKhoanDAO taiKhoanDAO=new TaiKhoanDAO(myContext);
                    if (taiKhoanDAO.updateTaiKhoan(taiKhoan)){}
//                        Toast.makeText(myContext,"Thành công",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}