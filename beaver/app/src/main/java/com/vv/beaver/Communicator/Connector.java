package com.vv.beaver.Communicator;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.vv.beaver.DataHolder;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by vova on 11/11/2016.
 */

public class Connector extends Thread{
    public static final String  SERVERIP    =   "54.175.128.20";
    public static final int     SERVERPORT  =   4444;
    //private OnMessageReceived mMessageListener = null;

    @Override
    public void run() {
        super.run();
        if(DataHolder.getInstance().getServerSocket() == null || DataHolder.getInstance().getServerSocket().isClosed()) {
            InetAddress serverAddr = null;
            Log.d("Connector", "connectToServer: before connecting...");
            try {
                serverAddr = InetAddress.getByName(SERVERIP);
                Socket socket = new Socket(serverAddr, SERVERPORT);
                DataHolder.getInstance().setServerSocket(socket);
                ObjectOutputStream object_output_stream = new ObjectOutputStream(socket.getOutputStream());
                DataHolder.getInstance().setServerOutStream(object_output_stream);
                Receiver receiver = new Receiver();
                receiver.start();
            } catch (UnknownHostException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    private boolean checkNetworkConnection(Context context) {
        ConnectivityManager check = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = check.getAllNetworkInfo();
        for (int i = 0; i<info.length; i++){
            if (info[i].getState() == NetworkInfo.State.CONNECTED){
                Toast.makeText(context, "Internet is connected", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        Toast.makeText(context, "Internet is not connected", Toast.LENGTH_SHORT).show();
        return false;
    }
}
