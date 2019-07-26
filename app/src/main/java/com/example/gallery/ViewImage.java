package com.example.gallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ViewImage extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        Intent intent = getIntent();
        String file = intent.getStringExtra("img");

        imageView =  findViewById(R.id.imageView);
        //imageView.setImageURI(Uri.parse(file));
        //Loading image from below url into imageView
        Glide.with(getApplicationContext())
                .load(file)
                .into(imageView);
    }


}