package com.example.notepadapp.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.notepadapp.R;
import com.example.notepadapp.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddContentActivity extends Activity implements View.OnClickListener {

    String content;
    private Button add_save,add_cancel,photo;
    private EditText add_et;
    private DatabaseHelper mHelper;
    private SQLiteDatabase db;
    private String username;
    private ImageView mImageView;
    private String photoPath;
    public static final int REQUEST_SYSTEM_PIC = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);
        init();
    }

    private void init() {
        //init the view

        photo= findViewById(R.id.photo);
        content=getIntent().getStringExtra("key");
        username=getIntent().getStringExtra("username");
        add_save= findViewById(R.id.add_save);
        add_cancel= findViewById(R.id.add_cancel);
        add_et= findViewById(R.id.et_add);
        add_save.setOnClickListener(this);
        add_cancel.setOnClickListener(this);
        photo.setOnClickListener(this);
        mHelper=new DatabaseHelper(this);
        db=mHelper.getWritableDatabase();
        mImageView = findViewById(R.id.imageViewAdd);
        photoPath="";
    }
    private String getDate(){
        SimpleDateFormat format= new SimpleDateFormat("dd/MM/yyyy");
        Date current=new Date();
        return format.format(current);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_save:
                add();
                finish();
                break;
            case R.id.add_cancel:
                finish();
                break;
            case R.id.photo:
                if (ContextCompat.checkSelfPermission(AddContentActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddContentActivity.this, new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openGallery();
                }
                break;
        }
    }

    private void openGallery() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_SYSTEM_PIC);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SYSTEM_PIC && resultCode == RESULT_OK && null != data) {
            if (Build.VERSION.SDK_INT >= 23) {
                handleImageOnKitkat(data);
            } else {
                handleImageBeforeKitkat(data);
            }
        }
    }

    @TargetApi(19)
    private void handleImageOnKitkat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content:" +
                        "//downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);

    }

    private void handleImageBeforeKitkat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);

    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        photoPath = path;
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mImageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private void add() {
        ContentValues contentValues=new ContentValues();
        contentValues.put(mHelper.USERNAME,username);
        contentValues.put(mHelper.CONTENT,add_et.getText().toString());
        contentValues.put(mHelper.PHOTO, photoPath);
        contentValues.put(mHelper.DATE,getDate());
        db.insert(mHelper.NOTE_TABLE,null,contentValues);
        db.close();
    }
}
