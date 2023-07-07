package com.example.asm2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.asm2.R;
import com.example.asm2.fragment.FragmentMh1Menu;
import com.example.asm2.fragment.FragmentMh2Detail;
import com.example.asm2.model.Animal;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {

        FragmentMh1Menu fragmentMh1Menu = new FragmentMh1Menu();

        getSupportFragmentManager().beginTransaction()

                .replace(R.id.ln_main, fragmentMh1Menu, null).commit();

        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_CALL_LOG,
            }, 101);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp
                Toast.makeText(MainActivity.this, "Quyền đã được cấp", Toast.LENGTH_SHORT).show();
            } else {
                // Quyền bị từ chối
                // Xử lý khi người dùng từ chối cấp quyền
                Toast.makeText(MainActivity.this, "Please allow this permission to use features of the app", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    /**
     * đi vào show detail infor của animal
     *
     * @param listAnimals
     * @param animal
     */
    public void showDetail(List<Animal> listAnimals, Animal animal) {


        FragmentMh2Detail fragmentMh2Detail = new FragmentMh2Detail();

        //! gửi dữ liệu
        Bundle bundle = new Bundle();

        bundle.putSerializable("listAnimals", (Serializable) listAnimals);
        bundle.putSerializable("animal", animal);

        fragmentMh2Detail.setArguments(bundle);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.fade_out);    //! tạo animation

        fragmentTransaction.replace(R.id.ln_main, fragmentMh2Detail, null);

        //! add vào stack để có thể quay lại
        fragmentTransaction.addToBackStack(null);         //! add vào stack để có thể quay lại
        fragmentTransaction.commit();


    }

}

