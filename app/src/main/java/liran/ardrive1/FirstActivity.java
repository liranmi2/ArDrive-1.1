package liran.ardrive1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    BTIO btio = new BTIO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(context, "Failed to connect", duration);
        final Button btnCon = (Button) findViewById(R.id.btnConnect);
        final Button btnEnt = (Button)findViewById(R.id.btnEnter);


        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnCon.getText().toString().equals("connect")) {
                    try {
                        btio.findBT();
                        btio.openBT();
                    } catch (Exception ex) {
                        toast.show();
                        //Log.e("Ardrive1", "exception", ex);
                    }
                    btnCon.setText("enter");
                }
//              else if(btnCon.getText().toString().equals("enter"))
//                {
//                    Intent intent = new Intent(FirstActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
            }
        });

        btnEnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

//    void findBT() {
//
//        //myLabel.setText("Bluetooth Device Found");
//    }
//
//    void openBT() throws Exception {
//        //beginListenForData();
//        //myLabel.setText("Bluetooth Opened");
//    }

}
