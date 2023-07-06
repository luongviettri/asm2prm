package com.example.asm2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

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

