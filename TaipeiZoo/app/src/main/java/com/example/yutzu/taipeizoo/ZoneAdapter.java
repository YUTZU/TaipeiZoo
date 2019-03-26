package com.example.yutzu.taipeizoo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yutzu.taipeizoo.Tool.RoundTransform;
import com.example.yutzu.taipeizoo.Tool.Zone;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ZoneAdapter extends RecyclerView.Adapter<ZoneAdapter.ViewHolder>  implements LifecycleOwner {

    private Context context;
    private List<Zone> zoneList;

    ZoneAdapter(Context context, List<Zone>zoneList){
        this.context=context;
        this.zoneList=zoneList;
    }


    @NonNull
    @Override
    public ZoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Zone zone = zoneList.get(position);
        Picasso.with(this.context).load(zone.getPicurl()).resize(350,200).transform(new RoundTransform(this.context)).into(holder.imageView);
        holder.nameTv.setText(zone.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ZooActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("zone",zoneList.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return zoneList.size();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv, idTv;
        LinearLayout linearLayout;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTv=(TextView)itemView.findViewById(R.id.nameTv);
            imageView=(ImageView)itemView.findViewById(R.id.bgImg);

        }
    }
}
