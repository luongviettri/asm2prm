package com.example.asm2.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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


    private void showAnimals(String animalType) {

        //! tạo list animal
        listAnimals = createAnimalList(animalType);

        //! Hiển thị list animal lên RecyclerView
        displayListToRecyclerView();
    }

    /**
     * hiển thị lên recyclerView
     */

    public void displayListToRecyclerView() {
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

    /**
     * xử lý tạo animal
     */

    public ArrayList<Animal> createAnimalList(String animalType) {
        listAnimals = new ArrayList<Animal>();

        try {

            AssetManager assetManager = mContext.getAssets();
            String[] list = assetManager.list("animal/" + animalType); //! list sẽ chứa 9 string tên của động vật

            for (String photo : list) {
                //! chuẩn bị tài nguyên để khởi tạo animal: path, photoIcon, photoBackground, name, content, isFav

                String path = animalType + "/" + photo;

                Bitmap photoIcon = handlePhotoIcon(animalType, photo, assetManager);

                Bitmap photoBackground = handlePhotoBackground(animalType, photo, assetManager);

                String name = handleName(photo);

                String content = handleDescription(assetManager, animalType, name);

                boolean isFav = handleFavorite(name);


                Animal animal = new Animal(path, photoIcon, photoBackground, name, content, isFav);

                //! Cho vào danh sách

                listAnimals.add(animal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ArrayList<Animal>) listAnimals;
    }

    /**
     * xử lý thuộc tính favorite của animal
     *
     * @param name
     * @return
     */
    public boolean handleFavorite(String name) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("FavoriteAnimals", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isFav_" + name, false);
    }

    public String handleDescription(AssetManager assetManager, String animalType, String name) {
        String description = "";
        String fileText = "description/" + animalType + "/des_" + name.toLowerCase() + ".txt";

        try (InputStream inputStream = assetManager.open(fileText); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            StringBuilder stringBuilder = new StringBuilder();

            //! Đọc từng dòng từ tệp tin .txt
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            //! Hiển thị nội dung đọc được
            description = stringBuilder.toString();
        } catch (IOException e) {
            Log.d("khong the doc file description", "khong the doc file description");
            e.printStackTrace();
        }

        return description;
    }


    /**
     * xử lý ảnh icon cho animal
     *
     * @param animalType
     * @param photo
     * @return
     * @throws IOException
     */
    public Bitmap handlePhotoIcon(String animalType, String photo, AssetManager assetManager) throws IOException {
        String path = "animal/" + animalType + "/" + photo;
        return BitmapFactory.decodeStream(assetManager.open(path));
    }

    /**
     * xử lý name cho animal
     */
    public String handleName(String photo) {
        String name = photo;
        name = name.replace("ic_", "");
        name = name.substring(0, name.indexOf("."));

        name = capitalizeFirstLetter(name);
        return name;
    }


    /**
     * xử lý background  để thỏa mãn cả png và jpg
     *
     * @param animalType
     * @param photo
     * @return
     * @throws IOException
     */
    public Bitmap handlePhotoBackground(String animalType, String photo, AssetManager assetManager) throws IOException {
        Bitmap photoBackground = null;
        String backgroundPath = "bg_animal/" + animalType + "/" + photo.replace("ic_", "bg_");
        try {
            photoBackground = BitmapFactory.decodeStream(assetManager.open(backgroundPath));
        } catch (Exception e) {
            //! nếu vào đây thì background image đang khác định dạng so với icon ( png != jpg or ngược lại) => chỗ này sửa đuôi file
            if (backgroundPath.contains(".png")) {
                backgroundPath = backgroundPath.replace("png", "jpg");
                photoBackground = BitmapFactory.decodeStream(assetManager.open(backgroundPath));
            } else {
                photoBackground = BitmapFactory.decodeStream(assetManager.open("bg_animal/" + animalType + "/" + photo.replace("jpg", "png")));
            }

        }
        return photoBackground;
    }


    /**
     * hàm  viết hoa chữ đầu tiên
     *
     * @param input
     * @return
     */
    public String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}