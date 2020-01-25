package com.nvjweb.litletourettev1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;

public class primerapantalla extends AppCompatActivity {
    EditText ingresanombre, ingresaclave;
    Button btnlogin, btnregistrar;
    String avatar="avatar1";

    private static final String TAG = "Login";
    private static final String PREFS_KEY = "PREFS";
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primerapantalla);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ingresanombre=findViewById(R.id.ingresanombre);
        ingresaclave=findViewById(R.id.ingresaclave);
        btnregistrar=findViewById(R.id.btnregistrar);

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemintent = new Intent(primerapantalla.this, tercerapantalla.class);
                primerapantalla.this.startActivity(itemintent);

            }
        });

        db = FirebaseFirestore.getInstance();
        btnlogin=findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnectedToInternet()){
                    loguear();
                }else{
                    Toast.makeText(getApplicationContext(),"no hay internet", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void loguear(){
        db.collection("usuarios")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean flag=false;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getString("nombre").equals(""+ingresanombre.getText()) && document.getString("clave").equals(""+ingresaclave.getText())){
                                    flag=true;
                                    avatar=document.getString("avatar");

                                }

                            }
                            if(!flag){
                                Toast.makeText(getApplicationContext(),"datos incorrectos", Toast.LENGTH_LONG).show();


                            }else{
                                Intent itemintent = new Intent(primerapantalla.this, cuartapantalla.class);
                                primerapantalla.this.startActivity(itemintent);
                                guardarusuario(primerapantalla.this,"nombre", String.valueOf(ingresanombre.getText()));
                                guardarusuario(primerapantalla.this,"avatar", avatar);


                            }


                        }else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }




                    }
                });

    }
    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
    public static void guardarusuario(Context context, String keyPref, String valor) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putString(keyPref, valor);
        editor.commit();
    }
}






















