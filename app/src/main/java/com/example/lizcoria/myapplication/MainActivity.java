package com.example.lizcoria.myapplication;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lizcoria.myapplication.ApiConnections.ApiFolioRequests;
import com.example.lizcoria.myapplication.Models.WorkshopModel;
import com.example.lizcoria.myapplication.utils.CommonSettings;
import com.example.lizcoria.myapplication.vistas.ParticipanteActivity;
import com.example.lizcoria.myapplication.vistas.TalleristaActivity;

import java.util.List;

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

                if (tamaño>=4){
                    checkFolio(folio,0);
                }else{
                    Toast.makeText(MainActivity.this, "Ingresa un folio valido", Toast.LENGTH_SHORT).show();
                    Snackbar.make(v,"Ingresa un Folio Valido",Snackbar.LENGTH_LONG).show();
                }

            }
        });

        btn_tallerista.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonSettings.folio=txt_folio.getText().toString();
                int tamaño=CommonSettings.folio.length();
                String folio = txt_folio.getText().toString();
                if (tamaño>=4){
                    checkFolio(folio,1);
                }else{
                    Snackbar.make(v,"Ingresa un Folio Valido",Snackbar.LENGTH_LONG).show();

                }
            }
        }));

    }

    public void checkFolio(String folio, final int tipo){
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(CommonSettings.BaseUrl)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        ApiFolioRequests api = adapter.create(ApiFolioRequests.class);
        api.checkFolio(folio, new Callback<List<WorkshopModel>>() {
            @Override
            public void success(List<WorkshopModel> workshopModels, Response response) {

                try {
                    CommonSettings.workshop_id=workshopModels.get(0).getId();
                    //Toast.makeText(MainActivity.this, "workshop"+CommonSettings.workshop_id, Toast.LENGTH_SHORT).show();
                    if (tipo==0){
                       Intent intent=new Intent(MainActivity.this, ParticipanteActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.left_in,R.anim.left_out);

                    }
                    if (tipo==1){
                       Intent intentt =new Intent(MainActivity.this, TalleristaActivity.class);
                        startActivity(intentt);
                        overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

}
