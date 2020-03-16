package com.example.notepadapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    private ViewPager screenPager;
    WelcomeViewPageAdapter welcomeViewPageAdapter;
    TabLayout tab;
    Button btnNext;
    int position=0;
    Button btnStarted;
    Animation btnAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //check the app is the first open or not
        if(restorePrefData()){
            Intent loginActivity=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(loginActivity);
        }
        setContentView(R.layout.activity_welcome);
        //hide action bar
        getSupportActionBar().hide();

        //just make a comment to test git
        //initial view
        tab=findViewById(R.id.tab_layout);
        btnNext=findViewById(R.id.btn_next);
        btnStarted=findViewById(R.id.btn_started);
        btnAnim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_animation);



        final List<ScreenItem> mList=new ArrayList<>();
        mList.add(new ScreenItem("Step   one","Register your own account",R.drawable.register));
        mList.add(new ScreenItem("Step   Two","Log into your account",R.drawable.login));
        mList.add(new ScreenItem("Step Three","Enjoy your personal secret note",R.drawable.enjoy));

        //set viewpager
        screenPager=findViewById(R.id.screen_pager);
        welcomeViewPageAdapter=new WelcomeViewPageAdapter(this,mList);
        screenPager.setAdapter(welcomeViewPageAdapter);

        tab.setupWithViewPager(screenPager);
        //next button click listener
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                position= screenPager.getCurrentItem();
                if(position<mList.size()){

                    position++;
                    screenPager.setCurrentItem(position);

                }
                if(position==mList.size()-1){
                    loadLastScreen();
                }


            }
        });
        //tab add change listener
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==mList.size()-1){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //Started button click listener
        btnStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open login activity
                Intent loginActivity=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(loginActivity);
                //use shared preferences to make sure welcome page already been checked
                savePrefsData();
                finish();
            }
        });
    }

    private boolean restorePrefData(){
        SharedPreferences pref=getApplicationContext().getSharedPreferences("mPrefs",MODE_PRIVATE);
        Boolean isWelcomeActivityOpenedBefore=pref.getBoolean("isWelcomeOpened",false);

        return isWelcomeActivityOpenedBefore;
    }
    private void savePrefsData() {

        SharedPreferences pref=getApplicationContext().getSharedPreferences("mPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putBoolean("isWelcomeOpened",true);
        editor.commit();

    }

    //show the Started button and hide the tab and next button
    private void loadLastScreen() {

        btnStarted.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        tab.setVisibility(View.INVISIBLE);
        //setup animation
        btnStarted.setAnimation(btnAnim);

    }
}
