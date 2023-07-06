package com.example.asm2.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.asm2.model.Animal;
import com.example.asm2.model.AnimalFragment;

import java.util.List;

public class AnimalPagerAdapter2 extends FragmentPagerAdapter {

    private Context context;
    private List<Animal> animalList;


    public AnimalPagerAdapter2(Context context, List<Animal> animalList, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
        this.animalList = animalList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Animal animal = animalList.get(position);
        return AnimalFragment.newInstance(animal);
    }

    @Override
    public int getCount() {
        return animalList.size();
    }
}
