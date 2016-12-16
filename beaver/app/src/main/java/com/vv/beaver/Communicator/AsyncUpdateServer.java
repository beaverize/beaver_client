package com.vv.beaver.Communicator;

import android.os.AsyncTask;
import android.util.Log;

import com.vv.beaver.DataHolder;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by vova on 09/12/2016.
 */

public class AsyncUpdateServer extends AsyncTask<UpdateInfo, Void, Void> {
    @Override
    protected Void doInBackground(UpdateInfo... params) {
        Connector connector = new Connector();
        connector.start();
        try {
            connector.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ObjectOutputStream object_output_stream = DataHolder.getInstance().getServerOutStream();
        UpdateInfo update_info = params[0];
        try {
            object_output_stream.writeObject(update_info);
            Log.d("AsyncUpdateServer", "doInBackground: object sent");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
