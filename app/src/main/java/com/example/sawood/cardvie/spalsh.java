package com.example.sawood.cardvie;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class spalsh extends AppCompatActivity {

   // private static int Splashtime = 1500;
   private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
       // ActionBar actionBar = getActionBar();
       // actionBar.hide();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(spalsh.this,Login.class);
                spalsh.this.startActivity(mainIntent);
                spalsh.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
