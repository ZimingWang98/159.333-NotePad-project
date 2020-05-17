package com.example.notepadapp.search;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.notepadapp.R;
import com.example.notepadapp.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.Nullable;

import static com.example.notepadapp.database.DatabaseHelper.CONTENT;
import static com.example.notepadapp.database.DatabaseHelper.DATE;
import static com.example.notepadapp.database.DatabaseHelper.ID;
import static com.example.notepadapp.database.DatabaseHelper.NOTE_TABLE;
import static com.example.notepadapp.database.DatabaseHelper.PHOTO;
import static com.example.notepadapp.database.DatabaseHelper.USERNAME;

public class SearchActivity extends Activity {

    private EditText etSearch;

    private Button btCurrentUser;

    private Button btAllUser;

    private ListView listView;

    private SearchAdapter searchAdapter;

    private List<Map<String, String>> mData = new ArrayList();

    private String usernameQuery;

    private Boolean isAllUser = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initData();
        bindViews();
        initViews();
        logDb();
    }

    private void initData() {
        usernameQuery = getIntent().getStringExtra("Username");
    }

    private void bindViews() {
        etSearch = findViewById(R.id.et_search);
        btCurrentUser = findViewById(R.id.bt_currentUser);
        btAllUser = findViewById(R.id.bt_allUser);
        listView = findViewById(R.id.listView);
    }

    private void initViews() {
        btCurrentUser.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        btAllUser.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search(s.toString());
            }
        });

        btCurrentUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllUser = false;
                btCurrentUser.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                btAllUser.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                search(etSearch.getText().toString());
            }
        });

        btAllUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllUser = true;
                btCurrentUser.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                btAllUser.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                search(etSearch.getText().toString());
            }
        });

        searchAdapter = new SearchAdapter(this, mData);
        listView.setAdapter(searchAdapter);
    }

    private void search(String text) {
        if (text == null) return;
        mData.clear();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor;
        if (isAllUser) {
            cursor = db.rawQuery("SELECT * FROM " + NOTE_TABLE +" where "+CONTENT+"=?", new String[]{text});
        }else{
            cursor = db.rawQuery("SELECT * FROM " + NOTE_TABLE +" where "+USERNAME+"=?"
                    +" and "+ CONTENT + "=?" , new String[]{usernameQuery, text});
        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ID));
            String userName = cursor.getString(cursor.getColumnIndex(USERNAME));
            String content = cursor.getString(cursor.getColumnIndex(CONTENT));
            String date = cursor.getString(cursor.getColumnIndex(DATE));
            String photo = cursor.getString(cursor.getColumnIndex(PHOTO));
            Map<String, String> map = new HashMap<>();
            map.put(ID, id+"");
            map.put(USERNAME, userName);
            map.put(CONTENT, content);
            map.put(DATE, date);
            map.put(PHOTO, photo);
            mData.add(map);
        }
        cursor.close();
        searchAdapter.notifyDataSetChanged();
    }

    private void logDb() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NOTE_TABLE, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ID));
            String userName = cursor.getString(cursor.getColumnIndex(USERNAME));
            String content = cursor.getString(cursor.getColumnIndex(CONTENT));
            String date = cursor.getString(cursor.getColumnIndex(DATE));
            String photo = cursor.getString(cursor.getColumnIndex(PHOTO));
            String s = String.format(Locale.getDefault(),"id=%d, username =%s, content=%s, date=%s, photo=%s",id,userName,content,date,photo);
            Log.d("db", s);
        }
        cursor.close();
    }
}