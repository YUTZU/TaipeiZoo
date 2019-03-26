package com.example.yutzu.taipeizoo;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yutzu.taipeizoo.Tool.Zone;
import com.squareup.picasso.Picasso;

public class ZooFragment extends android.support.v4.app.Fragment  implements LifecycleOwner {

    TextView nameTv, infoTv, categoryTv, menoTv;
    ImageView imageView;
    Zone zone;
    Button urlBtn;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewModelProviders.of(getActivity()).get(ZooViewModel.class).getZoneData().observe(this, new Observer<Zone>() {

            public void onChanged(@Nullable final Zone zone) {

                infoTv.setText(zone.getInfo());
                categoryTv.setText(zone.getCategory());
                urlBtn.setText("前往網站");
                urlBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uriUrl = Uri.parse(zone.getUrl());
                        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                        startActivity(launchBrowser);
                    }
                });
                menoTv.setText(zone.getMeno());
                Picasso.with(getActivity()).load(zone.getPicurl()).resize(350,200).into(imageView);
                if(zone.getPlants()!=null){
                    Log.d("queenie_plans",zone.getPlants().get(0).getName());
                }else{
                    String url_temp = "https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=f18de02f-b6c9-47c0-8cda-50efad621c14&q=" + zone.getName();
//                    new ZoneActivity.TransTask().execute(url_temp);
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        infoTv= (TextView)getView().findViewById(R.id.infoTv);
        categoryTv= (TextView)getView().findViewById(R.id.categoryTv);
        menoTv= (TextView)getView().findViewById(R.id.menoTv);
        urlBtn=(Button)getView().findViewById(R.id.urlBtn);
        imageView=(ImageView)getView().findViewById(R.id.image);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zoo, container , false);
        return view;
    }

}
