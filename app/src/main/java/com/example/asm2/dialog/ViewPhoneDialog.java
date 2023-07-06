package com.example.asm2.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;

import com.example.asm2.R;
import com.example.asm2.model.Animal;

public class ViewPhoneDialog {
    ImageView ivIcon;
    EditText etPhoneNumber;


    public void showAlertDialog(final Context context, Animal animal, EditText etNumber) {


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

                    Log.d("thuc hien luu vao sharedPrefernces", "thuc hien luu vao sharedPrefernces");

                    editor.putString("phoneNumber_" + animal.getName(), phoneNumber);
                    editor.apply();

                    //? View phải cập nhật và hiện lên số điện thoại
//                    animal.setPhoneNumber(phoneNumber);
                    //!//////////////////////////////////////////////////
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


//                 sharedPreferences = context.getSharedPreferences("PhoneAnimals", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
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

}
