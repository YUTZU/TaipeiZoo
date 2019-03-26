package com.example.yutzu.taipeizoo;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yutzu.taipeizoo.Tool.Plant;
import com.example.yutzu.taipeizoo.Tool.RoundTransform;
import com.example.yutzu.taipeizoo.Tool.Zone;
import com.squareup.picasso.Picasso;


public class PlantFragment extends DialogFragment implements LifecycleOwner {
    TextView nameTv,name_enTv,name_latinTv, breifTv, functionTv, featureTv, familyTv, nicknameTv;
    ImageView imageView;
    LinearLayout layout;
    ZooViewModel zooViewModel;
    ImageButton closeBtn;
    Plant plant;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout=(LinearLayout)getView().findViewById(R.id.layout);
        name_enTv=(TextView)getView().findViewById(R.id.name_enTv);
        nameTv=(TextView)getView().findViewById(R.id.nameTv);
        name_latinTv=(TextView)getView().findViewById(R.id.name_latinTv);
        breifTv=(TextView)getView().findViewById(R.id.breifTv);
        functionTv=(TextView)getView().findViewById(R.id.functionTv);
        featureTv=(TextView)getView().findViewById(R.id.featureTv);
        familyTv=(TextView)getView().findViewById(R.id.familyTv);
        nicknameTv=(TextView)getView().findViewById(R.id.nicknameTv);
        imageView=(ImageView)getView().findViewById(R.id.imageView);
        zooViewModel = ViewModelProviders.of(getActivity()).get(ZooViewModel.class);
        closeBtn=(ImageButton)getView().findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        zooViewModel = ViewModelProviders.of(getActivity()).get(ZooViewModel.class);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zooViewModel.hidePlantView();
            }
        });


        zooViewModel.getPlantData().observe(this, new Observer<Plant>() {
            @Override
            public void onChanged(@Nullable Plant plant) {
                Picasso.with(getContext()).load(plant.getPicUrl()).resize(600,400).transform(new RoundTransform(getContext())).into(imageView);
                nameTv.setText(plant.getName());
                name_enTv.setText(plant.getName_en());
                name_latinTv.setText(plant.getName_latin());
                nicknameTv.setText(plant.getNickName());
                featureTv.setText(plant.getFeature());
                familyTv.setText(plant.getFamily());
                breifTv.setText(plant.getBrief());
                functionTv.setText(plant.getFunction());

            }


        });
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant, container , false);
        return view;
    }
}
