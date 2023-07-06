package com.example.asm2.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.asm2.R;

public class AnimalFragment extends Fragment {
    //! khởi tạo các view
    private static final String ARG_ANIMAL = "animal";

    private ImageView ivFavorite, ivBackground;
    private TextView tvName, tvDetailText;

    private ImageView iv_menu;

    private Animal animal;

    public static AnimalFragment newInstance(Animal animal) {
        AnimalFragment animalFragment = new AnimalFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_ANIMAL, animal);
        animalFragment.setArguments(bundle);
        return animalFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            animal = (Animal) getArguments().getSerializable(ARG_ANIMAL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        //!  khởi tạo các view
        ivFavorite = view.findViewById(R.id.ivFavorite);
        ivBackground = view.findViewById(R.id.ivBackground);
        tvName = view.findViewById(R.id.tvName);
        tvDetailText = view.findViewById(R.id.tvDetailText);
        iv_menu = view.findViewById(R.id.iv_menu);
        //! gán giá trị


        ivBackground.setImageBitmap(animal.getPhotoBg());


        iv_menu.setImageResource(R.drawable.ic_back);

        tvName.setText(animal.getName());
        tvDetailText.setText(animal.getContent());
        iv_menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();


            }
        });

        //! thiết lập hình trái tim
        updateFavoriteState();

        //! thiết lập onClick
        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animal.setFav(!animal.isFav());

                saveFavoriteState(animal.isFav());  //? lưu lại trong sharedPreferences

                updateFavoriteState();
            }
        });
    }

    private void updateFavoriteState() {
        if (animal.isFav()) {
            ivFavorite.setImageResource(R.drawable.ic_favorite1);
        } else {
            ivFavorite.setImageResource(R.drawable.ic_favorite2);
        }
    }

    //! Lưu trạng thái yêu thích của con vật
    private void saveFavoriteState(boolean isFavorite) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("FavoriteAnimals", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isFav_" + animal.getName(), isFavorite);
        editor.apply();
    }


    //! Đọc trạng thái yêu thích của con vật
//    private void loadFavoriteState() {
//        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("FavoriteAnimals", Context.MODE_PRIVATE);
//        boolean isFav = sharedPreferences.getBoolean("isFav_" + animal.getName(), false);
//        animal.setFav(isFav);
//
//        if (animal.isFav()) {
//            ivFavorite.setImageResource(R.drawable.ic_favorite1);
//        } else {
//            ivFavorite.setImageResource(R.drawable.ic_favorite2);
//        }
//
//    }


}
