package com.example.asm2.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.asm2.R;
import com.example.asm2.model.Animal;
import com.example.asm2.model.AnimalFragment;

public class ViewPhoneDialog {
     ImageView ivIcon;
     EditText etPhoneNumber;


    public  void showAlertDialog(final Context context, Animal animal) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //! Tạo đối tượng LayoutInflater từ context
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.alert_dialog_custom_view, null);

        ivIcon = view.findViewById(R.id.ivIcon);
        etPhoneNumber = view.findViewById(R.id.etPhoneNumber);

        if (!animal.getPhoneNumber().isEmpty()) {
            etPhoneNumber.setText(animal.getPhoneNumber());
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


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //? Xử lý khi người dùng chọn "Yes"
                String phoneNumber = etPhoneNumber.getText().toString();
                if (!phoneNumber.isEmpty()) {
                    Log.d("thuc hien luu vao sharedPrefernces", "thuc hien luu vao sharedPrefernces");
                    //? cập nhật vào kho sharedPrefences
                    SharedPreferences sharedPreferences =  context.getSharedPreferences("PhoneAnimals", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("phoneNumber_" + animal.getName(), phoneNumber);
                    editor.apply();

                    //? View phải cập nhật và hiện lên số điện thoại
                    animal.setPhoneNumber(phoneNumber);

                }

                dialog.dismiss(); // Đóng dialog
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //? Xử lý khi người dùng chọn "No"
                Log.d("thuc hien remove sharedPrefernces", "thuc hien remove sharedPrefernces");
                dialog.dismiss(); // Đóng dialog
            }
        });

        //! Hiển thị dialog
        dialog.show();

    }

}
