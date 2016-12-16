package com.vv.beaver.Menu;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vv.beaver.DataHolder;
import com.vv.beaver.R;

import java.util.ArrayList;

/**
 * Created by vova on 03/05/2016.
 */
public class MenuArrayAdapter extends ArrayAdapter<MenuItem> {
    int layoutResourceId;
    LinearLayout linearMenu;
    Context context;
    ArrayList<MenuItem> data = new ArrayList<MenuItem>();

    View.OnClickListener                on_click_listener_change_owner           = new View.OnClickListener() {
        @Override
        public void onClick(View v) {//setting owner to product
            Log.d("MenuArrayAdapter", "onClick: set owner invoked");
            DialogFragment newFragment = new OwnersListDialogFragment();
            Bundle args = new Bundle();
            int menu_item_ndx = DataHolder.getInstance().getMenuItemNdxById(v.getId() - R.integer.change_owner_button_offset); //UPDATE vova & victor
            Log.d("MenuArrayAdapter", "onClick: set owner to item" + menu_item_ndx);
            if(menu_item_ndx != -1) {
                args.putInt("menuItemNdx", menu_item_ndx);
                newFragment.setArguments(args);
                newFragment.show(((Activity) context).getFragmentManager(), "owners");
            } else {
                Log.d("MenuArrayAdapter", "onClick: menu item not found");
            }
        }
    };

    public MenuArrayAdapter(Context context, int layoutResourceId,
                            ArrayList<MenuItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            linearMenu = (LinearLayout) row.findViewById(R.id.linearMenu);

            MenuItem menu_item = data.get(position);
            Log.d("MenuArrayAdapter", "getView: position=" + position + ", menu_item id=" + menu_item.getId() + ", menu_item name=" + menu_item.getName());

            TextView name_view = new TextView(context);
            name_view.setText(menu_item.getName());
            linearMenu.addView(name_view);

            TextView owner_view = new TextView(context);
            owner_view.setText("owner: " + String.valueOf(menu_item.getOwnerId()));
            owner_view.setId(R.integer.menu_item_owner_id_offset + menu_item.getId());
            linearMenu.addView(owner_view);

            TextView price_view = new TextView(context);
            price_view.setText("price: " + String.valueOf(menu_item.getPrice()));
            linearMenu.addView(price_view);


            if(this.context instanceof MenuListActivity) {
                Button change_owner_button = new Button(context);
                change_owner_button.setId(R.integer.change_owner_button_offset + menu_item.getId());
                Log.d("MenuArrayAdapter", "getView: created change owner button with id " + change_owner_button.getId());
                //data.get(position).setChangeOwnerButtonId(change_owner_button.getId()); //UPDATE vova & victor
                change_owner_button.setOnClickListener(this.on_click_listener_change_owner);
                change_owner_button.setText(R.string.change_owner_string);
                linearMenu.addView(change_owner_button);
                Button delete_item_button = new Button(context);
                delete_item_button.setId(R.integer.delete_menu_item_button_offset + menu_item.getId());
                Log.d("MenuArrayAdapter", "getView: created delete button with id " + delete_item_button.getId());
                MenuListActivity my_activity = (MenuListActivity) this.context;
                delete_item_button.setOnClickListener(my_activity.on_click_listener_delete_menu_item);
                delete_item_button.setText(R.string.delete_menu_item_string);
                linearMenu.addView(delete_item_button);
            }
        }

        return row;

    }

   }
