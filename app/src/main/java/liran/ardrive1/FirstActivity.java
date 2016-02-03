package liran.ardrive1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        final BTIO btio = new BTIO();

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
                        Log.e("Ardrive1", "exception", ex);
                    }
                    btnCon.setText("enter");
                }
//                if(btnCon.getText().toString().equals("enter"))
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
