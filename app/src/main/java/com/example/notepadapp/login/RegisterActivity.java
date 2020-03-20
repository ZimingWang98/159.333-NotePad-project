package com.example.notepadapp.login;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//import from DtaBaseHelper class
import com.example.notepadapp.R;
import com.example.notepadapp.database.DatabaseHelper;

import static com.example.notepadapp.database.DatabaseHelper.PASSWORD;
import static com.example.notepadapp.database.DatabaseHelper.USERNAME;
import static com.example.notepadapp.database.DatabaseHelper.USER_TABLE;

public class RegisterActivity extends Activity implements View.OnClickListener {
    private static final int MAX_SIZE=80;
    private static final int BACK= R.id.back;
    private static final int CONFIRM= R.id.confirm;
    private EditText ed_username,ed_psw,ed_con_psw;
    private TextView back;
    private Button confirm;
    private DatabaseHelper mDb;
    private String username,psw,conpsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        mDb=new DatabaseHelper(this);
        ed_username=(EditText)findViewById(R.id.register_username);
        ed_psw=(EditText)findViewById(R.id.register_psw);
        ed_con_psw=(EditText)findViewById(R.id.register_con_psw);
        confirm=(Button)findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
        back=(TextView)findViewById(R.id.back);
        back.setOnClickListener(this);

    }

    private boolean checkIsDataExist(String username){
        SQLiteDatabase db=mDb.getWritableDatabase();
        String sql="select * from "+USER_TABLE+" where "+ USERNAME + "=?";
        Cursor cursor=db.rawQuery(sql,new String[]{username});
        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }
        return false;

    }
    private void registerUser(String username,String psw){
        SQLiteDatabase db=mDb.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(USERNAME,username);
        values.put(PASSWORD,psw);
        db.insert(USER_TABLE,null,values);
        db.close();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case BACK:
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
        }

    }

}
