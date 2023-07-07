package com.example.asm2.fragment;

import android.content.Context;
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

        String path = "path";
        boolean isFav = false;

        switch (animalType) {
            case "bird":
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_eagle)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_eagle)), "eagle", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_falcon)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_falcon)), "falcon", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_hawk)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_hawk)), "hawk", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_parrot)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_parrot)), "parrot", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_peacock)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_peacock)), "peacock", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_peguin)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_penguin)), "penguin", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_raven)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_raven)), "raven", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_sparrow)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_sparrow)), "sparrow", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_woodpecker)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_woodpecker)), "woodpecker", mContext.getString(R.string.detail), isFav));
                break;
            case "mammal":
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_cat)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_cat)), "cat", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_dog)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_dog)), "dog", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_hippotamus)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_hippo)), "hippo", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_lion)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_lion)), "lion", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_monkey)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_monkey)), "monkey", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_rabbit)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_rabbit)), "rabbit", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_tiger)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_tiger)), "tiger", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_turtle)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_turtle)), "turtle", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_zibra)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_zebra)), "zebra", mContext.getString(R.string.detail), isFav));
                break;
            case "sea":
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_crab)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_crab)), "crab", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_dolphin)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_dolphin)), "dolphin", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_jellyfish)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_jellyfish)), "jellyfish", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_octopus)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_octopus)), "octopus", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_red_snapper)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_red_snapper)), "red snapper", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_shark)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_shark)), "shark", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_squid)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_squid)), "squid", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_swordfish)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_swordfish)), "swordfish", mContext.getString(R.string.detail), isFav));
                listAnimals.add(new Animal(path, resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_whale)), resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_whale)), "whale", mContext.getString(R.string.detail), isFav));
                break;
            default:
                Log.d("something wrong", "something wrong");
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