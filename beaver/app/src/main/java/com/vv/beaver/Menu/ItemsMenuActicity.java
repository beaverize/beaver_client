package com.vv.beaver.Menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vv.beaver.Beaver.BeaverItem;
import com.vv.beaver.Communicator.AsyncUpdateServer;
import com.vv.beaver.Communicator.UpdateCode;
import com.vv.beaver.Communicator.UpdateInfo;
import com.vv.beaver.DataHolder;
import com.vv.beaver.DbInterface;
import com.vv.beaver.R;

import java.util.ArrayList;

public class ItemsMenuActicity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView menulistview;
    public MenuArrayAdapter adapter;
    ArrayList<MenuItem> itemarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_menu_acticity);

        itemarray = new ArrayList<>();
        itemarray.add(new MenuItem(0, "Black Label", 10));
        itemarray.add(new MenuItem(1, "Bushmills", 20));
        itemarray.add(new MenuItem(2, "Jameson", 30));
        itemarray.add(new MenuItem(3, "Jack Daniels", 40));


        menulistview = (ListView) findViewById(R.id.menulist);
        adapter = new MenuArrayAdapter(this, R.layout.menu_list, itemarray);

        menulistview.setAdapter(adapter);
        menulistview.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MenuItem itemSelected = (MenuItem) itemarray.get(position);
        int menu_item_id = itemSelected.getId();
        int price = itemSelected.getPrice();
        String name = itemSelected.getName();
        int beaver_id = (DataHolder.getInstance().getBeaverItemsList().get(0)).getId();

        
        Toast.makeText(ItemsMenuActicity.this, name +" / "+ price , Toast.LENGTH_SHORT).show();
        DataHolder.getInstance().getMenuItemsList().add(new MenuItem(menu_item_id, name, price, beaver_id));
        DataHolder.getInstance().findBeaverById(beaver_id).getMenuItemsSublist().add(new MenuItem(menu_item_id, name, price, beaver_id));
        DbInterface.getInstance().addMenuItemEntry(this, menu_item_id, name, price, beaver_id);
        DbInterface.getInstance().updateMenuItemOwnerId(this, menu_item_id, beaver_id);
        UpdateInfo update_info = new UpdateInfo(UpdateCode.BEAVER_ORDERED, beaver_id, menu_item_id);
        AsyncUpdateServer updater = new AsyncUpdateServer();
        updater.execute(update_info);
        finish();
    }
}
