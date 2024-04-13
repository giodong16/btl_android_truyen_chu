package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.trang_chu.ListTruyenActivity;
import com.example.trang_chu.R;

import java.util.List;

import Model.TheLoai;

@SuppressLint("ViewConstructor")
public class CustomLayoutForTheLoai extends LinearLayout {

    public CustomLayoutForTheLoai(Context context, List<TheLoai> list) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i < list.size(); i++) {
            Button button = new Button(context);
            final String ma=list.get(i).getMaTheLoai();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    120
            );
            layoutParams.setMargins(15,0,15,0);
            button.setLayoutParams(layoutParams);
            button.setText(list.get(i).getTenTheLoai());
            button.setTextSize(9);
            button.setTextAppearance(R.style.style_button_theloai);
            @SuppressLint("UseCompatLoadingForDrawables")
            Drawable drawable = context.getDrawable(R.drawable.style_tag_theloai);
            button.setBackground(drawable);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ListTruyenActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("LOAI","TheLoai");
                    bundle.putString("MA",ma);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            addView(button);
        }
    }
}
