package epeden.nightwalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.channguyen.rsv.RangeSliderView;

import org.w3c.dom.Text;

/**
 * Created by epeden on 4/30/16.
 */
public class SettingsActivity extends AppCompatActivity {
        SeekBar snoozeLimitSeekBar;
        TextView snoozeLimitTextView;
        SeekBar snoozeDurationSeekBar;
        TextView snoozeDurationTextView;

        private RangeSliderView snoozeLimitSlider;
    private RangeSliderView snoozeDurationSlider;


    Settings settings = Settings.findById(Settings.class, 1);

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings);



            snoozeLimitTextView = (TextView) findViewById(R.id.snoozeLimitTextView);
            snoozeDurationTextView = (TextView) findViewById(R.id.snoozeDurationTextView);

            snoozeLimitSlider = (RangeSliderView) findViewById(R.id.snoozeLimitRangeSlider);
            snoozeDurationSlider = (RangeSliderView) findViewById(R.id.snoozeDurationRangeSlider);

            final RangeSliderView.OnSlideListener limitListener = new RangeSliderView.OnSlideListener() {
                @Override
                public void onSlide(int index) {
                    int newSnoozeLimit = 0;
                    newSnoozeLimit = index;
                    settings.setSnoozeLimit(newSnoozeLimit);
                    settings.save();
                    snoozeLimitTextView.setText("Covered: " + newSnoozeLimit + "/" + snoozeLimitSlider.getRangeCount());
                }
            };
            final RangeSliderView.OnSlideListener durationListener = new RangeSliderView.OnSlideListener() {
                @Override
                public void onSlide(int index) {
                    int newSnoozeDuration = 0;
                    newSnoozeDuration = index;
                    settings.setSnoozeDuration(newSnoozeDuration);
                    settings.save();
                    snoozeDurationTextView.setText("Snooze Duration: " + newSnoozeDuration + "/" + snoozeDurationSlider.getRangeCount());
                }
            };

            snoozeLimitSlider.setOnSlideListener(limitListener);
            snoozeDurationSlider.setOnSlideListener(durationListener);







//            snoozeLimitTextView = (TextView) findViewById(R.id.snoozeLimitTextView);
//            snoozeLimitSeekBar = (SeekBar) findViewById(R.id.snoozeLimitSeekBar);
//            snoozeLimitSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                int newSnoozeLimit = 0;
//
//                @Override
//                public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
//                    newSnoozeLimit = progressValue;
//                    settings.setSnoozeLimit(newSnoozeLimit);
//                    settings.save();
////                    Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar seekBar) {
////                    Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onStopTrackingTouch(SeekBar seekBar) {
//                    snoozeLimitTextView.setText("Covered: " + newSnoozeLimit + "/" + seekBar.getMax());
////                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
//                }
//
//            });
//            snoozeDurationTextView = (TextView) findViewById(R.id.snoozeDurationTextView);
//            snoozeDurationSeekBar = (SeekBar) findViewById(R.id.snoozeDurationSeekBar);
//            snoozeDurationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                int newSnoozeDuration = 0;
//
//                @Override
//                public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
//                    newSnoozeDuration = progresValue;
//                    settings.setSnoozeDuration(newSnoozeDuration);
//                    settings.save();
////                    Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar seekBar) {
////                    Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onStopTrackingTouch(SeekBar seekBar) {
//                    snoozeDurationTextView.setText("Snooze Duration: " + newSnoozeDuration + "/" + seekBar.getMax());
////                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
//                }
//
//            });
        }

    public void backButtonPushed(View v) {
        Intent main_intent = new Intent(this,MainActivity.class);
        startActivity(main_intent);
    }

}