package com.vv.beaver.Menu;

import android.content.Context;

import com.vv.beaver.Beaver.BeaverItem;

/**
 * Created by vova on 05/05/2016.
 */
public class MenuItem {
    private int         id                      ;
    private String      name                    ;
    private int         price                   ;
    private int         owner_id                ;
    //private int         change_owner_button_id  ;     UPDATE vova & victor no need

    public MenuItem(int id, String name, int price) {
        super()                             ;
        this.id         = id                ;
        this.name       = new String(name)  ;
        this.price      = price             ;
        this.owner_id   = -1                ;
    }

    public MenuItem(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public MenuItem(String name, int price, int owner_id) {
        this.name = name;
        this.price = price;
        this.owner_id = owner_id;
    }

    public MenuItem(int id, String name, int price, int owner_id) {
        super()                             ;
        this.id         = id                ;
        this.name       = new String(name)  ;
        this.price      = price             ;
        this.owner_id   = owner_id          ;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public void setOwner(int id) {
        this.owner_id = id;
    }
    public int getOwnerId() {
        return this.owner_id;
    }

    /*
    public void setChangeOwnerButtonId(int button_id) {
        this.change_owner_button_id = button_id;
    }
    public int getChangeOwnerButtonId() {
        return this.change_owner_button_id;

    }
    //UPDATE vova & victor no need
    */

}