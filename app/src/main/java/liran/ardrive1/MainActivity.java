package liran.ardrive1;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    BTIO btio = new BTIO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton rightArr = (ImageButton) findViewById(R.id.rightArrow);
        rightArr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btio.setDirection(3);
            }
        });

        final ImageButton leftArr = (ImageButton) findViewById(R.id.leftArrow);
        leftArr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btio.setDirection(4);
            }
        });

        final TextView speedText = (TextView) findViewById(R.id.speedTextView);

        final SeekBar carSpeed = (SeekBar) findViewById(R.id.seekBar);

        carSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speedText.setText("" + progress);
                btio.setSpeed(progress + 150);
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
