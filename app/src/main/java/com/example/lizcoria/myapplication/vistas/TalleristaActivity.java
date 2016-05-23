package com.example.lizcoria.myapplication.vistas;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.lizcoria.myapplication.ApiConnections.ApiFolioRequests;
import com.example.lizcoria.myapplication.Models.ContestantModel;
import com.example.lizcoria.myapplication.R;
import com.example.lizcoria.myapplication.utils.CommonSettings;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class TalleristaActivity extends AppCompatActivity {

    protected  static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE =0;

    private static int RESULT_LOAD_IMAGE = 1;
    Button btn_take,btnSelect,btnsubir;
    Uri imageUri;

    String image_path= "";
    ImageView imageView;

    Cloudinary cloudinary;
    HashMap config;
    FileInputStream fileInputStream;
    File file;
    MaterialDialog.Builder builder;
    MaterialDialog dialog;
    Map Result;
    EditText asistentes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tallerista);

        asistentes = (EditText) findViewById(R.id.txt_asistentes);
        imageView = (ImageView) findViewById(R.id.imgVwTaller);

        btnSelect = (Button)findViewById(R.id.btn_seleccion_taller);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


        btn_take = (Button)findViewById(R.id.btn_tomar_taller);
        btn_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"frame_"+String.valueOf(System.currentTimeMillis()+".jpg")));
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        builder = new MaterialDialog.Builder(this)
                .title("Espere")
                .progress(true, 0)
                .progressIndeterminateStyle(true);
        dialog = builder.build();

        config= new HashMap();
        config.put("cloud_name", "dttuj6iv1");
        config.put("api_key", "626489617795249");//I have changed the key and secret
        config.put("api_secret", "zdAbLdG6Ppy14iKkrG42W25syjA");

        cloudinary = new Cloudinary(config);
        btnsubir = (Button)findViewById(R.id.btnGuardar);
        btnsubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               subir();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            if (requestCode==CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
                Log.e("URI",imageUri.toString());
                image_path = imageUri.toString();
                Picasso.with(getApplicationContext()).load(imageUri).into(imageView);

                btnsubir.setVisibility(View.VISIBLE);

            }
            else if (resultCode==RESULT_CANCELED) {
            }
        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            image_path = picturePath;
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            btnsubir.setVisibility(View.VISIBLE);

        }
    }


    public void subir(){
        TypedFile typedFile = new TypedFile("multipart/form-data", new File(image_path));
        file = new File(image_path);
        try {
            fileInputStream = new FileInputStream(file);
            new Upload(cloudinary).execute();
            dialog.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class Upload extends AsyncTask<String, Void, String> {
        private Cloudinary mCloudinary;

        public Upload( Cloudinary cloudinary ) {
            super();
            mCloudinary = cloudinary;
        }
        @Override
        protected String doInBackground(String... params) {
            String response ="";
            try
            {
                Log.d("UPLOAD", "doInBackground: --------------------->");
                Result =  mCloudinary.uploader().upload(fileInputStream, ObjectUtils.emptyMap());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {

            getResult();
            super.onPostExecute(result);
        }
    }

    public void getResult(){

        Log.d("UPLOAD", "getResult: "+Result);
        String url_upload = (String) Result.get("url");

        serverpost(url_upload);

    }

    public void serverpost(String url_upload){

        String totalAsistentes = asistentes.getText().toString();
        ContestantModel contestantModel = new ContestantModel(0,url_upload, CommonSettings.workshop_id,1,totalAsistentes);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(CommonSettings.BaseUrl)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        ApiFolioRequests api = adapter.create(ApiFolioRequests.class);

        api.createPost(contestantModel, new Callback<ContestantModel>() {
            @Override
            public void success(ContestantModel contestantModel, Response response) {
                dialog.dismiss();
                new MaterialDialog.Builder(TalleristaActivity.this)
                        .title("Gracias por participar en el taller")

                        .positiveText("Cerrar")
                        .show();
                clean();
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.dismiss();
                new MaterialDialog.Builder(TalleristaActivity.this)
                        .title("Ah Ocurrido un error")
                        .content(error.getMessage())
                        .positiveText("Aceptar")
                        .show();
            }
        });
    }

    public void clean(){

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
