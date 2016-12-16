package com.vv.beaver.Communicator;

import android.util.Log;

import com.vv.beaver.DataHolder;
import com.vv.beaver.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;



/**
 * Created by vova on 11/11/2016.
 */

public class Receiver extends Thread {
    public static final String SERVERIP = "54.175.128.20";
    public static final int SERVERPORT = 4444;

    @Override
    public void run() {
        Socket server_socket = DataHolder.getInstance().getServerSocket();
        try {
            ObjectInputStream object_input_stream = new ObjectInputStream(server_socket.getInputStream());
            while (true) {
                UpdateInfo update_info = (UpdateInfo) object_input_stream.readObject();

                if (update_info != null) {
                    Log.d("Receiver", "run: received \"" + update_info.toString() + "\"");
                    updateDH(update_info);
                } else {
                    Log.d("Receiver", "run: end of stream reached");
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                server_socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //read the message received from client
        //in this while we wait to receive messages from client (it's an infinite loop)
        //this while it's like a listener for messages

        return;
    }

    private void updateDH(UpdateInfo update_info) {
        int update_code = update_info.getUpdateCode();
        //Log.d("Receiver", "updateDH: adding beaver0 update_code = " + update_code + " define = " + R.integer.add_beaver);
        switch (update_code) {
            case UpdateCode.ADD_NEW_BEAVER:
                //Log.d("Receiver", "updateDH: adding beaver1");
                DataHolder.getInstance().addNewBeaver(update_info.getBeaverId());
                break;

            default:
                break;
        }

    }

}
