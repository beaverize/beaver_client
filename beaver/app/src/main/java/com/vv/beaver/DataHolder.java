package com.vv.beaver;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.vv.beaver.Beaver.BeaverItem;
import com.vv.beaver.Menu.MenuItem;

import junit.framework.Assert;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vova on 10/05/2016.
 */
public class DataHolder {
    private int                     next_beaver_id;
    private List<Integer>           deleted_beaver_ids;
    private int                     next_menu_item_id;
    private List<Integer>           deleted_menu_item_ids;
    private ArrayList<BeaverItem>   beaver_items_list   = new ArrayList<BeaverItem>();
    private ArrayList<MenuItem>     menu_items_list     = new ArrayList<MenuItem>();
    private Socket                  server_socket       ;
    private ObjectOutputStream      server_out_stream   ;

    public Socket getServerSocket() {
        return this.server_socket;
    }

    public void setServerSocket(Socket server_socket) {
        this.server_socket = server_socket;
    }
    public ObjectOutputStream getServerOutStream() {
        return this.server_out_stream;
    }

    public void setServerOutStream(ObjectOutputStream server_out_stream) {
        this.server_out_stream = server_out_stream;
    }

    private DataHolder() {
        this.deleted_beaver_ids     = new ArrayList<>();
        this.deleted_menu_item_ids  = new ArrayList<>();
    }
    private static final DataHolder holder = new DataHolder();

    public static DataHolder getInstance() {
        return holder;
    }

    public ArrayList<MenuItem> getMenuItemsList() {
        return this.menu_items_list;
    }

    public ArrayList<BeaverItem> getBeaverItemsList() {
        return this.beaver_items_list;
    }

    public CharSequence[] getBeaversCharSequenceArray() {
        int size = this.beaver_items_list.size();
        CharSequence[] beaver_sequence_array = new CharSequence[size];
        for (int i = 0; i < size; i++) {
            beaver_sequence_array[i] = this.beaver_items_list.get(i).getName();
        }
        return beaver_sequence_array;
    }
    public void addNewBeaver(int beaver_id){
        String beaver_name ="beaver"+ beaver_id;
        DataHolder.getInstance().getBeaverItemsList().add(new BeaverItem(beaver_name, beaver_id));
        Log.d("DataHolder", "addNewBeaver: beaver " + beaver_id + " added");
      //  DbInterface.getInstance().addBeaverEntry(this, beaver_id, beaver_name); /No activity context

    }

    public int getMenuItemNdxById(int menu_item_id) {
        for (int i = 0; i < this.menu_items_list.size(); i++) {
            Log.d("DataHolder", "getMenuItemNdxById: looking for id " + menu_item_id + " on index " + i + " current_id = " + this.menu_items_list.get(i).getId());
            //do something with object
            if (this.menu_items_list.get(i).getId() == menu_item_id) {//UPDATE vova & victor 12.07.2016
                Log.d("DataHolder", "getMenuItemNdxById: entry " + i + " menu_item_id = " + this.menu_items_list.get(i).getId());
                return i;
            }
        }
        return -1;
    }

    //public int getNextBeaverId() {
    //    return this.next_beaver_id;
    //}
    //public int getNextMenuItemId() {
    //    return this.next_menu_item_id;
    //}
    public int retrieveNextBeaverId() {
        int ret_val;
        if(deleted_beaver_ids.size()== 0) {
            ret_val = this.next_beaver_id;
            this.next_beaver_id++;
        }
        else {
            ret_val = deleted_beaver_ids.get(0);
            deleted_beaver_ids.remove(0);
        }
        return ret_val;
    }

    public int retrieveNextMenuItemId() {
        int ret_val;
        if(deleted_menu_item_ids.size()== 0) {
            ret_val = this.next_menu_item_id;
            this.next_menu_item_id++;
        }
        else {
            ret_val = deleted_menu_item_ids.get(0);
            deleted_menu_item_ids.remove(0);
        }
        return ret_val;
    }

    public void setNextBeaverId(int next_beaver_id) {
        this.next_beaver_id = next_beaver_id;
    }
    public void setNextMenuItemId(int next_menu_item_id) {
        this.next_menu_item_id = next_menu_item_id;
    }

    public BeaverItem findBeaverById(int beaver_id) {
        for (int i = 0; i < this.beaver_items_list.size(); i++) {
            if (beaver_items_list.get(i).getId() == beaver_id) {
                return this.beaver_items_list.get(i);
            }
        }
        return null;
    }
    //UPDATE vova & victor
    public void removeMenuItem(int menu_item_ndx) {
        //delete menu item from menu
        MenuItem deleted_menu_item = DataHolder.getInstance().getMenuItemsList().get(menu_item_ndx);
        int deleted_menu_item_id = deleted_menu_item.getId();
        Log.d("DataHolder", "onClick: delete item phase 1" + menu_item_ndx);
        //add deleted id to the list to be reused later
        this.deleted_menu_item_ids.add(this.menu_items_list.get(menu_item_ndx).getId());
        //delete item
        this.menu_items_list.remove(menu_item_ndx);
        //delete menu item form it's owner's sublist
        int owner_id = deleted_menu_item.getOwnerId();
        BeaverItem owner = this.findBeaverById(owner_id);
        if(owner == null) {
            return;
        }
        for (int i = 0; i < owner.getMenuItemsSublist().size(); i++) {
            int curr_menu_item_id = owner.getMenuItemsSublist().get(i).getId();
            if(curr_menu_item_id == deleted_menu_item_id) {
                owner.getMenuItemsSublist().remove(i);
            }
        }
    }

    public void removeBeaverById(int deleted_beaver_id) {//UPDATE voka&vitka 24/07/2016
        BeaverItem beaver_to_delete = this.findBeaverById(deleted_beaver_id);
        Assert.assertEquals(false, beaver_to_delete == null);
        this.deleted_beaver_ids.add(beaver_to_delete.getId());
        this.beaver_items_list.remove(beaver_to_delete);
        for(int menu_item_ndx = 0; menu_item_ndx < this.menu_items_list.size(); menu_item_ndx++) {
            if(this.menu_items_list.get(menu_item_ndx).getOwnerId() == deleted_beaver_id) {
                this.menu_items_list.get(menu_item_ndx).setOwner(-1);
            }
        }
    }

    public int calculateTotalPrice(ArrayList<MenuItem> menu_items_list) {
        int total_price = 0;
        for(int i = 0; i < menu_items_list.size(); i++) {
            total_price += menu_items_list.get(i).getPrice();
        }
        return total_price;
    }


}