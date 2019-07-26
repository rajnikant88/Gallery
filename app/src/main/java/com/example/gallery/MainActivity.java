package com.example.gallery;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    GridView gridView;
    ArrayList<File> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();

        list = getImageFiles(Environment.getExternalStorageDirectory());

        gridView =  findViewById(R.id.gridView);
        gridView.setAdapter(new GridAdapter());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getApplicationContext(), ViewImage.class).putExtra("img", list.get(i).toString()));
            }
        });

    }

    class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.grid_item, viewGroup, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            //imageview.setImageURI(Uri.parse(getItem(i).toString()));

            //Loading image from below url into imageView
            Glide.with(getApplicationContext())
                    .load(getItem(i).toString())
                    .into(imageView);
            return view;
        }
    }

    ArrayList<File> getImageFiles(File root) {
        ArrayList<File> list = new ArrayList();
        File[] files = root.listFiles();
        for (int i = 0; i < files.length; i++) {
            if(files[i].isDirectory()) {
                list.addAll(getImageFiles(files[i]));
            } else {
                if(files[i].getName().endsWith(".jpg") || files[i].getName().endsWith(".png") || files[i].getName().endsWith(".gif")) {
                    list.add(files[i]);
                }
            }
        }
        return  list;
    }

    private void checkPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                //Requesting permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override //Override from ActivityCompat.OnRequestPermissionsResultCallback Interface
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                }
                return;
            }
        }
    }

    private ActivityManager.MemoryInfo getMemoryInfo() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }


}

