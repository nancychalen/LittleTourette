package com.nvjweb.litletourettev1;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class segundapantalla extends AppCompatActivity {
    private static final String PREFS_KEY ="PREFS";
    FirebaseFirestore db;
    ImageView registrar;
    String avatar;
    EditText ingresanombre,ingresaclave,txtedad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.segundapantalla);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        db = FirebaseFirestore.getInstance();

        avatar= getIntent().getStringExtra("avatar");
        //Toast.makeText(getApplicationContext(), avatar, Toast.LENGTH_LONG).show();

        registrar=findViewById(R.id.registrar);
        ingresanombre=findViewById(R.id.ingresanombre);
        ingresaclave=findViewById(R.id.ingresaclave);
        txtedad=findViewById(R.id.txtedad);

        registrar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(isConnectedToInternet())
                {
                    empezar();
                }else{
                    Toast.makeText(getApplicationContext(),"no hay internet", Toast.LENGTH_LONG).show();

                }
            }
        });

        txtedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.txtedad:
                        showDatePickerDialog();
                        break;
                }
            }
        });





    }

    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if(info != null)
                for (int i = 0; i < info.length; i++)
                     if (info[i].getState() == NetworkInfo.State.CONNECTED){
                         return true;

                    }


        }
        return false;

    }

    public void guardarusuario(Context context, String keyPref, String valor){
        SharedPreferences settings = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putString(keyPref, valor);
        editor.commit();



    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                String formattedMonth= String.valueOf(month);
                String formattedDayOfMonth= String.valueOf(day);
                if(month < 10){
                    formattedMonth = "0" + month;

                }
                if(day<10){
                    formattedDayOfMonth = "0" + day;

                }

                final String selectedDate = formattedDayOfMonth + "/" + formattedMonth + "/" + year;
                EditText etPlannedDate = (EditText) findViewById(R.id.txtedad);
                etPlannedDate.setText(selectedDate);




            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void empezar(){
        Map<String, Object> user = new HashMap<>();
        user.put("nombre", ""+ingresanombre.getText());
        user.put("clave", ""+ingresaclave.getText());
        user.put("nacimiento", ""+txtedad.getText());
        user.put("avatar", avatar);

        db.collection("usuarios")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent intent = new Intent(segundapantalla.this, cuartapantalla.class);
                        guardarusuario(segundapantalla.this,"nombre",""+ingresanombre.getText());
                        guardarusuario(segundapantalla.this,"avatar",""+avatar);
                        segundapantalla.this.startActivity(intent);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("tag", "Error adding document", e);
                        Toast.makeText(getApplicationContext(), "no se pudo registrar", Toast.LENGTH_LONG).show();


                    }
                });
    }

}
