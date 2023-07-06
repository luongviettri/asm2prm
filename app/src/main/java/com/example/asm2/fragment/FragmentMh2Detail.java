package com.example.asm2.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asm2.R;
import com.example.asm2.adapter.AnimalFragmentPagerAdapter;
import com.example.asm2.model.Animal;

import java.util.ArrayList;


public class FragmentMh2Detail extends Fragment {
    private ViewPager viewPager;
    private AnimalFragmentPagerAdapter animalFragmentPagerAdapter;
    private Context mContext;
    private ImageView ivFavorite, ivBackground, iv_back;
    private TextView tvName, tvDetailText;

    private Animal animal;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mh2_detail, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        //! nhận dữ liệu dc truyền qua từ activty
        Bundle bundle = getArguments();
        ArrayList<Animal> listAnimals = (ArrayList<Animal>) bundle.getSerializable("listAnimals");
         animal = (Animal) bundle.getSerializable("animal");

        viewPager = view.findViewById(R.id.viewPager);
        //! Khởi tạo adapter cho ViewPager
        animalFragmentPagerAdapter = new AnimalFragmentPagerAdapter(mContext, listAnimals, getChildFragmentManager());

        //! Thiết lập adapter cho ViewPager
        viewPager.setAdapter(animalFragmentPagerAdapter);

        //! Lấy vị trí của animal trong danh sách
        int position = listAnimals.indexOf(animal);

        //! Chuyển đến vị trí được chọn
        viewPager.setCurrentItem(position);


    }
}