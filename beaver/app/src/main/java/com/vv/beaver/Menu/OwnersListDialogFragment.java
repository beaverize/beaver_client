package com.vv.beaver.Menu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.vv.beaver.Beaver.BeaverItem;
import com.vv.beaver.DataHolder;
import com.vv.beaver.DbInterface;
import com.vv.beaver.Menu.MenuArrayAdapter;
import com.vv.beaver.Menu.MenuItem;
import com.vv.beaver.Menu.MenuListActivity;
import com.vv.beaver.R;

import java.util.ArrayList;

/**
 * Created by vova on 09/05/2016.
 */
public class OwnersListDialogFragment extends DialogFragment {
    ArrayList<MenuItem>       menu_items_list   = DataHolder.getInstance().getMenuItemsList()   ;
    ArrayList<BeaverItem>     beaver_items_list = DataHolder.getInstance().getBeaverItemsList() ;
    //private MenuItemDbHelper menu_item_db;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        //this.menu_item_db = new MenuItemDbHelper(getActivity());
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final int menu_item_ndx = getArguments().getInt("menuItemNdx");
        final int menu_item_id  = DataHolder.getInstance().getMenuItemsList().get(menu_item_ndx).getId();
        builder.setTitle(R.string.beaver_list_string)
                .setItems(DataHolder.getInstance().getBeaversCharSequenceArray(), new DialogInterface.OnClickListener() {
                    @SuppressLint("LongLogTag")
                    public void onClick(DialogInterface dialog, int which) {
                        //find menu_item by id
                        //Log.d("OwnersListDialogFragment", "onClick: which = " + which + ", menuItem = " + menuItemNdx);
                        Log.d("OwnersListDialogFragment", "onClick: new owner of " + menu_items_list.get(menu_item_ndx).getId() + " is " + beaver_items_list.get(which).getId());
                        int old_owner_id = menu_items_list.get(menu_item_ndx).getOwnerId();
                        if(old_owner_id != -1) {
                            BeaverItem old_owner = DataHolder.getInstance().findBeaverById(old_owner_id);
                            old_owner.removeMenuItemFromSublist(menu_item_id); //UPDATE vovka&vitka 24/07/2016
                        }
                        menu_items_list.get(menu_item_ndx).setOwner(beaver_items_list.get(which).getId()); //todo get menu item index
                        beaver_items_list.get(which).addMenuItemToSublist(menu_items_list.get(/*menu_item.get_id()*/menu_item_ndx));
                        // The 'which' argument contains the index position
                        // of the selected item
                        //DbInterface.getInstance().updateMenuItemOwnerId(menu_item_id, new owner id);
                        //menu_item_db.updateOwnerId(menu_items_list.get(menu_item_ndx).getId(), beaver_items_list.get(which).getId());
                        DbInterface.getInstance().updateMenuItemOwnerId(getActivity(), menu_items_list.get(menu_item_ndx).getId(), beaver_items_list.get(which).getId());
                        ((MenuListActivity) getActivity()).refreshOwnerTextView(menu_item_ndx);
                    }
                    // Create the AlertDialog object and return it
                });
        //((MenuListActivity) getActivity()).menu_adapter.notifyDataSetChanged();

        return builder.create();
    }
}