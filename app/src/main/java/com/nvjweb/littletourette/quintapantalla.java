package com.nvjweb.littletourette;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class quintapantalla extends AppCompatActivity {

    ImageView imgpina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgpina=findViewById(R.id.imgpina);


        imgpina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(quintapantalla.this,sextapantalla.class);
                quintapantalla.this.startActivity(intent);


            }
        });
    }
}
