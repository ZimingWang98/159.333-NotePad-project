package com.example.notepadapp.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.notepadapp.R;
import com.example.notepadapp.database.DatabaseHelper;
import com.example.notepadapp.login.LoginActivity;
import com.example.notepadapp.search.SearchActivity;
import com.example.notepadapp.weather.WeatherActivity;
import com.google.android.material.navigation.NavigationView;

import static com.example.notepadapp.database.DatabaseHelper.USERNAME;
import static com.example.notepadapp.database.DatabaseHelper.NOTE_TABLE;

public class MainActivity extends Activity implements View.OnClickListener{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView menu;
    private TextView username;
    View head;
    private Button newNote;
    private ListView list;
    private MainViewAdapter mAdapter;
    private DatabaseHelper mHelper;
    private SQLiteDatabase db;
    private Cursor mCursor;
    private String usernameQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void onClick(View view) {
        Intent add =new Intent(this,AddContentActivity.class);
        switch (view.getId()){
            case R.id.menu:
                if(drawerLayout.isDrawerOpen(navigationView)){
                    drawerLayout.closeDrawer(navigationView);
                }else{
                    drawerLayout.openDrawer(navigationView);
                    username=(TextView)findViewById(R.id.tv_username);
                    username.setText(usernameQuery);
                }
                break;
            case R.id.newnote_btn:
                add.putExtra("key","1");
                add.putExtra("username",usernameQuery);
                startActivity(add);
        }

    }



    private void init(){
        usernameQuery=getIntent().getStringExtra("Username");
        drawerLayout=(DrawerLayout)findViewById(R.id.main_activity_drawer);
        navigationView=(NavigationView)findViewById(R.id.navigation_view);
        menu=(ImageView)findViewById(R.id.menu);
        head=navigationView.getHeaderView(0);
        menu.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(navigationView);
                switch (item.getItemId()){
                    case R.id.action_change_psw:
                        Intent intent=new Intent(MainActivity.this,ChangePasswordActivity.class);
                        intent.putExtra("Username",usernameQuery);
                        startActivity(intent);
                        break;
                    case R.id.action_login:
                        Intent intent1=new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.action_exist:
                        finish();
                        break;
                    case R.id.action_weather:
                        Intent w=new Intent(MainActivity.this, WeatherActivity.class);
                        startActivity(w);
                        break;
                    case R.id.action_search:
                        Intent s=new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(s);
                        break;

                }
                return true;
            }
        });
        list=(ListView)findViewById(R.id.list);
        newNote=(Button)findViewById(R.id.newnote_btn);
        newNote.setOnClickListener(this);
        mHelper=new DatabaseHelper(this);
        db=mHelper.getReadableDatabase();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCursor.moveToPosition(i);
                Intent intent=new Intent(MainActivity.this,SelectActivity.class);
                intent.putExtra(mHelper.ID,mCursor.getInt(mCursor.getColumnIndex(mHelper.ID)));
                intent.putExtra(mHelper.USERNAME,mCursor.getString(mCursor.getColumnIndex(mHelper.USERNAME)));
                intent.putExtra(mHelper.CONTENT,mCursor.getString(mCursor.getColumnIndex(mHelper.CONTENT)));
                intent.putExtra(mHelper.DATE,mCursor.getString(mCursor.getColumnIndex(mHelper.DATE)));
                startActivity(intent);
            }
        });
    }
    private void select(String usernameQuery){
        //select user from the database
        String[] arg=new String[]{usernameQuery};
        mCursor=db.rawQuery("SELECT * FROM " + NOTE_TABLE +" where "+USERNAME+"=?", new String[]{usernameQuery});
        mAdapter=new MainViewAdapter(this,mCursor);
        list.setAdapter(mAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        select(usernameQuery);
    }
}
