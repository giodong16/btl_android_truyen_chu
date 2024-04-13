package com.example.trang_chu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class Update_Personal_Info_Activity extends AppCompatActivity {

    TextInputEditText accountname;
    ShapeableImageView imageView;
    TextInputLayout accountnameContainer;
    Button btnUpdate;
    ImageView btnQuit;
    public static  int RESULTCODE_PERSONAL_INFORMATION=101;
    public static  int GET_IMAGE=2003;
    private String image_path="";
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_personal_info);
        anhXa();
        getData();
        setActions();
    }

    private void getData() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            String id=bundle.getString("ID");
            String tentaikhoan=bundle.getString("TENTAIKHOAN");
             image_path=bundle.getString("ANH");
            accountname.setText(tentaikhoan);
            if(image_path!=null){
                Uri imageUri = Uri.parse(image_path);
                imageView.setImageURI(imageUri);
            }
            else{
                imageView.setImageResource(R.drawable.login);
            }
        }
    }

    private void setActions() {
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GET_IMAGE);

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mes="";
                mes=validAccoutName();
                if(mes!=null && mes.length()>0){
                    accountnameContainer.setHelperText(mes);
                }
                else{
                    Bundle bundle=new Bundle();
                    bundle.putString("ANH",image_path);
                    bundle.putString("TENDANGNHAP",accountname.getText().toString());
                    Intent intent=new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULTCODE_PERSONAL_INFORMATION,intent);
                    finish();
                }

            }
        });
    }

    private void anhXa() {
        btnUpdate=findViewById(R.id.btnUpdate_personal_information_activity);
        accountname=findViewById(R.id.edittext_account_name_update_activity);
        accountnameContainer=findViewById(R.id.accountname_container_update_activity);
        imageView=findViewById(R.id.imageview_avatar_update_activity);
        btnQuit=findViewById(R.id.btn_quit_update_personal_information_activity);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri uri_img = data.getData();
            String picturePath = getRealPathFromURI(uri_img);
            image_path = picturePath;
            if (picturePath != null) {
                // Lấy tên file gốc
                String imageName = getFileNameFromPath(picturePath);
                // Copy ảnh vào thư mục ứng dụng
                String destinationPath = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/"+imageName;
                try {
                    File sourceFile = new File(picturePath);
                    File destinationFile = new File(destinationPath);
                    Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    // Lưu đường dẫn mới vào biến image_path
                    image_path = destinationPath;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Hiển thị ảnh đã chọn
                uri = Uri.parse(image_path);
                imageView.setImageURI(uri);
            }

        }}

    private String getFileNameFromPath(String picturePath) {
        int lastIndex = picturePath.lastIndexOf("/");
        if (lastIndex >= 0 && lastIndex < picturePath.length() - 1) {
            return picturePath.substring(lastIndex + 1);
        }
        return null;
    }

    private String getRealPathFromURI(Uri urlImg) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(urlImg, projection, null, null, null);
        if (cursor == null) return null;
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;

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
}