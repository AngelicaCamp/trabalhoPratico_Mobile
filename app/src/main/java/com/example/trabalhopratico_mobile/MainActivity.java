package com.example.trabalhopratico_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtText;
    private ImageButton btnProcessar;
    private ProgressBar pgbProgresso;
    private ImageView imgDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtText = findViewById(R.id.edtText);
        btnProcessar = findViewById(R.id.btnProcessar);
        btnProcessar.setOnClickListener(this);

        pgbProgresso = findViewById(R.id.pgbProgresso);
        imgDownload = findViewById(R.id.imgDownload);
    }

    @Override
    public void onClick(View view) {
        btnProcessar.setEnabled(false);
        pgbProgresso.setVisibility(View.VISIBLE);
        imgDownload.setVisibility(View.INVISIBLE);
        processaPedido();
    }

    public void processaPedido(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = edtText.getText().toString();
                    InputStream in = new URL(url).openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    in.close();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imgDownload.setImageBitmap(bitmap);
                            pgbProgresso.setVisibility(View.INVISIBLE);
                            imgDownload.setVisibility(View.VISIBLE);
                            btnProcessar.setEnabled(true);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}