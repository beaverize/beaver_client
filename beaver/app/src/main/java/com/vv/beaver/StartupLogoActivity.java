package com.vv.beaver;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.vv.beaver.Beaver.BeaverListActivity;
import com.vv.beaver.Communicator.Connector;

import java.net.Socket;

public class StartupLogoActivity extends AppCompatActivity {
    CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_logo);
        // wait start logo
        final Intent intent = new Intent(this, BeaverListActivity.class);
        timer = new CountDownTimer(1000, 1000) {
            public void onFinish() {
                // When timer is finished
                // Execute your code here
                //setContentView(R.layout.login_screen);
                Log.d("main", "onCreate: before login screen");

                //setContentView(R.layout.login_screen);
                startActivity(intent);


                Log.d("main", "onCreate: after login screen");
            }

            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
                Log.d("CountDownTimer::onTick", "time=" + millisUntilFinished);
            }
        };
        timer.start();

        Connector connector = new Connector();
        Log.d("startup", "startup");

        //if(socket == null) {
        //    Toast.makeText(getApplicationContext(), "Server trouble", Toast.LENGTH_SHORT).show();
        //} else {
        //    Toast.makeText(getApplicationContext(), "Connected!!!", Toast.LENGTH_SHORT).show();
        //}


        //DbInterface.getInstance().loadDb(this);
        connector.start();
    }
    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }
}
