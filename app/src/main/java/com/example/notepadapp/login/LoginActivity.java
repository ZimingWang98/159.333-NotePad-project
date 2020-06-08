package com.example.notepadapp.login;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notepadapp.R;
import com.example.notepadapp.database.DatabaseHelper;
import com.example.notepadapp.main.MainActivity;
//import from DtaBaseHelper class
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.notepadapp.database.DatabaseHelper.PASSWORD;
import static com.example.notepadapp.database.DatabaseHelper.USERNAME;
import static com.example.notepadapp.database.DatabaseHelper.USER_TABLE;

public class LoginActivity extends Activity implements View.OnClickListener{


    private ImageView mImageView;
    private EditText edPsw,edUser;
    private TextView register;
    private String username,psw;
    private CheckBox rememberPsw;
    private Button login;
    private DatabaseHelper mDb=new DatabaseHelper(this);
    //use to save data
    private SharedPreferences sharedPreferences;
    //use to write data
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        //getActionBar().hide();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(requestCode==RESULT_OK){
                    String username=data.getStringExtra("Username");
                    String psw=data.getStringExtra("Password");
                    edUser.setText(username);
                    edPsw.setText(psw);
                }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //case for clicking the register
            case R.id.register:
                //jump to register activity
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent,1);
                break;
                //case for clicking the login button
            case R.id.login_btn:
                //check the if enter the username and password or not
                if (edUser.getText().toString().equals("")||
                edPsw.getText().toString().equals("")){
                    //make a toast to reminder user enter username and password
                    Toast.makeText(LoginActivity.this,"Oops! Enter username and password!",Toast.LENGTH_SHORT).show();
                }else{
                    //read user information
                    readUser();

                }
                break;
                    
                
        }

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
    private void readUser() {
        username=edUser.getText().toString();
        psw=edPsw.getText().toString();
        editor=sharedPreferences.edit();
        String pswmd5=getStringMD5(psw);
        if (loginCheck(username,pswmd5)){
            if(rememberPsw.isChecked()){
                editor.putBoolean("rem_psw",true);
                editor.putString("Username",username);
                editor.putString("Password",psw);
            }else {
                editor.clear();
            }
            editor.apply();
            Toast.makeText(LoginActivity.this,username+" Welcome to your note",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("Username",username);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(LoginActivity.this,"Wrong username or password",Toast.LENGTH_SHORT ).show();
        }
    }

    private boolean loginCheck(String username, String psw) {
        SQLiteDatabase db = mDb.getWritableDatabase();
        String sql="select * from "+ USER_TABLE +" where "+ USERNAME +"=? and "+ PASSWORD + "=?";
        Cursor cursor=db.rawQuery(sql,new String[]{username,psw});
        if (cursor.moveToFirst()){
            cursor.close();
            return true;
        }else {
            return false;
        }
    }

    private void init(){
        //find the view
        mImageView=(ImageView)findViewById(R.id.user_image);
        edUser=(EditText)findViewById(R.id.et_username);
        edPsw=(EditText)findViewById(R.id.et_password);
        rememberPsw=(CheckBox)findViewById(R.id.remember_password);
        register=(TextView)findViewById(R.id.register);
        login=(Button)findViewById(R.id.login_btn);
        sharedPreferences= getSharedPreferences("mPref",MODE_PRIVATE);
        boolean isRem=sharedPreferences.getBoolean("rem_psw",false);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        if(isRem){
            String username=sharedPreferences.getString("Username","");
            String psw=sharedPreferences.getString("Password","");
            edUser.setText(username);
            edPsw.setText(psw);
            rememberPsw.setChecked(true);

        }

    }
}
