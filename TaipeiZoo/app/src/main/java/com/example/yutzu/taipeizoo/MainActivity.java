package com.example.yutzu.taipeizoo;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yutzu.taipeizoo.Tool.Zone;

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

public class MainActivity extends AppCompatActivity  {
    List<Zone> zoneList;
    TextView nameTv, menoTv, infoTv;
    RecyclerView zoneRv;
    ProgressBar progressBar;
    TextView showTv;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zoneList = new ArrayList<>();
        zoneRv=(RecyclerView)findViewById(R.id.zoneRv);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        showTv=(TextView)findViewById(R.id.showTv);
        showTv.setText("資料載入中...");
        new TransTask().execute("https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a");
    }

    class TransTask extends AsyncTask<String, Integer, String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);
            showTv.setVisibility(View.INVISIBLE);
            parseJSON(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder sb = new StringBuilder();
            try{
                Thread.sleep(1000);
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

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            showTv.setText("Running..."+ values[0]);
            progressBar.setProgress(values[0]);
        }
    }

    public void parseJSON(String s){
        try {

            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("results");



            if(jsonArray.getJSONObject(0).has("E_Pic_URL")) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    Zone zone = new Zone(jsonArray.getJSONObject(i).getString("E_Pic_URL"),
                            jsonArray.getJSONObject(i).getString("E_Info"),
                            Integer.parseInt(jsonArray.getJSONObject(i).getString("E_no")),
                            jsonArray.getJSONObject(i).getString("E_Category"),
                            jsonArray.getJSONObject(i).getString("E_Name"),
                            jsonArray.getJSONObject(i).getString("E_Memo"),
                            Integer.parseInt(jsonArray.getJSONObject(i).getString("_id")),
                            jsonArray.getJSONObject(i).getString("E_URL"));


                    zoneList.add(zone);
                    Log.d("queenie", zone.getName());

                }
                zoneRv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                zoneRv.setAdapter(new ZoneAdapter(this, zoneList));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
