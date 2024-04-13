package com.example.trang_chu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import Model.TaiKhoan;
import SQL.TaiKhoanDAO;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText username,accountname,password1,password2;
    TextInputLayout usernameContainer,accountnameContainer,password1Container,password2Container;
    Button btnSignUp;
    Button btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        anhXa();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isNotValid=validAll();
                if(!isNotValid) {
                    boolean isSuccess=new  TaiKhoanDAO(SignUpActivity.this).createTaiKhoan(new TaiKhoan(0,accountname.getText().toString(),
                            username.getText().toString(),password1.getText().toString(),null));
                    if(isSuccess){
                        MyDialog.showSuccessDialog(SignUpActivity.this,true,"Tài khoản đã được tạo!",MyDialog.SUCCESS);
//                        MyDialog.showSuccessDialog(SignUpActivity.this,"Tạo thành công!",MyDialog.INFORMATION);
//                        MyDialog.showSuccessDialog(SignUpActivity.this,"Tạo thành công!",MyDialog.ERROR);
//                        MyDialog.showSuccessDialog(SignUpActivity.this,"Tạo thành công!",MyDialog.SUCCESS);
//                        MyDialog.showSuccessDialog(SignUpActivity.this,"Tạo thành công!",MyDialog.WARNING);
                    }

                }


            }
        });
    }

    private boolean validAll() {
        String mesUser=validUserName();
        String mesAccount=validAccoutName();
        String mesPassword1=validNewPassword();
        String mesPassword2=validNewPassword2();
        accountnameContainer.setHelperText(mesAccount);
        usernameContainer.setHelperText(mesUser);
        password1Container.setHelperText(mesPassword1);
        password2Container.setHelperText(mesPassword2);
        return mesUser.length() != 0 || mesAccount.length() != 0 || mesPassword1.length() != 0 || mesPassword2.length() != 0;
    }

    private void anhXa() {
        username=findViewById(R.id.edittext_username_create);
        accountname=findViewById(R.id.edittext_account_name_create);
        password1=findViewById(R.id.edittext_new_password);
        password2=findViewById(R.id.edittext_new_password_1);
        btnCancel=findViewById(R.id.btnCancel_signup);
        btnSignUp=findViewById(R.id.btnSignUp_signup);

        usernameContainer=findViewById(R.id.username_container);
        accountnameContainer=findViewById(R.id.accountname_container);
        password1Container=findViewById(R.id.password_container);
        password2Container=findViewById(R.id.password_container1);
    }
    public String validAccoutName(){
        String mes="";

        String name= Objects.requireNonNull(accountname.getText()).toString();
        String regex = "^[\\p{L}\\s]+$";
        if(accountname.getText()==null || accountname.getText().length()==0) mes="Bắt buộc";
        else if(name.length()>50) mes="Tên tài khoản chỉ giới hạn trong 50 kí tự";
        else if(!name.matches(regex)){
            mes="Tên chỉ được gồm chữ và dấu cách";
        }

        return mes;
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
    public String validNewPassword(){
        String password= Objects.requireNonNull(password1.getText()).toString();
        String mes="";
        String regex="^[a-zA-Z0-9]{8}$";
        if(password1.getText()==null || password1.getText().length()==0) mes="Bắt buộc";
        else if(password.length()!=8) mes="Mật khẩu phải đủ 8 kí tự";
        else if(!password.matches(regex)){
            mes="Mật khẩu chỉ được gồm chữ và số";
        }

        return mes;
    }
    public String validNewPassword2(){
        String password= Objects.requireNonNull(password2.getText()).toString();
        String mes="";
        String regex="^[a-zA-Z0-9]{8}$";
        String pass1=validNewPassword();
        if(password2.getText()==null || password2.getText().length()==0) mes="Bắt buộc";
        else if( pass1.length()==0 && password1.getText()!=null && !password.equals(password1.getText().toString()) ) mes="Sai mật khẩu";
        return mes;
    }

}