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
    Button btnSave, btnDelete;
    Context context;


    public void showAlertDialog(final Context context, Animal animal, EditText etNumber) {
        this.context = context;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("PhoneAnimals", Context.MODE_PRIVATE);
        //! gán view
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.alert_dialog_custom_view, null);

        initView(view);

        //! lấy dữ liệu từ sharedPreferences để xử lý giá trị cho view
        String phoneNumber = sharedPreferences.getString("phoneNumber_" + animal.getName(), "");

        //! gán giá trị cho các view
        assignValueToView(phoneNumber, animal);

        //! Dialog
        //? Thiết lập view của dialog
        builder.setView(view);

        //? Tạo dialog
        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);

        //? Xử lý sự kiện cho các button trong dialog
        handleEvents(btnSave, btnDelete, sharedPreferences, animal, etNumber, etPhoneNumber, dialog);

        //! Hiển thị dialog
        dialog.show();

    }

    /**
     * hàm xử lý sự kiện
     *
     * @param btnSave
     * @param btnDelete
     * @param sharedPreferences
     * @param animal
     * @param etNumber
     * @param etPhoneNumber
     * @param dialog
     */
    public void handleEvents(Button btnSave, Button btnDelete, SharedPreferences sharedPreferences, Animal animal, EditText etNumber, EditText etPhoneNumber, AlertDialog dialog) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumber = etPhoneNumber.getText().toString();
                if (!phoneNumber.isEmpty()) {

                    etNumber.setVisibility(View.VISIBLE);

                    //! nếu sdt cũ và mới là khác nhau thì phải xóa đi dữ liệu cũ trong sharedPreferences

                    String oldPhoneNumber = animal.getPhoneNumber();

                    if (!oldPhoneNumber.equals(phoneNumber)) {
                        SharedPreferences sharedPreferences2 = context.getSharedPreferences("PhoneAnimals___PhoneToImage", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                        editor2.remove(oldPhoneNumber);
                        editor2.apply();
                    }

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
    }

    /**
     * assign value
     *
     * @param phoneNumber
     * @param animal
     */
    public void assignValueToView(String phoneNumber, Animal animal) {
        if (!phoneNumber.isEmpty()) {
            etPhoneNumber.setText(phoneNumber);
        }
        ivIcon.setImageBitmap(animal.getPhoto());
    }

    /**
     * ánh xạ view
     *
     * @param view
     */
    public void initView(View view) {
        ivIcon = view.findViewById(R.id.ivIcon);
        etPhoneNumber = view.findViewById(R.id.etPhoneNumber);
        btnSave = view.findViewById(R.id.btnSave);
        btnDelete = view.findViewById(R.id.btnDelete);
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
