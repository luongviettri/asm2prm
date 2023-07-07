package com.example.asm2.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;

import com.example.asm2.R;
import com.example.asm2.model.Animal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ViewPhoneDialog {
    ImageView ivIcon;
    EditText etPhoneNumber;
    Context context;


    public void showAlertDialog(final Context context, Animal animal, EditText etNumber) {
        this.context = context;

//        listener.onClick();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //! Tạo đối tượng LayoutInflater từ context
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.alert_dialog_custom_view, null);

        ivIcon = view.findViewById(R.id.ivIcon);
        etPhoneNumber = view.findViewById(R.id.etPhoneNumber);


        SharedPreferences sharedPreferences = context.getSharedPreferences("PhoneAnimals", Context.MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("phoneNumber_" + animal.getName(), "");


        if (!phoneNumber.isEmpty()) {

            etPhoneNumber.setText(phoneNumber);
        }

        ivIcon.setImageBitmap(animal.getPhoto());


        //! Thiết lập view của dialog
        builder.setView(view);

        //! Tạo dialog
        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        //! Xử lý sự kiện cho các button trong dialog
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnDelete = view.findViewById(R.id.btnDelete);


        //? cập nhật vào kho sharedPrefences
        sharedPreferences = context.getSharedPreferences("PhoneAnimals", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //? Xử lý khi người dùng chọn "Yes"
                String phoneNumber = etPhoneNumber.getText().toString();
                if (!phoneNumber.isEmpty()) {


                    etNumber.setVisibility(View.VISIBLE);


                    //! nếu sdt cũ và mới là khác nhau thì phải xóa đi dữ liệu cũ trong sharedPreferences

                    String oldPhoneNumber = animal.getPhoneNumber();
                    if ( !oldPhoneNumber.equals(phoneNumber) ) {
                        SharedPreferences sharedPreferences2 = context.getSharedPreferences("PhoneAnimals___PhoneToImage", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = sharedPreferences2.edit();

                        editor2.remove(oldPhoneNumber);
                        editor2.apply();
                    }


                    Log.d("thuc hien luu vao sharedPrefernces", "thuc hien luu vao sharedPrefernces");

                    editor.putString("phoneNumber_" + animal.getName(), phoneNumber);
                    editor.apply();

                    //!lưu dữ liệu này để lấy dữ liệu hình ảnh tương ứng khi nhận cuộc gọi

                    //! Lưu Bitmap thành tệp tin

                    String filePath = saveBitmapToFile(animal.getPhoto());


                    SharedPreferences sharedPreferences2 = context.getSharedPreferences("PhoneAnimals___PhoneToImage", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = sharedPreferences2.edit();

                    editor2.putString(phoneNumber, filePath);

                    editor2.apply();


                    //!cập nhật lại giao diện
                    etNumber.setText(phoneNumber);
                }

                dialog.dismiss(); // Đóng dialog
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //? Xử lý khi người dùng chọn "No"
                Log.d("thuc hien remove sharedPrefernces", "thuc hien remove sharedPrefernces");

                SharedPreferences sharedPreferences2 = context.getSharedPreferences("PhoneAnimals___PhoneToImage", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();

                editor2.remove(animal.getPhoneNumber());
                editor2.apply();

                editor.remove("phoneNumber_" + animal.getName());
                editor.apply();

                dialog.dismiss(); // Đóng dialog

                //! chinh lai edittext
                etNumber.setText("");
                etNumber.setVisibility(View.GONE);
            }
        });

        //! Hiển thị dialog
        dialog.show();

    }

    private String saveBitmapToFile(Bitmap bitmap) {
        //! Tạo đường dẫn và tên tệp tin mới
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "image_" + timeStamp + ".jpg";
        File file = new File(context.getFilesDir(), fileName);
        try {
            //? Tạo một luồng ghi dữ liệu vào tệp tin
            FileOutputStream outputStream = new FileOutputStream(file);

            //? Chuyển đổi Bitmap thành dữ liệu JPEG và ghi vào tệp tin
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);

            //? Đóng luồng ghi dữ liệu
            outputStream.close();

            //? Trả về đường dẫn tới tệp tin đã lưu
            return file.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
