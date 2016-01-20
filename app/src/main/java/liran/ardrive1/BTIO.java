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

    private static BTIO instance = null;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;
    private OutputStream mmOutputStream;
    private InputStream mmInputStream;
    private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard //SerialPortService ID
    private int direction = 0;
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
                if (device.getName().equals("LIRANVOSTRO")) {
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

    public boolean sendOutput()
    {
//      need to use the output stream and try to send string streams to arduino
        try {
            mmOutputStream.write(new Byte(":"+direction+"$"+speed+":"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setSpeed (int speed)
    //  some basic guess of how to use the output stream format
    {
        this.speed = speed;
        sendOutput();
    }

    public void setDirection(int direction)
    {
        this.direction = direction;
        sendOutput();
    }


}
