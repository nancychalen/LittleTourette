package com.nvjweb.littletourette;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class cuartapantalla extends AppCompatActivity {

    ImageView btnempezarr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnempezarr=findViewById(R.id.btnempezarr);


        btnempezarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cuartapantalla.this,quintapantalla.class);
                cuartapantalla.this.startActivity(intent);


            }
        });
    }
}