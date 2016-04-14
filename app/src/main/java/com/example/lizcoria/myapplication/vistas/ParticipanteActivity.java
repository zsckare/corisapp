package com.example.lizcoria.myapplication.vistas;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lizcoria.myapplication.R;

import java.io.File;

public class ParticipanteActivity extends AppCompatActivity {

    protected  static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE =0;

    private static int RESULT_LOAD_IMAGE = 1;
    Button btn_take,btnSelect;
    Uri imageUri;

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participante);

        btn_take = (Button)findViewById(R.id.btn_tomar);


        btn_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"frame_"+String.valueOf(System.currentTimeMillis()+".jpg")));
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);

                startActivityForResult(intent,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });
        btnSelect = (Button)findViewById(R.id.btn_seleccion);

        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            if (requestCode==CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
                //Bundle extras = data.getExtras();

                Log.e("URI",imageUri.toString());

                //Bitmap bmp = (Bitmap)extras.get("data");
                Toast.makeText(ParticipanteActivity.this, ""+imageUri.toString(), Toast.LENGTH_SHORT).show();
               // imageView.setImageBitmap(bmp);

            }
            else if (resultCode==RESULT_CANCELED) {
                Toast.makeText(ParticipanteActivity.this, "Error", Toast.LENGTH_SHORT).show();
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

            ImageView imageView = (ImageView) findViewById(R.id.iViewParticipante);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }
}
