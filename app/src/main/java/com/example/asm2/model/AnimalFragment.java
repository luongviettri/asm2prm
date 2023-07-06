package com.example.asm2.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm2.R;
import com.example.asm2.dialog.ViewPhoneDialog;

public class AnimalFragment extends Fragment {
    //! khởi tạo các view
    private static final String ARG_ANIMAL = "animal";

    private ImageView ivFavorite, ivBackground, ivPhoneIcon;
    private TextView tvName, tvDetailText;

    private EditText etNumber;
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

        //!  khởi tạo các view và  gán giá trị
        initAndAssignVars(view);

        //! thiết lập icon back
        setBackIconForImageView(iv_menu);
        //! thiết lập hình trái tim
        updateFavoriteState();

        //! check xem con vật đã có sdt chưa, nếu có rồi thì hiện etNumber lên còn ko thì ẩn
        setPhoneNumberForAnimal(animal);

        //! thiết lập sự kiện cho nút điện thoại

        setEventForPhoneIcon(ivPhoneIcon);


        //! thiết lập onClick
        setFavoriteButton(ivFavorite, animal);

    }

    private void setEventForPhoneIcon(ImageView ivPhoneIcon) {


        ivPhoneIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPhoneDialog viewPhoneDialog = new ViewPhoneDialog();
                viewPhoneDialog.showAlertDialog(requireActivity(), animal, etNumber);

            }
        });


    }

    /**
     * !  để hiện trạng thái các con vật lên xml file
     */
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

    /**
     * ! hàm set nút back trên toolbar
     */
    private void setBackIconForImageView(ImageView imageView) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });
    }

    /**
     * ! hàm lấy sdt từ trong sharedPreferences và gán vào cho từng con vật
     */

    private void setPhoneNumberForAnimal(Animal animal) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("PhoneAnimals", Context.MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("phoneNumber_" + animal.getName(), "");

        animal.setPhoneNumber(phoneNumber);

        //? nếu animal này có số điện thoại thì cho  hiển thị lên
        if (!animal.getPhoneNumber().isEmpty()) {
            etNumber.setVisibility(View.VISIBLE);
            etNumber.setText(animal.getPhoneNumber());
        }
    }

    /**
     * ! hàm set logic cho nút tim
     */
    private void setFavoriteButton(ImageView imageView, Animal animal) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animal.setFav(!animal.isFav());

                saveFavoriteState(animal.isFav());  //? lưu lại trong sharedPreferences

                updateFavoriteState();
            }
        });
    }

    /**
     * ! hàm ánh xạ các giá trị
     */
    private void initAndAssignVars(View view) {
        ivFavorite = view.findViewById(R.id.ivFavorite);
        ivBackground = view.findViewById(R.id.ivBackground);
        tvName = view.findViewById(R.id.tvName);
        tvDetailText = view.findViewById(R.id.tvDetailText);
        iv_menu = view.findViewById(R.id.iv_menu);

        ivPhoneIcon = view.findViewById(R.id.ivPhone);
        etNumber = view.findViewById(R.id.etNumber);


        ivBackground.setImageBitmap(animal.getPhotoBg());
        iv_menu.setImageResource(R.drawable.ic_back);
        tvName.setText(animal.getName());
        tvDetailText.setText(animal.getContent());
    }


    public void updatePhoneNumber(String phoneNumber) {
        etNumber.setText(phoneNumber);
    }

}
