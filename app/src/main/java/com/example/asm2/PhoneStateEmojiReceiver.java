package com.example.asm2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PhoneStateEmojiReceiver extends BroadcastReceiver {
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String action = intent.getAction();

        if (action != null && action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if (phoneNumber != null) {
                makeEmojiToast(context, phoneNumber);
                Log.d("IncomingCallReceiver", "Incoming call from: " + phoneNumber);
            }
        }
    }


    private void makeEmojiToast(Context context, String incomingNumber) {


        //! inflate layout

        View view = LayoutInflater.from(context).inflate(R.layout.item_toast, (ViewGroup) null);
        //! anh xa view
        ImageView ivToast = view.findViewById(R.id.ivToast);
        //! goi sharedPreferences
        SharedPreferences sharedPreferences2 = context.getSharedPreferences("PhoneAnimals___PhoneToImage", Context.MODE_PRIVATE);

        String imageFilePath = sharedPreferences2.getString(incomingNumber, "");


        //! neu co sdt==> cho phep hien emoji

        if (imageFilePath.isEmpty()) return;


        Bitmap bitmap = loadBitmapFromFile(imageFilePath);

        ivToast.setImageBitmap(bitmap);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();



    }

    private Bitmap loadBitmapFromFile(String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            try {
                // Đọc dữ liệu từ tệp tin thành một mảng byte
                FileInputStream inputStream = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                inputStream.read(data);
                inputStream.close();

                // Chuyển đổi mảng byte thành Bitmap
                return BitmapFactory.decodeByteArray(data, 0, data.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


}
