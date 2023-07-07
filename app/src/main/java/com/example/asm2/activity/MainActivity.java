package com.example.asm2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.asm2.PhoneStateEmojiReceiver;
import com.example.asm2.R;
import com.example.asm2.fragment.FragmentMh1Menu;
import com.example.asm2.fragment.FragmentMh2Detail;
import com.example.asm2.model.Animal;

import java.io.Serializable;
import java.util.List;


/**
 * ! Main activity, nơi để các Fragment tương tác với nhau
 */
public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 101;


    private PhoneStateEmojiReceiver phoneStateEmojiReceiver; //! Receiver để nhận cuộc gọi


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //! gán giao diện
        initViews();
        //! xin quyền cho app
        requestPermissionForThisApp();

        //! đăng ký IncomingCallReceiver động
        registerIncomingCallReceiver();

    }


    /**
     * ! Gán giao diện
     */
    private void initViews() {
        FragmentMh1Menu fragmentMh1Menu = new FragmentMh1Menu();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ln_main, fragmentMh1Menu, null).commit();

    }

    /**
     * ! yêu cầu quyền cho ứng dụng
     */
    private void requestPermissionForThisApp() {
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_CALL_LOG,
            }, PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * ! xử lý sau khi người dùng cấp/ không cấp quyền
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //? Quyền đã được cấp
                Toast.makeText(MainActivity.this, "Quyền đã được cấp", Toast.LENGTH_SHORT).show();
            } else {
                //? Quyền bị từ chối
                Toast.makeText(MainActivity.this, "Vui lòng cấp quyền cho ứng dụng", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    /**
     * ! Đi vào show màn hình Detail của mỗi animal
     *
     * @param listAnimals
     * @param animal
     */
    public void showDetail(List<Animal> listAnimals, Animal animal) {
        FragmentMh2Detail fragmentMh2Detail = new FragmentMh2Detail();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        //! gửi dữ liệu qua fragment 2
        sendDataToDetailFragment(fragmentMh2Detail, listAnimals, animal);
        //! tạo animation
        createAnimation(fragmentTransaction, fragmentMh2Detail);
        //! add vào stack để có thể quay lại
        fragmentTransaction.addToBackStack(null);
        //! xác nhận và gửi đi
        fragmentTransaction.commit();

    }

    /**
     * ! tạo animation từ màn hình này sang fragment2
     *
     * @param fragmentTransaction
     * @param fragmentMh2Detail
     */
    public void createAnimation(FragmentTransaction fragmentTransaction, FragmentMh2Detail fragmentMh2Detail) {
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.fade_out);
        fragmentTransaction.replace(R.id.ln_main, fragmentMh2Detail, null);
    }

    /**
     * ! hàm gửi dữ liệu sang fragment detail
     *
     * @param fragmentMh2Detail
     * @param listAnimals
     * @param animal
     */
    public void sendDataToDetailFragment(FragmentMh2Detail fragmentMh2Detail, List<Animal> listAnimals, Animal animal) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("listAnimals", (Serializable) listAnimals);
        bundle.putSerializable("animal", animal);
        fragmentMh2Detail.setArguments(bundle);
    }

    /**
     * ! hàm đăng ký để nhận broadcast ( vì ko đăng ký qua manifest nếu api > 26 )
     */
    private void registerIncomingCallReceiver() {
        phoneStateEmojiReceiver = new PhoneStateEmojiReceiver();
        IntentFilter intentFilter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(phoneStateEmojiReceiver, intentFilter);
    }

    /**
     * ! hàm hủy đăng ký broadcast
     */

    private void unregisterIncomingCallReceiver() {
        if (phoneStateEmojiReceiver != null) {
            unregisterReceiver(phoneStateEmojiReceiver);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //! Hủy đăng ký IncomingCallReceiver khi Activity bị hủy
        unregisterIncomingCallReceiver();
    }
}

