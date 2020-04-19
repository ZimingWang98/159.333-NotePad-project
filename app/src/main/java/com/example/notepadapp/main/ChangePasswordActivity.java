package com.example.notepadapp.main;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.notepadapp.R;
import com.example.notepadapp.database.DatabaseHelper;
import com.example.notepadapp.welcome.WelcomeActivity;

import static com.example.notepadapp.database.DatabaseHelper.USERNAME;
import static com.example.notepadapp.database.DatabaseHelper.PASSWORD;
import static com.example.notepadapp.database.DatabaseHelper.USER_TABLE;
public class ChangePasswordActivity extends Activity implements View.OnClickListener {
    private TextView back;
    private Button confirm;
    private EditText change_new_et,change_old_et;
    private String oldpsw,newpsw,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_psw);
        init();
    }


    private void init() {
        username=getIntent().getStringExtra("Username");
        change_old_et=(EditText)findViewById(R.id.change_old_et);
        change_new_et=(EditText)findViewById(R.id.change_new_et);
        confirm=(Button)findViewById(R.id.change_confirm);
        back=(TextView)findViewById(R.id.change_back);
        back.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.change_back:
                finish();
                break;
            case R.id.change_confirm:
                oldpsw=change_old_et.getText().toString().trim();
                newpsw=change_new_et.getText().toString().trim();
                if(oldpsw.equals("")||newpsw.equals(""))
                {
                    Toast.makeText(ChangePasswordActivity.this,"Cannot be empty",Toast.LENGTH_SHORT).show();
                }else{
                    if(oldpsw.equals(newpsw)){
                        Toast.makeText(ChangePasswordActivity.this,"New password should be different！",Toast.LENGTH_SHORT).show();
                    }else {
                        if(checkOldpsw(username,oldpsw)){
                            changePsw(username,newpsw);
                            finish();
                            Toast.makeText(ChangePasswordActivity.this,"New password save!",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(ChangePasswordActivity.this, WelcomeActivity.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(ChangePasswordActivity.this,"Oops, old password not match." +
                                    "Please enter your old password.",Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                break;
        }
    }

    private void changePsw(String username, String newpsw) {
        DatabaseHelper helper=new DatabaseHelper(this);
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(PASSWORD,newpsw);
        String [] arg={username};
        long row=db.update(USER_TABLE,cv,USERNAME+" =?",arg);
        db.close();
    }

    private boolean checkOldpsw(String username, String oldpsw) {
        DatabaseHelper helper=new DatabaseHelper(this);
        SQLiteDatabase db=helper.getWritableDatabase();
        String sql="select * from " + USER_TABLE+" where "+USERNAME+"=? and "+PASSWORD+" =?";
        Cursor cursor=db.rawQuery(sql,new String[]{username,oldpsw});
        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }else {
            cursor.close();
            return false;
        }
    }
}
