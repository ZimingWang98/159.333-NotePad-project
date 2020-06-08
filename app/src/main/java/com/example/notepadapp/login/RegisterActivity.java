package com.example.notepadapp.login;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import from DtaBaseHelper class
import com.example.notepadapp.R;
import com.example.notepadapp.database.DatabaseHelper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
   // private SharedPreferences sp;


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
       // sp = getSharedPreferences("config", MODE_PRIVATE);


    }

    public static String getStringMD5(String sourceStr) {
        String s = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //change the bytes array to BigInteger
            BigInteger bigInt = new BigInteger(1, md.digest(sourceStr.getBytes()));
            //use format to get 32-bit hexadecimal string
            s = String.format("%032x", bigInt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
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
        //MD5 for the 加密
        //String md5=getStringMD5(psw);
        values.put(PASSWORD,psw);
        db.insert(USER_TABLE,null,values);
        db.close();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case BACK:
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            case CONFIRM:
                username=ed_username.getText().toString();
                psw=ed_psw.getText().toString().trim();
                conpsw=ed_con_psw.getText().toString().trim();
                String pswmd5=getStringMD5(psw);
                String conpswmd5=getStringMD5(conpsw);
                Log.d("username+",username);
                Log.d("password",pswmd5);
                Log.d("conpassword",conpswmd5);
                //check user enter the three required information are not empty
                if(username.equals("")||psw.equals("")){
                    Toast.makeText(RegisterActivity.this,"Oops! Please enter the username, password",Toast.LENGTH_SHORT).show();
                }else if(conpsw.equals("")){
                    Toast.makeText(RegisterActivity.this,"Oops! The confirm password should not be empty",Toast.LENGTH_SHORT).show();
                }else{
                    if(username.length()>MAX_SIZE){
                        Toast.makeText(RegisterActivity.this,"Too many character",Toast.LENGTH_SHORT).show();
                    }else {
                        if(checkIsDataExist(username)){
                            Toast.makeText(RegisterActivity.this,"User already existed",Toast.LENGTH_SHORT).show();
                        }else{
                            if(pswmd5.equals(conpswmd5)){
                                registerUser(username,pswmd5);
                                Toast.makeText(RegisterActivity.this,username+" Welcome!",Toast.LENGTH_SHORT).show();
                                Intent intent1=new Intent(RegisterActivity.this,LoginActivity.class);
                                intent1.putExtra("Username",username);
                                intent1.putExtra("Password",psw);
                                setResult(RESULT_OK,intent1);
                                finish();
                            }else {
                                Toast.makeText(RegisterActivity.this,"Please confirm your enter password are same",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                break;
        }

    }

}
