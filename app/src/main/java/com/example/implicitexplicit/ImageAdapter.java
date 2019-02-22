package com.example.implicitexplicit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder>{
    private JSONArray myValues;
    private Context context;

    public ImageAdapter (JSONArray myValues,Context context){
            this.myValues= myValues;
            this.context=context;
            }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagecard, parent, false);
            return new MyViewHolder(listItem);
            }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

            try {
                byte [] encodeByte= Base64.decode(myValues.getJSONObject(position).getString("firstName"),Base64.DEFAULT);
                Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                holder.image.setImageBitmap(bitmap);
                holder.time.setText(myValues.getJSONObject(position).getString("date"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

    }

    @Override
    public int getItemCount() {
            return myValues.length();
            }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView time;
        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            time = itemView.findViewById(R.id.time);
        }
    }
}
