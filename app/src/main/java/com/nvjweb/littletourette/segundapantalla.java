package com.nvjweb.littletourette;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class segundapantalla extends AppCompatActivity {

    ImageView imgavatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgavatar=findViewById(R.id.imagenavatar);


        imgavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(segundapantalla.this,tercerapantalla.class);
                segundapantalla.this.startActivity(intent);


            }
        });
    }
}