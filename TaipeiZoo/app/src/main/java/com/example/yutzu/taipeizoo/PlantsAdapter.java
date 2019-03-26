package com.example.yutzu.taipeizoo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yutzu.taipeizoo.Tool.CircleTransform;
import com.example.yutzu.taipeizoo.Tool.Plant;
import com.example.yutzu.taipeizoo.Tool.RoundTransform;
import com.example.yutzu.taipeizoo.Tool.Zone;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.ViewHolder> implements LifecycleOwner {

    private Context context;
    private List<Plant> plantList;
    private ZooViewModel zooViewModel;
    PlantsAdapter(Context context, List<Plant>plantList){
        this.context=context;
        this.plantList=plantList;
    }


    @NonNull
    @Override
    public PlantsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.plant_view, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Plant plant = plantList.get(position);

        if (plant.getPicUrl()!=null) {
            try {
                Picasso.with(this.context).
                        load(plant.getPicUrl()).
                        resize(400,400).
                        transform(new RoundTransform(this.context)).
                        into(holder.imageView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        zooViewModel = ViewModelProviders.of((FragmentActivity) context).get(ZooViewModel.class);
                        zooViewModel.init(plant);

//                        Log.d("queenie",plant.getBrief());
//                        PlantFragment fragment2 = new PlantFragment();
//                        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
//                                .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.enter_from_right)
//                                .replace(R.id.fragment_container, fragment2)
//                                .commit();
//                        zooViewModel.showPlantView();

                        FragmentTransaction ft =((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                        Fragment prev = ((FragmentActivity)context).getSupportFragmentManager().findFragmentByTag("dialog");
                        if (prev != null) {
                            ft.remove(prev);
                        }
                        ft.addToBackStack(null);
                        DialogFragment dialogFragment = new PlantFragment();
                        dialogFragment.show(ft, "dialog");

//                        zooViewModel.showPlantView();








                    }
                });

            }catch (Exception e){
                holder.imageView.setImageResource(R.drawable.ic_launcher_background);
            }

        }


    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.imageView);
        }
    }
}
