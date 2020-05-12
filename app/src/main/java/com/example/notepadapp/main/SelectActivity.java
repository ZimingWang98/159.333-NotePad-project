package com.example.notepadapp.main;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.notepadapp.R;
import com.example.notepadapp.database.DatabaseHelper;
import com.example.notepadapp.login.LoginActivity;
import com.example.notepadapp.login.RegisterActivity;

public class SelectActivity extends Activity implements View.OnClickListener {
    private Button delete,back;
    private TextView tv;
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
        helper=new DatabaseHelper(this);
        db=helper.getWritableDatabase();
        back.setOnClickListener(this);
        delete.setOnClickListener(this);
        tv.setText(getIntent().getStringExtra(helper.CONTENT));
        tv.setTextColor(Color.rgb(255,255,255));
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

    private void deleteDate() {
        db.delete(helper.NOTE_TABLE,"_id="+getIntent()
                .getIntExtra(helper.ID,0),null);
    }
}
