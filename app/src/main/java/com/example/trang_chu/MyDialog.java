package com.example.trang_chu;


import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MyDialog {
//    public static void showDialog(Context context, String message, String style) {
//        int layoutResId = 0;
//        int buttonColorResId;
//        if (style.equals("success")) {
//            layoutResId = R.layout.susccess_dialog;
//          //  buttonColorResId = R.color.success_button_color;
//        } else if (style.equals("error")) {
//           // layoutResId = R.layout.error_dialog;
//          //  buttonColorResId = R.color.error_button_color;
//        } else {
//            return; // Handle unsupported style
//        }
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        View view = LayoutInflater.from(context).inflate(layoutResId, null);
//        builder.setView(view);
//
//        TextView messageTextView = view.findViewById(R.id.success_content);
//        TextView titleTextView = view.findViewById(R.id.success_title);
//        Button doneButton = view.findViewById(R.id.success_done);
//
//        messageTextView.setText(message);
//        //doneButton.setBackgroundColor(context.getResources().getColor(buttonColorResId));
//
//        final AlertDialog alertDialog = builder.create();
//        doneButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//
//        if (alertDialog.getWindow() != null) {
//            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        }
//        alertDialog.show();
//    }
    @SuppressLint("StaticFieldLeak")
    private static Activity currentActivity;
    public static boolean isFisnishAction;
    public static int SUCCESS=1;
    public static int ERROR=-1;
    public static int WARNING=0;
    public static int INFORMATION=2;
    public static void showSuccessDialog(Activity activity,String mes, int style) {
        showSuccessDialog(activity, false,mes,style);
    }
    public static void showSuccessDialog(Activity activity, boolean isFisnish, String mes, int style ) {
        currentActivity = activity;
        isFisnishAction = isFisnish;
        AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
        LayoutInflater inflater = LayoutInflater.from(currentActivity);
        View dialogView = inflater.inflate(R.layout.susccess_dialog,null );

        ImageView imageView=dialogView.findViewById(R.id.image_success);
        TextView titleTextView = dialogView.findViewById(R.id.success_title);
        TextView contentTextView = dialogView.findViewById(R.id.success_content);
        Button doneButton = dialogView.findViewById(R.id.success_done);
        int colorId = activity.getResources().getColor(R.color.btn_error);

        switch (style){

            case -1:
                titleTextView.setText("Lỗi");
                colorId = activity.getResources().getColor(R.color.btn_error);
                titleTextView.setTextColor(colorId);
                imageView.setImageResource(R.drawable.error);

                break;
            case 0:
                titleTextView.setText("Cảnh báo");
                colorId = activity.getResources().getColor(R.color.btn_warning);
                titleTextView.setTextColor(colorId);
                imageView.setImageResource(R.drawable.warning);
                break;
            case 1:
                titleTextView.setText("Thành công");
                colorId = activity.getResources().getColor(R.color.btn_success);
                titleTextView.setTextColor(colorId);
                imageView.setImageResource(R.drawable.check);
                break;
            case 2:
                titleTextView.setText("Thông báo");
                colorId = activity.getResources().getColor(R.color.btn_information);
                titleTextView.setTextColor(colorId);
                imageView.setImageResource(R.drawable.information);
                break;
        }
        contentTextView.setText(mes);
        ColorStateList colorStateList = ColorStateList.valueOf(colorId);
        doneButton.setBackgroundTintList(colorStateList);

        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(isFisnishAction){
                    currentActivity.finish();
                }
            }
        });
        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            dialogWindow.setBackgroundDrawable(new ColorDrawable(0));
            Rect displayRectangle = new Rect();
            dialogWindow.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
            dialog.getWindow().setLayout((int) (displayRectangle.width() *
                    0.8f), dialog.getWindow().getAttributes().height);
        }
        dialog.show();
    }
}
