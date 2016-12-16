package com.vv.beaver;

import android.app.Activity;
import android.content.Context;

import com.vv.beaver.Menu.MenuItem;

/**
 * Created by vova on 01/07/2016.
 */
public class DbInterface {
    private static DbInterface holder = new DbInterface();

    public static DbInterface getInstance() {
        return holder;
    }

    BeaverDbHelper beaver_db;
    MenuItemDbHelper menu_item_db;

    //MenuItem
    public void addMenuItemEntry(Context context,  int menu_item_id, String menu_item_name, int menu_item_price, int menu_item_owner_id) {
        this.menu_item_db = new MenuItemDbHelper(context);
        menu_item_db.addEntry(menu_item_id, menu_item_name, menu_item_price, menu_item_owner_id);
    }

    public void removeMenuItemEntry(Context context,  int menu_item_id) {
        this.menu_item_db = new MenuItemDbHelper(context);
        menu_item_db.deleteEntry(menu_item_id);
    }
    public void updateMenuItemOwnerId(Context context, int menu_item_id, int new_owner_id) {
        this.menu_item_db = new MenuItemDbHelper(context);
        menu_item_db.updateOwnerId(menu_item_id, new_owner_id);
    }

    //Beaver
    public void addBeaverEntry(Context context,  int beaver_id, String beaver_name) {
        this.beaver_db = new BeaverDbHelper(context);
        beaver_db.addEntry(beaver_id, beaver_name);
    }
    public void removeBeaverEntry(Context context,  int owner_id) { //UPDATE vovka&vitka 24/07/2016
        this.beaver_db = new BeaverDbHelper(context);
        this.beaver_db.deleteEntry(owner_id);
        this.menu_item_db = new MenuItemDbHelper(context);
        this.menu_item_db.removeSelectedOwnerFromAllProducts(owner_id);
    }



    public void loadDb(Context context) {
        this.beaver_db = new BeaverDbHelper(context);
        beaver_db.loadHolderFromDb();
        this.menu_item_db = new MenuItemDbHelper(context);
        menu_item_db.loadHolderFromDb();
        for(int menu_item_ndx = 0; menu_item_ndx < DataHolder.getInstance().getMenuItemsList().size(); menu_item_ndx++) {
            MenuItem current_menu_item = DataHolder.getInstance().getMenuItemsList().get(menu_item_ndx);
            int current_menu_item_owner_id = DataHolder.getInstance().getMenuItemsList().get(menu_item_ndx).getOwnerId();
            if(current_menu_item_owner_id != -1) {
                DataHolder.getInstance().findBeaverById(current_menu_item_owner_id).getMenuItemsSublist().add(current_menu_item);
            }
        }
    }
}
