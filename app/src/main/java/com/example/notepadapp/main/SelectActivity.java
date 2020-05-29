package com.example.notepadapp.main;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notepadapp.R;
import com.example.notepadapp.database.DatabaseHelper;


public class SelectActivity extends Activity implements View.OnClickListener {
    private Button delete,back;
    private TextView tv;
    private ImageView mImageView;
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        init();
    }

    private void init() {
        back= findViewById(R.id.select_back);
        delete= findViewById(R.id.select_delete);
        tv= findViewById(R.id.select_tv);
        mImageView = findViewById(R.id.imageViewSelect);
        helper=new DatabaseHelper(this);
        db=helper.getWritableDatabase();
        back.setOnClickListener(this);
        delete.setOnClickListener(this);
        tv.setText(getIntent().getStringExtra(helper.CONTENT));
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        tv.setTextColor(Color.rgb(255,255,255));
        displayImage(getIntent().getStringExtra(helper.PHOTO));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.select_delete:
                deleteDate();
                finish();
                break;
            case R.id.select_back:
                finish();
                break;
        }
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mImageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteDate() {
        db.delete(helper.NOTE_TABLE,"_id="+getIntent()
                .getIntExtra(helper.ID,0),null);
    }
}
