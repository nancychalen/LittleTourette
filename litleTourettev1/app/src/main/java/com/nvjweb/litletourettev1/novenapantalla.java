package com.nvjweb.litletourettev1;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class novenapantalla extends AppCompatActivity {
    private static final String PREFS_KEY = "PREFS";
    Integer errores=0, aciertos=0;
    String avatar,nombre;
    ImageView imgavatar, regresar;
    TextView txtaciertos, txterrores,txtnivel;

    String path,idusuario;
    java.net.URL url;
    String responseText;
    StringBuffer response;
    ServicioWebReporte reporte;
    Boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novenapantalla);
        avatar=leerusuario(this,"avatar");
        nombre=leerusuario(this,"nombre");
        idusuario=leerusuario(this,"idusuario");


        txtaciertos=findViewById(R.id.txtaciertos);
        txterrores=findViewById(R.id.txterrores);
        txtnivel=findViewById(R.id.txtnivel);
        imgavatar=findViewById(R.id.avatar);
        switch (avatar){
            case "avatar1":
                imgavatar.setImageResource(R.drawable.avatar1);
                break;
            case "avatar11":
                imgavatar.setImageResource(R.drawable.avatar11);
                break;
            case "avatar10":
                imgavatar.setImageResource(R.drawable.avatar10);
                break;
            case "avatar2":
                imgavatar.setImageResource(R.drawable.avatar2);
                break;
            case "avatar7":
                imgavatar.setImageResource(R.drawable.avatar7);
                break;
            case "avatar9":
                imgavatar.setImageResource(R.drawable.avatar9);
                break;
            case "avatar3":
                imgavatar.setImageResource(R.drawable.avatar3);
                break;
            case "avatar5":
                imgavatar.setImageResource(R.drawable.avatar5);
                break;
            case "avatar6":
                imgavatar.setImageResource(R.drawable.avatar6);
                break;

        }
        if(isConnectedToInternet())
        {
            path = "https://littletourettebase.herokuapp.com/mostraractividades/"+idusuario;
            reporte = (ServicioWebReporte) new ServicioWebReporte().execute();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"no hay internet", Toast.LENGTH_LONG).show();
        }

        regresar=findViewById(R.id.regresar);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(novenapantalla.this,cuartapantalla.class);
                novenapantalla.this.startActivity(intent);
            }
        });


    }

    public static String leerusuario(Context context, String keyPref) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        return  preferences.getString(keyPref, "");
    }

    private class ServicioWebReporte extends AsyncTask<Integer, Integer, Boolean> {


        @Override
        protected void onPreExecute() {
        }
        @Override
        protected Boolean doInBackground(Integer... params) {
            return getWebServiceResponseData();
        }

        protected Boolean getWebServiceResponseData() {
            flag=false;
            try {
                url=new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    // Reading response from input Stream
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    String output;
                    response = new StringBuffer();

                    while ((output = in.readLine()) != null) {
                        response.append(output);
                    }
                    in.close();
                }}
            catch(Exception e){
                e.printStackTrace();
            }

            try {
                responseText = response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                reporte.cancel(true);
            }

            try {
                JSONArray jsonarray = new JSONArray(responseText);

                for (int i=0;i<jsonarray.length();i++){
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    flag=true;
                    errores=errores+ Integer.valueOf(jsonobject.getString("errores"));
                    aciertos=aciertos+ Integer.valueOf(jsonobject.getString("aciertos"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return flag;
        }

        @Override
        protected void onPostExecute(Boolean flag) {
            super.onPostExecute(flag);

            if(flag){
                txterrores.setText(""+errores);
                txtaciertos.setText(""+aciertos);
                if(Integer.valueOf(aciertos) < 10){
                    txtnivel.setText("1-En este nivel se recomiendsa:El uso de Carteles , fotos ,dibujosy señales acusticas");
                }else{
                    if(Integer.valueOf(aciertos) < 20){
                        txtnivel.setText("2-En este nivel se recomienda : Un guia de conducta, pensar en voz alta , repetirse la orden,autoinstrucciones y trabajar con la imaginaciòn");
                    }else{
                        if(Integer.valueOf(aciertos) < 35){
                            txtnivel.setText("3-En este nivel se recomienda habitos , rutinas ,Organizaciòn y hoarios");
                        }else{
                            if(Integer.valueOf(aciertos) < 50){
                                txtnivel.setText("4");
                            }else{
                                if(Integer.valueOf(aciertos) < 80){
                                    txtnivel.setText("5");
                                }else{
                                    txtnivel.setText("6");
                                }
                            }
                        }
                    }
                }
            }
        }
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
}


