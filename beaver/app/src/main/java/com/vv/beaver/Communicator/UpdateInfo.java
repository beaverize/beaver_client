package com.vv.beaver.Communicator;
import com.vv.beaver.R;

import java.io.Serializable;

/**
 * Created by vova on 15/11/2016.
 */

public class UpdateInfo implements Serializable {
    private static final long serialVersionUID = 5950169519310163575L;
    private int update_code;
    private int beaver_id;
    private int meal_id;

    public UpdateInfo(int update_code, int beaver_id, int meal_id) {
        this.update_code = update_code;
        this.beaver_id   = beaver_id;
        this.meal_id     = meal_id;
    }

    public int getUpdateCode() {
        return update_code;
    }
    public void setUpdateCode(int update_code) {
        this.update_code = update_code;
    }
    public int getBeaverId() {
        return beaver_id;
    }
    public void setBeaverId(int beaver_id) {
        this.beaver_id = beaver_id;
    }
    public int getMealId() {
        return meal_id;
    }
    public void setMealId(int meal_id) {
        this.meal_id = meal_id;
    }
    @Override
    public String toString() {
        return  String.valueOf(this.update_code) + ", " +
                String.valueOf(this.beaver_id)   + ", " +
                String.valueOf(this.meal_id);

    }
}
