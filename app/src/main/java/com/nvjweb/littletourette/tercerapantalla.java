package com.nvjweb.littletourette;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class tercerapantalla extends AppCompatActivity {

    ImageView btnempezar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnempezar=findViewById(R.id.btnempezar);


        btnempezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tercerapantalla.this,cuartapantalla.class);
                tercerapantalla.this.startActivity(intent);


            }
        });
    }
}
