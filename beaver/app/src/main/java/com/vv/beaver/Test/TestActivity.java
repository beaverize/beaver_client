package com.vv.beaver.Test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.vv.beaver.Communicator.AsyncUpdateServer;
import com.vv.beaver.Communicator.UpdateCode;
import com.vv.beaver.Communicator.UpdateInfo;
import com.vv.beaver.R;

public class TestActivity extends AppCompatActivity {


    private static final Integer TEST_UPDATE_CODE_OFFSET = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TestActivity", "onCreate: start");
        super.onCreate(savedInstanceState);
        Log.d("TestActivity", "onCreate: stage0");
        setContentView(R.layout.activity_test);
        Log.d("TestActivity", "onCreate: end");

    }

    public void onClickSendTest(View view) {
        EditText update_code_view   = (EditText) findViewById(R.id.editTextUpdateCode   );
        EditText beaver_id_view     = (EditText) findViewById(R.id.editTextBeaverId     );
        EditText meal_id_view       = (EditText) findViewById(R.id.editTextMealId       );
        UpdateInfo update_info = new UpdateInfo(
                Integer.valueOf(update_code_view.getText().toString())+ TEST_UPDATE_CODE_OFFSET ,
                Integer.valueOf(beaver_id_view  .getText().toString())                          ,
                Integer.valueOf(meal_id_view    .getText().toString())
        );
        Log.d("TestActivity", "onClickSendTest: update_info = "+ update_info.toString());
        AsyncUpdateServer updater = new AsyncUpdateServer();
        updater.execute(update_info);
        //zhopa
    }
}
