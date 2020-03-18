package com.example.notepadapp;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;

public class LoginActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getActionBar().hide();
    }

    @Override
    public void onClick(View view) {

    }
}
