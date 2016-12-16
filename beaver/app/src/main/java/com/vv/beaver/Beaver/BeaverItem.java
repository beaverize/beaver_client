package com.vv.beaver.Beaver;

import android.media.Image;
import android.view.Menu;

import com.vv.beaver.Menu.MenuItem;

import java.util.ArrayList;

/**
 * Created by vova on 03/05/2016.
 */
public class BeaverItem {

    private final int id;
    private Image avatar;
    private String name;
    private boolean is_paid;

    private ArrayList<MenuItem> menu_items_sublist = new ArrayList<MenuItem>();

    public BeaverItem(String name, int id) {
        super();
        this.name = new String(name);
        this.id = id;
    }

    public ArrayList<MenuItem> getMenuItemsSublist() {
        return menu_items_sublist;
    }

    public void setMenuItemsSublist(ArrayList<MenuItem> menu_items_sublist) {
        this.menu_items_sublist = menu_items_sublist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMenuItemToSublist(MenuItem new_menu_item) {
        this.menu_items_sublist.add(new_menu_item);
    }
    public int getId() {
        return this.id;
    }

    public void removeMenuItemFromSublist(int menu_item_id_to_delete) {
        for(int submenu_ndx = 0; submenu_ndx < this.menu_items_sublist.size(); submenu_ndx++) {
            if(menu_items_sublist.get(submenu_ndx).getId() == menu_item_id_to_delete) {
                menu_items_sublist.remove(submenu_ndx);
            }
        }
    }

}


