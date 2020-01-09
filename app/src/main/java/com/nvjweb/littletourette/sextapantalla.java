package com.nvjweb.littletourette;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class sextapantalla extends AppCompatActivity {

    ImageView imgsandiaa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgsandiaa=findViewById(R.id.imgsandiaa);


        imgsandiaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sextapantalla.this,septimapantalla.class);
                sextapantalla.this.startActivity(intent);


            }
        });
    }
}