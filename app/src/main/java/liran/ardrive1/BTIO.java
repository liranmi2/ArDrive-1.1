package liran.ardrive1;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;


/**
 * Created by Liran on 23/11/2015.
 * changed by Liran on 20/01/2016
 */

public class BTIO {

//    Thread workerThread;
//    byte[] readBuffer;
//    int readBufferPosition;
//    //int counter;
//    volatile boolean stopWorker;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;
    private OutputStream mmOutputStream;
    private InputStream mmInputStream;
    private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard //SerialPortService ID
    private String direction = "s";
    private int speed = 0;


    public boolean findBT(Activity activity)
    {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.v("from findBT", "BT adapter status: " + mBluetoothAdapter.isEnabled());
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            Log.v("intent enable BT", "" + enableBluetooth);
            activity.startActivityForResult(enableBluetooth, 1);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0) {
            for(BluetoothDevice device : pairedDevices)
                if (device.getName().equals("ArDrive")) {
                    mmDevice = device;
                    return true;
                }
        }
        return false;
    }

    public void openBT() throws Exception
    {
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();
        mmInputStream = mmSocket.getInputStream();
    }

    private boolean sendAndReceive(String command)
    {
        try {
            byte[] write_cmd = command.getBytes();
            for (int i=0; i<10; i++)
            {
                mmOutputStream.write(write_cmd);

                Log.v("write to BT: ", "" + command);
//                try {
//                    Thread.sleep(250);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                Log.v("input stream bytes: ", "" + mmInputStream.available());
                if (mmInputStream.available() > 0)
                {

                    byte[] readBuffer = new byte[255];
                    mmInputStream.read(readBuffer);
//
                    String response = new String(readBuffer, 0, 0);
                    Log.v("read buffer", "" + readBuffer);
                    Log.v("response from BT: ", response);
                    Log.v("command: ", command);
                    if (command.contains(response))
                    {
                        Log.v("aaaaaaaaaaaaaaa", "in contatinss@@@@@");
                        return true;
                    }


                }

            }
            return false;


        } catch (IOException e){ // | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean driveForward(int speed)
    {
        this.direction = "f";
        this.speed = speed;
        return this.sendAndReceive(":f:" + speed + ":@");
    }

    public boolean driveTo()
    {
        return this.sendAndReceive(":"+this.direction+":"+this.speed+":@");
    }

    public boolean driveBackward(int speed)
    {
        this.direction = "b";
        this.speed = speed;
        return this.sendAndReceive(":" + this.direction + ":" + speed + ":@");
    }

    public boolean release()
    {
        this.direction = "s";
        return this.sendAndReceive(":" + this.direction + ":@");
    }

    public boolean turnLeft()
    {
        return this.sendAndReceive(":l:@");
    }

    public boolean turnRight()
    {
        return this.sendAndReceive(":r:@");
    }
}
