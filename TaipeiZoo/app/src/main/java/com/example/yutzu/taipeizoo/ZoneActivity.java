package com.example.yutzu.taipeizoo;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class ZoneActivity extends AppCompatActivity {
    TextView nameTv, infoTv, categoryTv, menoTv;
    ImageView imageView;
    Zone zone;
    Button urlBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoo);
        init();
        zone = null;

        if(getIntent().hasExtra("zone")){
//            nameTv.setText(zone.getName());

            zone = (Zone) getIntent().getSerializableExtra("zone");
            Log.d("queenie",zone.getName());
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
            Picasso.with(this).load(zone.getPicurl()).resize(350,200).into(imageView);
            if(zone.getPlants()!=null){
                Log.d("queenie_plans",zone.getPlants().get(0).getName());
            }else{
                String url_temp = "https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=f18de02f-b6c9-47c0-8cda-50efad621c14&q=" + zone.getName();
                new TransTask().execute(url_temp);
            }


        }

    }


    class TransTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            parseJSON(s);
        }

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
        Log.d("Queenie",s);
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
                    zone.setPlants(plantList);

                }
//                zoneRv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
//                zoneRv.setAdapter(new ZoneAdapter(this, zoneList));







        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void init(){
//        nameTv= (TextView)findViewById(R.id.nameTv);
        infoTv= (TextView)findViewById(R.id.infoTv);
        categoryTv= (TextView)findViewById(R.id.categoryTv);
        menoTv= (TextView)findViewById(R.id.menoTv);
        urlBtn=(Button)findViewById(R.id.urlBtn);
        imageView=(ImageView)findViewById(R.id.image);

    }
}
