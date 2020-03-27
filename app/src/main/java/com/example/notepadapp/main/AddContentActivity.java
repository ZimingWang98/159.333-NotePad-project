package com.example.notepadapp.main;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.notepadapp.R;
import com.example.notepadapp.database.DatabaseHelper;
import com.example.notepadapp.gallery.GalleryActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddContentActivity extends Activity implements View.OnClickListener {

    String content;
    private Button add_save,add_cancel,photo;
    private EditText add_et;
    private DatabaseHelper mHelper;
    private SQLiteDatabase db;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);
        init();
    }

    private void init() {

        photo=(Button)findViewById(R.id.photo);
        content=getIntent().getStringExtra("key");
        username=getIntent().getStringExtra("username");
        add_save=(Button)findViewById(R.id.add_save);
        add_cancel=(Button)findViewById(R.id.add_cancel);
        add_et=(EditText)findViewById(R.id.et_add);
        add_save.setOnClickListener(this);
        add_cancel.setOnClickListener(this);
        mHelper=new DatabaseHelper(this);
        db=mHelper.getWritableDatabase();

    }
    private String getDate(){
        SimpleDateFormat format= new SimpleDateFormat("dd/mm/yyyy");
        Date current=new Date();
        String date=format.format(current);
        return date;
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
                openGallery();
                break;
        }
    }

    private void openGallery() {
        Intent intent= new Intent(AddContentActivity.this, GalleryActivity.class);
        startActivity(intent);
    }

    private void add() {
        ContentValues contentValues=new ContentValues();
        contentValues.put(mHelper.USERNAME,username);
        contentValues.put(mHelper.CONTENT,add_et.getText().toString());
        contentValues.put(mHelper.DATE,getDate());
        db.insert(mHelper.NOTE_TABLE,null,contentValues);

    }
}
