package com.example.venkatesh.mygitproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.AccessibleObject;

/**
 * Created by venkatesh on 28/4/15.
 */
public class Idcard extends Activity {

    TextView name;
    TextView mobile;
    ImageView imageView;
    Button download;
    RelativeLayout capture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
setContentView(R.layout.idcard);
        name=(TextView)findViewById(R.id.textView);
        capture=(RelativeLayout)findViewById(R.id.capture);
        capture.setDrawingCacheEnabled(true);
        mobile=(TextView)findViewById(R.id.textView2);
        imageView=(ImageView)findViewById(R.id.imageView);

        Bundle data=getIntent().getExtras();
        name.setText(data.getString("name").toString());
        mobile.setText(data.getString("mobile"));
        imageView.setImageBitmap(MainActivity.thumbnail);
        download=(Button)findViewById(R.id.button2);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureScreen();
            }
        });
    }
    private void captureScreen() {
        //View v = capture.g
        //v.setDrawingCacheEnabled(true);
        Bitmap bmp = Bitmap.createBitmap(capture.getDrawingCache());
        capture.setDrawingCacheEnabled(false);
        try {
            FileOutputStream fos = new FileOutputStream(new File(Environment
                    .getExternalStorageDirectory().toString(), "SCREEN"
                    + System.currentTimeMillis() + ".png"));
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
