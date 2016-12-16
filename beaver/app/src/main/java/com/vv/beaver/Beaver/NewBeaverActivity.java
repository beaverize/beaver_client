package com.vv.beaver.Beaver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.vv.beaver.DataHolder;
import com.vv.beaver.DbInterface;
import com.vv.beaver.R;

public class NewBeaverActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText beaverName;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__beaver_);

        findViewById(R.id.addbeaverbtn).setOnClickListener(this);
        findViewById(R.id.cnlbtn).setOnClickListener(this);
        beaverName = (EditText) findViewById(R.id.beavernametxt);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addbeaverbtn:
                int beaver_id = DataHolder.getInstance().retrieveNextBeaverId();
                String sbeavername = beaverName.getText().toString();
                DataHolder.getInstance().getBeaverItemsList().add(new BeaverItem(sbeavername, beaver_id));
                //DataHolder.getInstance().setNextBeaverId(DataHolder.getInstance().getNextBeaverId() + 1);
                DbInterface.getInstance().addBeaverEntry(this, beaver_id, sbeavername);
                finish();
                break;
            case R.id.cnlbtn:
                finish();
                break;
        }


    }
}
