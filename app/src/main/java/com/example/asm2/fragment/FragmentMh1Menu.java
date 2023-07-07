package com.example.asm2.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.example.asm2.R;
import com.example.asm2.activity.MainActivity;
import com.example.asm2.adapter.AnimalAdapter;
import com.example.asm2.model.Animal;

import java.util.ArrayList;
import java.util.List;


public class FragmentMh1Menu extends Fragment {
    private Context mContext;
    private RecyclerView recyclerViewAnimal;
    private List<Animal> listAnimals;
    private DrawerLayout drawerLayout;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mh1_menu, container, false);
        initView(view);
        return view;
    }

    private void initView(View viewCreatedFragmentMH1) {

        drawerLayout = viewCreatedFragmentMH1.findViewById(R.id.drawerLayout);

        recyclerViewAnimal = viewCreatedFragmentMH1.findViewById(R.id.recyclerView_animal);

        //! cài chức năng cho nút menu trên toolbar
        setMenuButton(viewCreatedFragmentMH1);

        //! cài list animal dựa vào tùy chọn của user
        setListAnimal(viewCreatedFragmentMH1);

        //! Hiển thị danh sách ảnh lên RecyclerView
        setAdapterForRecyclerView();
    }

    /**
     * ! Hiển thị danh sách ảnh lên RecyclerView
     */
    private void setAdapterForRecyclerView() {
        if (listAnimals != null) {

            AnimalAdapter animalAdapter = new AnimalAdapter(listAnimals, mContext, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doCLickAnimal((Animal) v.getTag());
                }
            });

            recyclerViewAnimal.setAdapter(animalAdapter);

        }
    }


    /**
     * cài đặt list animal dựa vào tùy chọn của user
     *
     * @param viewCreatedFragmentMH1
     */
    private void setListAnimal(View viewCreatedFragmentMH1) {

        //! Hiển thị ảnh sea animal
        viewCreatedFragmentMH1.findViewById(R.id.iv_sea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCreatedFragmentMH1.startAnimation(AnimationUtils.loadAnimation(mContext, androidx.appcompat.R.anim.abc_fade_in));

                showAnimals("sea");
            }
        });

        //! Hiển thị ảnh mammal animal

        viewCreatedFragmentMH1.findViewById(R.id.iv_mammal).setOnClickListener(v1 -> {

            viewCreatedFragmentMH1.startAnimation(AnimationUtils.loadAnimation(mContext, androidx.appcompat.R.anim.abc_fade_in));

            showAnimals("mammal");

        });

        //! Hiển thị ảnh bird animal

        viewCreatedFragmentMH1.findViewById(R.id.iv_bird).setOnClickListener(v1 -> {

            viewCreatedFragmentMH1.startAnimation(AnimationUtils.loadAnimation(mContext, androidx.appcompat.R.anim.abc_fade_in));

            showAnimals("bird");

        });


    }


    /**
     * hàm cài đặt nút menu trên toolbar
     *
     * @param viewCreatedFragmentMH1
     */
    private void setMenuButton(View viewCreatedFragmentMH1) {
        viewCreatedFragmentMH1.findViewById(R.id.iv_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void doCLickAnimal(Animal animal) {

        //! Chuyển sang màn hình chi tiết
        MainActivity mainActivity = (MainActivity) mContext;

        mainActivity.showDetail(listAnimals, animal);
    }

    /**
     * hàm fix lỗi file ảnh quá lớn
     *
     * @param originalBitmap
     * @return
     */
    private Bitmap resizeBitmap(Bitmap originalBitmap) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();

        float scaleWidth = ((float) 200) / width;
        float scaleHeight = ((float) 200) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(originalBitmap, 0, 0, width, height, matrix, false);
    }


    private void showAnimals(String animalType) {

        listAnimals = new ArrayList<>();

        try {

            String[] list = mContext.getAssets().list("animal/" + animalType);


            for (String photo : list) {
                Bitmap photoB = BitmapFactory.decodeStream(mContext.getAssets().open("animal/" + animalType + "/" + photo));
                Bitmap photoBG = null;
                try {
                    photoBG = BitmapFactory.decodeStream(mContext.getAssets().open("bg_animal/" + animalType + "/" + photo.replace("ic_", "bg_")));
                } catch (Exception ignored) {
                }
                String name = photo;
                name = name.replace("ic_", "");
                name = name.substring(0, name.indexOf("."));
                String fileText = "description/" + animalType + "/des_" + name + ".txt";
                String content = "example contenttttttttttttttttttttttttttttttttttttttttttt";

                //! Học sinh tự code để đọc dữ liệu text từ đường dẫn fileText trong thư mục assets vào tham số conten
                //! Lấy thông tin động vật yêu thích đã lưu trữ trong file_savef của SharedPreference

                SharedPreferences sharedPreferences = mContext.getSharedPreferences("FavoriteAnimals", Context.MODE_PRIVATE);
                boolean isFav = sharedPreferences.getBoolean("isFav_" + name, false);

                //! Khởi tạo động vật
                String path = animalType + "/" + photo;

                Animal animal = new Animal(path, photoB, photoBG, name, content, isFav);
                //! Cho vào danh sách
                listAnimals.add(animal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //! Hiển thị danh sách ảnh lên RecyclerView

        AnimalAdapter animalAdapter = new AnimalAdapter(listAnimals, mContext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(mContext, androidx.appcompat.R.anim.abc_fade_in));
                doCLickAnimal((Animal) v.getTag());
            }
        });

        recyclerViewAnimal.setAdapter(animalAdapter);

        drawerLayout.closeDrawers();
    }
}