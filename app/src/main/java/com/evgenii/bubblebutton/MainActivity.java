package com.evgenii.bubblebutton;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDampingSeekBar();
        setupVelocitySeekBar();
        setupDurationVar();
    }

    public void onDestroy() {
        mPlayer.stop();
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
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        double animationDuration = getDurationValue() * 1000;
        myAnim.setDuration((long)animationDuration);
        SpringInterpolator interpolator = new SpringInterpolator(getVelocityValue(), getDampingValue());
        myAnim.setInterpolator(interpolator);
        Button button = (Button)findViewById(R.id.play_button);
        button.startAnimation(myAnim);
        playSound();

        myAnim.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationEnd(Animation arg0) {
                animateButton();
            }
        });
    }

    void playSound() {
        if (mPlayer == null) {
            mPlayer = MediaPlayer.create(MainActivity.this, R.raw.bubble);
        }
        mPlayer.seekTo(0);
        mPlayer.start();
    }

    // Duration controls
    // ---------------

    void setupDurationVar() {
        final SeekBar seekBar =(SeekBar) findViewById(R.id.seek_bar_duration);
        seekBar.setProgress(10);
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


    // Damping controls
    // ---------------

    void setupDampingSeekBar() {
        final SeekBar dampingSeekBar =(SeekBar) findViewById(R.id.seek_bar_damping);
        dampingSeekBar.setProgress(10);
        updateDampingLabel();

        dampingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                animateButton();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                updateDampingLabel();
            }
        });
    }

    void updateDampingLabel() {
        TextView textViewDamping = (TextView) findViewById(R.id.text_view_damping);
        updateTextViewValue(textViewDamping, "Damping", getDampingValue());
    }

    double getDampingValue() {
        final SeekBar dampingSeekBar = (SeekBar) findViewById(R.id.seek_bar_damping);
        return getSeekBarValue(dampingSeekBar, 0.05);
    }


    // Velocity controls
    // ---------------

    void setupVelocitySeekBar() {
        final SeekBar seekBar =(SeekBar) findViewById(R.id.seek_bar_velocity);
        seekBar.setProgress(20);
        updateVelocityLabel();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                animateButton();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                updateVelocityLabel();
            }
        });
    }

    void updateVelocityLabel() {
        TextView textView = (TextView) findViewById(R.id.text_view_velocity);
        updateTextViewValue(textView, "Velocity", getVelocityValue());
    }

    double getVelocityValue() {
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seek_bar_velocity);
        return getSeekBarValue(seekBar, 0.1);
    }
}
