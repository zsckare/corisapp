package com.example.lizcoria.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lizcoria.myapplication.ApiConnections.ApiFolioRequests;
import com.example.lizcoria.myapplication.Models.FolioModel;
import com.example.lizcoria.myapplication.utils.CommonSettings;
import com.example.lizcoria.myapplication.vistas.ParticipanteActivity;
import com.example.lizcoria.myapplication.vistas.TalleristaActivity;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    EditText txt_folio;
    Button btn_participante, btn_tallerista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_folio = (EditText)findViewById(R.id.txt_folio);
        btn_participante=(Button)findViewById(R.id.btn_participante);
        btn_tallerista=(Button)findViewById(R.id.btn_tallerista);

        btn_participante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonSettings.folio=txt_folio.getText().toString();
                int tamaño=CommonSettings.folio.length();
                 String folio = txt_folio.getText().toString();
                checkFolio(folio);
                if (tamaño>=5){
                    Intent intent=new Intent(MainActivity.this, ParticipanteActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Ingresa un folio valido", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_tallerista.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonSettings.folio=txt_folio.getText().toString();
                int tamaño=CommonSettings.folio.length();
                if (tamaño>=5){
                    Intent intent=new Intent(MainActivity.this, TalleristaActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Ingresa un folio valido", Toast.LENGTH_SHORT).show();
                }
            }
        }));

    }

    public void checkFolio(String folio){
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(CommonSettings.BaseUrl)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        ApiFolioRequests api = adapter.create(ApiFolioRequests.class);
        api.checkFolio(folio, new Callback<FolioModel>() {
            @Override
            public void success(FolioModel folioModel, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

}
