package com.example.mrfoodie;


import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

public class Welcome extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Thread startTimer = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                    Intent intent0 = new Intent(Welcome.this, Order_Type.class);
                    startActivity(intent0);
                    overridePendingTransition(R.anim.fadin, R.anim.fadout);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };
        startTimer.start();
    }
}
