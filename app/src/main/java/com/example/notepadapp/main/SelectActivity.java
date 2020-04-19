package com.example.notepadapp.main;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.notepadapp.R;
import com.example.notepadapp.database.DatabaseHelper;

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
        back=(Button)findViewById(R.id.select_back);
        delete=(Button)findViewById(R.id.select_delete);
        tv=(TextView)findViewById(R.id.select_tv);
        helper=new DatabaseHelper(this);
        db=helper.getWritableDatabase();
        back.setOnClickListener(this);
        delete.setOnClickListener(this);
        tv.setText(getIntent().getStringExtra(helper.CONTENT));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.select_delete:
                deleteDate();
                finish();
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
