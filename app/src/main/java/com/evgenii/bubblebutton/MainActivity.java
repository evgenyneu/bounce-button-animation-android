package com.evgenii.bubblebutton;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupAmplitudeSeekBar();
        setupFrequencySeekBar();
        setupDurationVar();

        animateButton();
    }

    public void onDestroy() {
        // Stop the sound
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer = null;
        }

        super.onDestroy();
    }

    void updateTextViewValue(TextView textView, String prefix, double value) {
        String text = String.format("%s: %.2f", prefix, value);
        textView.setText(text);
    }

    double getSeekBarValue(SeekBar seekBar, double step) {
        return ((double)seekBar.getProgress() + 1.0) / ( 1.0 / step);
    }

    public void didTapPlayButton(View view) {
        animateButton();
    }

    void animateButton() {
        // Load the animation
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        double animationDuration = getDurationValue() * 1000;
        myAnim.setDuration((long)animationDuration);

        // Use custom animation interpolator to achieve the bounce effect
        MyBounceInterpolator interpolator = new MyBounceInterpolator(getAmplitudeValue(), getFrequencyValue());

        myAnim.setInterpolator(interpolator);

        // Animate the button
        Button button = (Button)findViewById(R.id.play_button);
        button.startAnimation(myAnim);
        playSound();

        // Run button animation again after it finished
        myAnim.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {}

            @Override
            public void onAnimationRepeat(Animation arg0) {}

            @Override
            public void onAnimationEnd(Animation arg0) {
                animateButton();
            }
        });
    }

    void playSound() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
        }

        mPlayer = MediaPlayer.create(MainActivity.this, R.raw.bubble);
        mPlayer.start();
    }

    // Duration controls
    // ---------------

    void setupDurationVar() {
        final SeekBar seekBar =(SeekBar) findViewById(R.id.seek_bar_duration);
        seekBar.setProgress(19);
        updateDurationLabel();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                animateButton();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                updateDurationLabel();
            }
        });
    }

    void updateDurationLabel() {
        TextView textView = (TextView) findViewById(R.id.text_view_duration);
        updateTextViewValue(textView, "Duration", getDurationValue());
    }

    double getDurationValue() {
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seek_bar_duration);
        return getSeekBarValue(seekBar, 0.1);
    }


    // Amplitude controls
    // ---------------

    void setupAmplitudeSeekBar() {
        final SeekBar dampingSeekBar =(SeekBar) findViewById(R.id.seek_bar_amplitude);
        dampingSeekBar.setProgress(19);
        updateAmplitudeLabel();

        dampingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                animateButton();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                updateAmplitudeLabel();
            }
        });
    }

    void updateAmplitudeLabel() {
        TextView textViewDamping = (TextView) findViewById(R.id.text_view_amplitude);
        updateTextViewValue(textViewDamping, "Amplitude", getAmplitudeValue());
    }

    double getAmplitudeValue() {
        final SeekBar dampingSeekBar = (SeekBar) findViewById(R.id.seek_bar_amplitude);
        return getSeekBarValue(dampingSeekBar, 0.01);
    }


    // Frequency controls
    // ---------------

    void setupFrequencySeekBar() {
        final SeekBar seekBar =(SeekBar) findViewById(R.id.seek_bar_frequency);
        seekBar.setProgress(39);
        updateFrequencyLabel();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                animateButton();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                updateFrequencyLabel();
            }
        });
    }

    void updateFrequencyLabel() {
        TextView textView = (TextView) findViewById(R.id.text_view_frequency);
        updateTextViewValue(textView, "Frequency", getFrequencyValue());
    }

    double getFrequencyValue() {
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seek_bar_frequency);
        return getSeekBarValue(seekBar, 0.5);
    }
}
