package com.vv.beaver.Beaver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.vv.beaver.DataHolder;
import com.vv.beaver.DbInterface;
import com.vv.beaver.Menu.MenuArrayAdapter;
import com.vv.beaver.Menu.MenuItem;
import com.vv.beaver.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class CurrentBeaverActivity extends AppCompatActivity {

    private ListView productsBeaver;
    private MenuArrayAdapter adapter;
    public ArrayList<MenuItem> list;
    public ArrayList<MenuItem> beaveritemlist;
    int beaver_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_beaver);

        productsBeaver = (ListView) findViewById(R.id.listView);

        Intent in = getIntent();
        beaver_id = in.getIntExtra(BeaverListActivity.BEAVER_ID, -10);
        if(beaver_id == -10) {
            Log.e("CurrentBeaverActivity", "OnCreate: error: no owner id transmitted");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateBeaversPrice();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("CurrentBeaverActivity", "onResume: looking for product with owner id " + beaver_id);
        adapter = new MenuArrayAdapter(this,R.layout.menu_list, DataHolder.getInstance().findBeaverById(beaver_id).getMenuItemsSublist());
        productsBeaver.setAdapter(adapter);
    }

    public void updateBeaversPrice() {
        int total_price = DataHolder.getInstance().calculateTotalPrice(DataHolder.getInstance().findBeaverById(beaver_id).getMenuItemsSublist());
        TextView total_price_view = (TextView) findViewById(R.id.beaversPrice);
        total_price_view.setText("Total price: " + String.valueOf(total_price));
    }
}