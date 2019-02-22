package com.example.implicitexplicit;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button but2;
    ImageView image;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private JSONArray ja;
    private JSONObject jo;
    String CHANNEL_ID = "my_channel_01";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        but2 = findViewById(R.id.but2);
        image = findViewById(R.id.image);
        ja = new JSONArray();
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(i.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(i,1,null);
                }
            }
        });


    }
    @Override
    protected void onActivityResult(int requestcode,int resultcode, Intent data){
        if (requestcode==1){
            if(resultcode == RESULT_OK){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_HH-mm-ss");
                String currentDateandTime = sdf.format(new Date());
                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                String temp = Base64.encodeToString(b, Base64.DEFAULT);
                jo = new JSONObject();
                try {
                    jo.put("firstName", temp);
                    jo.put("date", currentDateandTime);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ja.put(jo);

                mRecyclerView = (RecyclerView) findViewById(R.id.recycle_MainActivity);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

                mAdapter = new ImageAdapter(ja,MainActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }
}

