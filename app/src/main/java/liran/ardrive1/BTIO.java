package liran.ardrive1;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


/**
 * Created by Liran on 23/11/2015.
 * changed by Liran on 20/01/2016
 */

public class BTIO extends Activity {

//    Thread workerThread;
//    byte[] readBuffer;
//    int readBufferPosition;
//    //int counter;
//    volatile boolean stopWorker;

    // Liran:
    // I created this class in order for it to work with one instance,
    // so I will be able to connect in the first screen, and make changes in the second screen

    private static BTIO instance = null;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;
    private OutputStream mmOutputStream;
    private InputStream mmInputStream;
    private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard //SerialPortService ID
    private String direction = "release";
    private int speed = 0;

    protected BTIO(){} //only for instance

    public static BTIO getInstance()
    {
        if(instance == null)
        {
            instance = new BTIO();
        }
        return instance;
    }

    public boolean findBT()
    {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if(mBluetoothAdapter == null) {
//            // myLabel.setText("No bluetooth adapter available");
//        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0) {
            for(BluetoothDevice device : pairedDevices)
                if (device.getName().equals("HC-05")) {
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

    public boolean sendAndReceive(String command)
    {
//      need to use the output stream and try to send string streams to arduino
        try {
            byte[] write_cmd = command.getBytes();
            mmOutputStream.write(write_cmd);
            Thread.sleep(100);
            byte[] readBuffer = new byte[255];
            int bytes = mmInputStream.read(readBuffer);
            String response = new String(readBuffer, 0, bytes);
            return (response.equals("OK"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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

    public boolean TurnLeft()
    {
        return this.sendAndReceive(":left:@");
    }

    public boolean TurnRight()
    {
        return this.sendAndReceive(":right:@");
    }
}
