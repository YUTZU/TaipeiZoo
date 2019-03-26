package com.example.yutzu.taipeizoo;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yutzu.taipeizoo.Tool.Plant;
import com.example.yutzu.taipeizoo.Tool.Zone;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlantListFragment  extends android.support.v4.app.Fragment  implements LifecycleOwner {
    RecyclerView plantsRv;
    FrameLayout frameLayout;
    ZooViewModel zooViewModel;
    ProgressBar progressBar;
    TextView showTv;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        zooViewModel = ViewModelProviders.of(getActivity()).get(ZooViewModel.class);
        zooViewModel.setFragment(frameLayout);

        String url_temp = "https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=f18de02f-b6c9-47c0-8cda-50efad621c14&q=" + zooViewModel.getZoneData().getValue().getName();
        new TransTask().execute(url_temp);

//        ViewModelProviders.of(getActivity()).get(ZooViewModel.class).setFragment(frameLayout);
//        ViewModelProviders.of(getActivity()).get(ZooViewModel.class).getZoneData().observe(this, new Observer<Zone>() {
//            public void onChanged(Zone zone) {
//            String url_temp = "https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=f18de02f-b6c9-47c0-8cda-50efad621c14&q=" + zone.getName();
//            new TransTask().execute(url_temp);
//
////                zooViewModel = ViewModelProviders.of(getActivity()).get(ZooViewModel.class);
////                zooViewModel.setFragment(frameLayout);
//
//            }
//        });
    }
    class TransTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);
            showTv.setVisibility(View.INVISIBLE);
            parseJSON(s);        }

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder sb = new StringBuilder();
            try{
                URL url = new URL(strings[0]);
                BufferedReader sr = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = sr.readLine();
                while (line!=null){
                    Log.d("Http",line);
                    sb.append(line);
                    line = sr.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }
    }


    public void parseJSON(String s){
        List<Plant> plantList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                Plant plant = new Plant(jsonArray.getJSONObject(i).getString("F_Name_Ch"),
                        jsonArray.getJSONObject(i).getString("F_Name_En"),
                        jsonArray.getJSONObject(i).getString("F_Name_Latin"),
                        jsonArray.getJSONObject(i).getString("F_Pic01_URL"),
                        jsonArray.getJSONObject(i).getString("F_AlsoKnown"),
                        jsonArray.getJSONObject(i).getString("F_Feature"),
                        jsonArray.getJSONObject(i).getString("F_Family"),
                        jsonArray.getJSONObject(i).getString("F_Function&Application"),
                        jsonArray.getJSONObject(i).getString("F_Brief"));

                plantList.add(plant);
            }

            plantsRv.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
            plantsRv.setAdapter(new PlantsAdapter(getActivity(), plantList));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        plantsRv=(RecyclerView)getActivity().findViewById(R.id.plantsRv);
        frameLayout=(FrameLayout)getActivity().findViewById(R.id.fragment_container);
        progressBar=(ProgressBar)getActivity().findViewById(R.id.progressBar);
        showTv=(TextView)getActivity().findViewById(R.id.showTv);
        showTv.setText("資料載入中...");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plantlist, container , false);
        return view;
    }
}
