package com.vv.beaver.Menu;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.vv.beaver.Beaver.BeaverItem;
import com.vv.beaver.DataHolder;
import com.vv.beaver.DbInterface;
import com.vv.beaver.R;

import java.util.ArrayList;

public class MenuListActivity extends AppCompatActivity implements View.OnClickListener {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    //ArrayList<MenuItem> menu_items_list=new ArrayList<MenuItem>();
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    public MenuArrayAdapter     menu_adapter    ;
    private ListView menu_list_view;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_menu_list);
        this.menu_adapter=new MenuArrayAdapter(
                this,
                R.layout.menu_list,
                DataHolder.getInstance().getMenuItemsList());
        menu_list_view = (ListView) findViewById(R.id.menuListView);
        menu_list_view.setAdapter(menu_adapter);


        findViewById(R.id.itemfromenubtn).setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        menu_list_view.setAdapter(this.menu_adapter);

    }

    public void onStart() {
        super.onStart();
        this.updateTotalPrice();
    }

    public void refreshOwnerTextView(int menu_item_ndx) {
        MenuItem menu_item_found = DataHolder.getInstance().getMenuItemsList().get(menu_item_ndx);
        int menu_item_id = menu_item_found.getId();
        TextView owner_text_view = (TextView) findViewById(R.integer.menu_item_owner_id_offset + menu_item_id);
        if(menu_item_found != null && owner_text_view!= null) {
            owner_text_view.setText("owner: " + menu_item_found.getOwnerId());
            this.menu_adapter.notifyDataSetChanged();
        }
    }



    View.OnClickListener on_click_listener_delete_menu_item = new View.OnClickListener() {
        @Override
        public void onClick(View v) {//setting owner to product UPDATE vova & victor
            int deleted_menu_item_id = v.getId() - R.integer.delete_menu_item_button_offset;
            int menu_item_ndx = DataHolder.getInstance().getMenuItemNdxById(deleted_menu_item_id);
            DataHolder.getInstance().removeMenuItem(menu_item_ndx);
            //UPDATE vova try

//            menu_adapter=new MenuArrayAdapter(
//                    curr_context,
//                    R.layout.menu_list,
//                    DataHolder.getInstance().getMenuItemsList());
            //setListAdapter(menu_adapter);
            //((MenuArrayAdapter) getListAdapter()).notifyDataSetChanged();
            ListView menu_list_view = (ListView) findViewById(R.id.menuListView);
            menu_list_view.setAdapter(menu_adapter);
            MenuListActivity curr_context = (MenuListActivity) menu_adapter.getContext();
            DbInterface.getInstance().removeMenuItemEntry(curr_context, deleted_menu_item_id);
            curr_context.updateTotalPrice();

            //menu_adapter.notifyDataSetChanged();
        } //UPDATE vova & victor
    };

    @Override
    public void onClick(View v) {//on click listener to the button to open the add activity-by javier
        switch (v.getId()){

            case R.id.itemfromenubtn:
                Intent fromMenu = new Intent(this, ItemsMenuActicity.class);
                startActivity(fromMenu);
                break;
        }

    }
    public void updateTotalPrice() {
        int total_price = DataHolder.getInstance().calculateTotalPrice(DataHolder.getInstance().getMenuItemsList());
        TextView total_price_view = (TextView) findViewById(R.id.totalPrice);
        total_price_view.setText("Total price: " + String.valueOf(total_price));
    }
}