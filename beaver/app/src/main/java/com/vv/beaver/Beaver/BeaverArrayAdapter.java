package com.vv.beaver.Beaver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vv.beaver.Menu.MenuListActivity;
import com.vv.beaver.R;

import java.util.ArrayList;

/**
 * Created by vova on 03/05/2016.
 */
public class BeaverArrayAdapter extends ArrayAdapter<BeaverItem> {
    Context                 context                                 ;
    int                     layout_resource_id                      ;
    LinearLayout            linearBeaver                            ;
    ArrayList<BeaverItem>   data                = new ArrayList<BeaverItem>();

    View.OnClickListener                on_click_listener_display_current_beaver = new View.OnClickListener() {
        @Override
        public void onClick(View v) {//display current beaver
            Log.d("BeaverArrayAdapter", "onClick: display current beaver");
                Intent intent = new Intent(context, CurrentBeaverActivity.class);
                context.startActivity(intent);
        }
    };

    public BeaverArrayAdapter(Context context, int layoutResourceId,
                              ArrayList<BeaverItem> data) {
        super(context, layoutResourceId, data);
        this.layout_resource_id = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layout_resource_id, parent, false);

            linearBeaver = (LinearLayout) row.findViewById(R.id.linearBeaver);

            //linearBeaver.setId(5000+data.get(position).getId());

            BeaverItem beaver_item = data.get(position);
            TextView label = new TextView(context);
            label.setText(beaver_item.getName());
            linearBeaver.addView(label);
            linearBeaver.setTag(beaver_item);

            Button delete_item_button = new Button(context);
            delete_item_button.setId(R.integer.delete_beaver_button_offset + beaver_item.getId());
            Log.d("BeaverArrayAdapter", "getView: created delete button with id " + delete_item_button.getId());
            BeaverListActivity my_activity = (BeaverListActivity) this.context;
            delete_item_button.setOnClickListener(my_activity.on_click_listener_delete_beaver);
            delete_item_button.setText(R.string.delete_menu_item_string);
            delete_item_button.setFocusable(false);
            linearBeaver.addView(delete_item_button);


        }

        return row;

    }

}