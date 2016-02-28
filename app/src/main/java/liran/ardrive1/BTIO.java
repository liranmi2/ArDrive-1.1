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
    private String direction = "release";
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
//      need to use the output stream and try to send string streams to arduino
        try {
            byte[] write_cmd = command.getBytes();
            mmOutputStream.write(write_cmd);
            Thread.sleep(100);
            byte[] readBuffer = new byte[255];
            if (mmInputStream.available() > 0)
            {
                int bytes = mmInputStream.read(readBuffer);
                String response = new String(readBuffer, 0, bytes);
                return (response.equals("OK"));
            }
            return false;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean driveForward(int speed)
    {
        this.direction = "forward";
        this.speed = speed;
        return this.sendAndReceive(":forward:" + speed + ":@");
    }

    public boolean driveTo()
    {
        return this.sendAndReceive(":"+this.direction+":@");
    }

    public boolean driveBackward(int speed)
    {
        this.direction = "backward";
        this.speed = speed;
        return this.sendAndReceive(this.direction + speed + ":@");
    }

    public boolean release()
    {
        this.direction = "release";
        return this.sendAndReceive(this.direction + ":@");
    }

    public boolean turnLeft()
    {
        return this.sendAndReceive(":left:@");
    }

    public boolean turnRight()
    {
        return this.sendAndReceive(":right:@");
    }
}
