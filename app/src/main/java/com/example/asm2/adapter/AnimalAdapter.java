package com.example.asm2.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm2.R;
import com.example.asm2.model.Animal;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalHolder> {
    private Context mContext;
    private List<Animal> animalList;

    private View.OnClickListener clickListener;

    public AnimalAdapter(List<Animal> animalList, Context mContext, View.OnClickListener onClickListener) {
        this.mContext = mContext;
        this.animalList = animalList;
        this.clickListener = onClickListener;

    }

    @NonNull
    @Override
    public AnimalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_animal, parent, false);
        return new AnimalHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalHolder holder, int position) {

        Animal animal = animalList.get(position);

        Bitmap bitmap = animal.getPhoto();

        holder.ivItem.setImageBitmap(bitmap);


        holder.ivItem.setOnClickListener(clickListener);


        //! đi vào kho sharedPreference để lấy dữ liệu về con vật này
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("FavoriteAnimals", Context.MODE_PRIVATE);
        boolean isFav = sharedPreferences.getBoolean("isFav_" + animal.getName(), false); //? tiến hành lấy dữ liệu để gán vào fav attribute
        animal.setFav(isFav);

        if (animal.isFav()) {
            holder.ivIcon.setImageResource(R.drawable.ic_favorite1);
            holder.ivIcon.setVisibility(View.VISIBLE);
        } else {
            holder.ivIcon.setImageResource(R.drawable.ic_favorite2);
            holder.ivIcon.setVisibility(View.GONE);
        }

        holder.ivItem.setTag(animal);

    }


    @Override
    public int getItemCount() {
        return animalList.size();
    }

    public class AnimalHolder extends RecyclerView.ViewHolder {
        //! khai báo view
        ImageView ivItem;
        ImageView ivIcon;


        //! gán view trong holder
        public AnimalHolder(@NonNull View itemView) {
            super(itemView);

            ivItem = itemView.findViewById(R.id.ivItem);
            ivIcon = itemView.findViewById(R.id.ivIcon);

        }
    }
}
