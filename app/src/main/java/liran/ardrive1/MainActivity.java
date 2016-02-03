package liran.ardrive1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BTIO btio = new BTIO();
    Context context = getApplicationContext();
    int duration = Toast.LENGTH_SHORT;
    Toast toast = Toast.makeText(context, "Failed to update", duration);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton rightArr = (ImageButton) findViewById(R.id.rightArrow);
        rightArr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!btio.turnRight()){
                    toast.show();
                }
            }
        });

        final ImageButton leftArr = (ImageButton) findViewById(R.id.leftArrow);
        leftArr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!btio.turnLeft()){
                    toast.show();
                }
            }
        });

        final TextView speedText = (TextView) findViewById(R.id.speedTextView);

        final SeekBar carSpeed = (SeekBar) findViewById(R.id.seekBar);

        carSpeed.setMax(300);
        carSpeed.setProgress(45);

        carSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
         //the seek bar updating the BTIO
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // I assume that the speed is fully controlled by the app,
            // and so I present on the local variable "speed" of BTIO instance
//                speedText.setText("" + btio.getSpeed());
//                btio.setSpeed(progress + 150);
                if (progress > 45) {
                    if(!btio.goForwardAt(progress - 45)){
                        toast.show();
                    }
                }
                else if (progress < 45) {
                    if(!btio.goBackwardAt(45 - progress)){
                        toast.show();
                    }
                }
                else {
                    if(!btio.stop()){
                        toast.show();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        }
        );

    }

}
