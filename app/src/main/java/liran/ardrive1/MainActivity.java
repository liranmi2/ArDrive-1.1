package liran.ardrive1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int releasePoint = 145;
    final int max = 400;
    private BTIO btio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btio = FirstActivity.btio;
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        final Toast[] toast = {Toast.makeText(context, "Failed to update", duration)};

        final ImageButton rightArr = (ImageButton) findViewById(R.id.rightArrow);

        rightArr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!btio.turnRight()) {
                        toast[0].show();
                    }
//                    toast[0] = Toast.makeText(context, "Right", duration);
//                    toast[0].show();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (!btio.driveTo()) {
                        toast[0].show();
                    }
//                    toast[0] = Toast.makeText(context, "Forward", duration);
//                    toast[0].show();
                }
                return true;
            }
        });


        final ImageButton leftArr = (ImageButton) findViewById(R.id.leftArrow);

        leftArr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            //LIRAN 22/02/2016
            //
            //the ontouch function handles the turns correctly by responding also when you leave the button, and return to recent used direction
            //
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!btio.turnLeft()) {
                        toast[0].show();
                    }
//                    toast[0] = Toast.makeText(context, "Left", duration);
//                    toast[0].show();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (!btio.driveTo()) {
                        toast[0].show();
                    }
//                    toast[0] = Toast.makeText(context, "Forward", duration);
//                    toast[0].show();
                }
                return true;
            }
        });

        final TextView speedText = (TextView) findViewById(R.id.speedTextView);

        final SeekBar carSpeed = (SeekBar) findViewById(R.id.seekBar);

        carSpeed.setMax(max);
        carSpeed.setProgress(releasePoint);

        carSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                //the seek bar updating the BTIO
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // I assume that the speed is fully controlled by the app,
                    // and so I present on the local variable "speed" of BTIO instance
//                speedText.setText("" + btio.getSpeed());
//                btio.setSpeed(progress + 150);
                    if (progress > releasePoint) {
                        if (!btio.driveForward(progress - releasePoint)) {
                            toast[0].show();

                        }
                        speedText.setText("D " + (progress - releasePoint));
                        speedText.setTextColor(Color.GREEN);
                    } else if (progress < releasePoint) {
                        if (!btio.driveBackward(releasePoint - progress)) {
                            toast[0].show();
                        }
                        speedText.setText("R " + (releasePoint - progress));
                        speedText.setTextColor(Color.RED);
                    } else {
                        if (!btio.release()) {
                            toast[0].show();
                        }
                        speedText.setText("N");
                        speedText.setTextColor(Color.YELLOW);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            }
        );

    }

}
