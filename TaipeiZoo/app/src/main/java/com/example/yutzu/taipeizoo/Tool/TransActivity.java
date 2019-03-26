package com.example.yutzu.taipeizoo.Tool;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

  interface TransInterface {


      class TransTask extends AsyncTask<String, Void, String> {
          @Override
          protected void onPostExecute(String s) {
              super.onPostExecute(s);
              Log.d("JSON", s);

          }

          @Override
          protected String doInBackground(String... strings) {
              StringBuilder sb = new StringBuilder();
              try {
                  URL url = new URL(strings[0]);
                  BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                  String line = br.readLine();
                  while (line != null) {
                      Log.d("HTTP", line);
                      sb.append(line);
                      line = br.readLine();
                  }

              } catch (MalformedURLException e) {
                  e.printStackTrace();
              } catch (IOException e) {
                  e.printStackTrace();
              }
              return sb.toString();
          }
      }
  }
//    public void parseJSON(String s){
//
//    }


