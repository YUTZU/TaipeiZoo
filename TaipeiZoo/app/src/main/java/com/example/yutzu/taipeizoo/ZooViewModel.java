package com.example.yutzu.taipeizoo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.yutzu.taipeizoo.Tool.Plant;
import com.example.yutzu.taipeizoo.Tool.Zone;

public class ZooViewModel extends ViewModel {
    private MutableLiveData<Zone> zoneData;
    private MutableLiveData<Plant> plantData;
    private MutableLiveData<FrameLayout> frameLayoutMutableLiveData;

    public void init(Zone zone)
    {
        zoneData = new MutableLiveData<>();
        zoneData.setValue(zone);
    }
    public void init(Plant plant)
    {
        plantData = new MutableLiveData<>();
        plantData.setValue(plant);
    }
    public void setPlantData(Plant plant)
    {
        plantData.setValue(plant);

    }
    public void setFragment(FrameLayout fragment){
        frameLayoutMutableLiveData = new MutableLiveData<>();
        frameLayoutMutableLiveData.setValue(fragment);
        Log.d("sss","true");
    }
    public void showPlantView(){
        frameLayoutMutableLiveData.getValue().setVisibility(View.VISIBLE);
    }
    public void hidePlantView(){
        frameLayoutMutableLiveData.getValue().setVisibility(View.INVISIBLE);
    }

    public LiveData<Plant> getPlantData() {
        return plantData;
    }
    public void setZoneData(Zone zone) {
        zoneData.setValue(zone);

    }
    public LiveData<Zone> getZoneData() {
        return zoneData;
    }
    public LiveData<FrameLayout> getFragLayout() {
        return frameLayoutMutableLiveData;
    }


}
