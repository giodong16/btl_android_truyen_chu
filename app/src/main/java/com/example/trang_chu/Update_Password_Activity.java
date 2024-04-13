package com.example.trang_chu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Update_Password_Activity extends AppCompatActivity {
    TextInputEditText username,old_password,new_password1,new_password2;
    TextInputLayout username_container,old_password_container,new_password1_container,new_password2_container;
    Button btnUpdate;
    ImageView btnQuit;
    String matkhaucu;
    public static final int RESULT_CODE_UPDATE_PASSWORD=1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        anhXa();
        getData();
        setActions();
    }

    private void setActions() {
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mes1=validUserName();
                String mes2=validOldPassword();
                String mes3=validNewPassword1();
                String mes4=validNewPassword2();
                if(mes1.length()==0 &&mes2.length()==0 &&mes3.length()==0&& mes4.length()==0){
                    Intent intent=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putString("TENDANGNHAP",username.getText().toString());
                    bundle.putString("MATKHAU",new_password1.getText().toString());
                    intent.putExtras(bundle);
                    setResult(RESULT_CODE_UPDATE_PASSWORD,intent);
                    finish();
                }
                else{
                    username_container.setHelperText(mes1);
                    old_password_container.setHelperText(mes2);
                    new_password1_container.setHelperText(mes3);
                    new_password2_container.setHelperText(mes4);
                }
            }
        });
    }

    private void getData() {
        Intent intent=getIntent();
        Bundle bundle=new Bundle();
        bundle=intent.getExtras();
        if(bundle!=null){
            String tendangnhap=bundle.getString("TENDANGNHAP");
            matkhaucu=bundle.getString("MATKHAU");
            username.setText(tendangnhap);
        //    old_password.setText(matkhaucu);

        }
    }

    private void anhXa() {
        username=findViewById(R.id.edittext_username_update_activity);
        old_password=findViewById(R.id.edittext_old_password_update_activity);
        new_password1=findViewById(R.id.edittext_new_password_update_activity);
        new_password2=findViewById(R.id.edittext_new_password_1_update_activity);

        username_container=findViewById(R.id.username_container_update_activity);
        old_password_container=findViewById(R.id.password_old_container_update_activity);
        new_password1_container=findViewById(R.id.password_new_container_update_activity);
        new_password2_container=findViewById(R.id.password_container1_update_activity);

        btnUpdate=findViewById(R.id.btnUpdate_password_activity);
        btnQuit=findViewById(R.id.btn_quit_update_password_activity);
    }
    public  String validUserName(){
        String name= Objects.requireNonNull(username.getText()).toString();
        String mes="";
        String regex="^[a-zA-Z0-9]{1,30}$";
        if(username.getText()==null || username.getText().length()==0) mes="Bắt buộc";
        else if(name.length()>30) mes="Tên tài khoản giới hạn trong 30 kí tự";
        else if(!name.matches(regex)){
            mes="Tên chỉ được gồm chữ và số";
        }

        return mes;
    }
    public String validOldPassword(){
        String password= Objects.requireNonNull(old_password.getText()).toString();
        String mes="";
        if(old_password.getText()==null || old_password.getText().length()==0) mes="Bắt buộc";
        else if(!password.equals(matkhaucu)) mes="Sai mật khẩu!";
        return mes;
    }
    public String validNewPassword1(){
        String password= Objects.requireNonNull(new_password1.getText()).toString();
        String mes="";
        String regex="^[a-zA-Z0-9]{8}$";
        if(new_password1.getText()==null || new_password1.getText().length()==0) mes="Bắt buộc";
        else if(password.length()!=8) mes="Mật khẩu phải đủ 8 kí tự";
        else if(!password.matches(regex)){
            mes="Mật khẩu chỉ được gồm chữ và số";
        }

        return mes;
    }
    public String validNewPassword2(){
        String password= Objects.requireNonNull(new_password2.getText()).toString();
        String mes="";
        String pass1=validNewPassword1();
        if(new_password2.getText()==null || new_password2.getText().length()==0) mes="Bắt buộc";
        else if( pass1.length()==0 && !password.equals(Objects.requireNonNull(new_password1.getText()).toString())) mes="Sai mật khẩu";
        return mes;
    }
}