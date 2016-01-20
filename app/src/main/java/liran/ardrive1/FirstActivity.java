package liran.ardrive1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
public class FirstActivity extends AppCompatActivity {

    BTIO btio = new BTIO();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        final Button btnCon = (Button) findViewById(R.id.btnConnect);
        final Button btnEnt = (Button)findViewById(R.id.btnEnter);


        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    btio.findBT();
                    btio.openBT();
                }
                catch (Exception ex) {
                    Log.e("Ardrive1","exception",ex);
                }
            }
        });

        btnEnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnEnt.isClickable()) {
                    Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                    startActivity(intent);
                }
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
