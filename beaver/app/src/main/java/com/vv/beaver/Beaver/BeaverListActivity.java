package com.vv.beaver.Beaver;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vv.beaver.DataHolder;
import com.vv.beaver.BeaverDbHelper;
import com.vv.beaver.DbInterface;
import com.vv.beaver.Menu.MenuListActivity;
import com.vv.beaver.R;
import com.vv.beaver.Test.TestActivity;

import java.io.IOException;

import junit.framework.Assert;

public class BeaverListActivity extends AppCompatActivity {
    public static final String BEAVER_ID = "beaverId";
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    //ArrayList<BeaverItem> beaver_items_list = new ArrayList<BeaverItem>();



    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    BeaverArrayAdapter beaver_adapter;
        //private BeaverDbHelper  beaver_db;

    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    int clickCounter = 0;
    ListView beaver_list_view;
    int beaver_id;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);



        Log.d("precrush 0", "before find listview");
        setContentView(R.layout.activity_beaver_list);
        Log.d("precrush 0.5", "before find listview");
        beaver_adapter = new BeaverArrayAdapter(
                this,
                R.layout.beaver_list,
                DataHolder.getInstance().getBeaverItemsList());
        //setListAdapter(adapter);
        Log.d("precrush 1", "before find listview");
        beaver_list_view = (ListView) findViewById(R.id.beaverListView);
        beaver_list_view.setOnItemClickListener(this.mOnItemClickListener);




        if(DataHolder.getInstance().getBeaverItemsList().size() == 0) {
            beaver_id = 0;
                String sbeavername = "Me";
                DataHolder.getInstance().getBeaverItemsList().add(new BeaverItem(sbeavername, beaver_id));
                //DataHolder.getInstance().setNextBeaverId(DataHolder.getInstance().getNextBeaverId() + 1);
                DbInterface.getInstance().addBeaverEntry(this, beaver_id, sbeavername);

        }else{
            BeaverItem item = DataHolder.getInstance().getBeaverItemsList().get(0);
            beaver_id = item.getId();
            if(beaver_id != 0){
                String sbeavername = "Me";
                DataHolder.getInstance().getBeaverItemsList().add(new BeaverItem(sbeavername, beaver_id));
                //DataHolder.getInstance().setNextBeaverId(DataHolder.getInstance().getNextBeaverId() + 1);
                DbInterface.getInstance().addBeaverEntry(this, beaver_id, sbeavername);
            }
        }
        beaver_list_view.setAdapter(beaver_adapter);
    }



    public void addItemBeaver(View v) {
        Intent addBeaverIntent = new Intent(this, NewBeaverActivity.class);
        startActivity(addBeaverIntent);
    }

    public void displayMenu(View v) {
        Intent intent = new Intent(this, MenuListActivity.class);
        startActivity(intent);
    }

    ListView.OnItemClickListener mOnItemClickListener = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("BeaverArrayAdapter", "onClick: display current beaver");
            Intent intent = new Intent(BeaverListActivity.this, CurrentBeaverActivity.class);
            BeaverItem beaver =  (BeaverItem) view.getTag();
            intent.putExtra(BEAVER_ID, beaver.getId());
            Log.d("OnItemClick", "onClick: clicked oמ beaver id " + beaver.getId());
            startActivity(intent);
            Toast.makeText(BeaverListActivity.this, beaver.getId() + "", Toast.LENGTH_SHORT).show();
        }
    };
    View.OnClickListener on_click_listener_delete_beaver = new View.OnClickListener() { //UPDATE vovka&vitka 24/07/2016
        @Override
        public void onClick(View v) {//setting owner to product UPDATE vova & victor
            int deleted_beaver_id = v.getId() - R.integer.delete_beaver_button_offset;
            DataHolder.getInstance().removeBeaverById(deleted_beaver_id);
            ListView beaver_list_view = (ListView) findViewById(R.id.beaverListView);
            beaver_list_view.setAdapter(beaver_adapter);
            BeaverListActivity curr_context = (BeaverListActivity) beaver_adapter.getContext();
            DbInterface.getInstance().removeBeaverEntry(curr_context, deleted_beaver_id);
        }   //UPDATE vova & victor
    };

    @Override
    public void onBackPressed() {
        Log.d("LoginActivity", "onBackPressed: Back disabled. Exiting");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        beaver_list_view.setAdapter(beaver_adapter);


    }
    public void OnClickGoToTest(View view) {
        Intent intent = new Intent(this, TestActivity.class);
        Log.d("BeaverListActivity", "OnClickGoToTest: start");
        startActivity(intent);
    }
}