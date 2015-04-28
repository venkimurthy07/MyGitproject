package com.example.venkatesh.mygitproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class MainActivity extends ActionBarActivity {

    Button upload;
    EditText name;
    EditText mobile;
    static ImageView imageView;
    static Bitmap bitmap;
Button submit;
    static Bitmap thumbnail;
    private final int PICTURE_TAKEN_FROM_CAMERA = 1;
    private final int PICTURE_TAKEN_FROM_GALLERY = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
        name=(EditText)findViewById(R.id.editText);
        mobile=(EditText)findViewById(R.id.editText2);
        imageView=(ImageView)findViewById(R.id.imageView2);
        upload=(Button)findViewById(R.id.button);
        submit=(Button)findViewById(R.id.button3);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().length()==0)
                {
                    name.setError("Enter Name");
                }
                else if(mobile.getText().toString().length()==0)
                {
                    mobile.setError("Enter mobile");
                }

                else {
                    Intent i = new Intent(MainActivity.this, Idcard.class);
                    i.putExtra("name", name.getText().toString());
                    i.putExtra("mobile", mobile.getText().toString());
                    startActivity(i);
                }
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


getPictureFromGallery();

            }
        });
//venki changed file
        //santhosh imported the project and changed this line
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private void getPictureFromGallery(){
    /*
    //This allows to select the application to use when selecting an image.
    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
    i.setType("image/*");
    startActivityForResult(Intent.createChooser(i, "Escoge una foto"), PICTURE_TAKEN_FROM_GALLERY);
    */

        //This takes images directly from gallery
        Intent gallerypickerIntent = new Intent(Intent.ACTION_PICK);
        gallerypickerIntent.setType("image/*");
        startActivityForResult(gallerypickerIntent, PICTURE_TAKEN_FROM_GALLERY);

      /*  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(intent, PICTURE_TAKEN_FROM_CAMERA);*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap takenPictureData = null;

        switch(requestCode){

            case PICTURE_TAKEN_FROM_CAMERA:
                if(resultCode== Activity.RESULT_OK) {

                    File f = new File(Environment.getExternalStorageDirectory().toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            //hi
                            break;
                        }
                    }
                    try {

                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);

                        imageView.setImageBitmap(bitmap);

                        String path = android.os.Environment
                                .getExternalStorageDirectory()
                                + File.separator
                                + "Phoenix" + File.separator + "default";
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        try {
                            outFile = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                            outFile.flush();
                            outFile.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                   // takenPictureData = handleResultFromCamera(data);
                }
                break;
            case PICTURE_TAKEN_FROM_GALLERY:
                if(resultCode==Activity.RESULT_OK)

                {
                   // takenPictureData = handleResultFromChooser(data);

                    Uri selectedImage = data.getData();
                    String[] filePath = { MediaStore.Images.Media.DATA };
                    Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                   thumbnail = (BitmapFactory.decodeFile(picturePath));
              //      Log.w("path of image from gallery......******************.........", ""+picturePath+"");
                   imageView.setImageBitmap(thumbnail);
                   /* Uri selectedImageUri = data.getData();
                    String[] projection = { MediaStore.MediaColumns.DATA };
                    Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                            null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                    cursor.moveToFirst();

                    String selectedImagePath = cursor.getString(column_index);

                    Bitmap bm;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(selectedImagePath, options);
                    final int REQUIRED_SIZE = 200;
                    int scale = 1;
                    while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                            && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                        scale *= 2;
                    options.inSampleSize = scale;
                    options.inJustDecodeBounds = false;
                    bm = BitmapFactory.decodeFile(selectedImagePath, options);
                    Log.e("image",""+bm);
                    imageView.setImageBitmap(bm);*/

                }
                break;
        }

        /*//And show the result in the image view.
        if(takenPictureData!=null){
            imageView.setImageBitmap(takenPictureData);
        }*/
    }

    //AUXILIAR

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
