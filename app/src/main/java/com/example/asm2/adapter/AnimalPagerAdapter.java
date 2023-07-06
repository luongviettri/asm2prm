package com.example.asm2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.asm2.R;
import com.example.asm2.model.Animal;

import java.util.ArrayList;
import java.util.List;

public class AnimalPagerAdapter extends PagerAdapter {
    private ImageView ivFavorite, ivBackground, iv_back;
    private TextView tvName, tvDetailText;

    private Context context;
    private List<Animal> animalList;


    public AnimalPagerAdapter(Context context, List<Animal> animalList) {
        this.context = context;
        this.animalList = animalList;


    }

    @Override
    public int getCount() {
        return animalList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        Animal animal = animalList.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);

        //! khởi tạo

        ivFavorite = view.findViewById(R.id.ivFavorite);
        ivBackground = view.findViewById(R.id.ivBackground);
        tvName = view.findViewById(R.id.tvName);
        tvDetailText = view.findViewById(R.id.tvDetailText);

        //! gán giá trị vào các holder

        ivBackground.setImageBitmap(animal.getPhoto());
        tvName.setText(animal.getName());
        tvDetailText.setText(animal.getContent());
        //? xử lý trái tim

        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animal.setFav(!animal.isFav());
                setFav(animal);

                notifyDataSetChanged();

            }
        });

        setFav(animal);

        container.addView(view);

        return view;
    }


    public void setFav(Animal animal) {
        if (animal.isFav()) {
            Log.d("true", "true");
            ivFavorite.setImageResource(R.drawable.ic_favorite1);
        } else {
            Log.d("false", "false");
            ivFavorite.setImageResource(R.drawable.ic_favorite2);
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
