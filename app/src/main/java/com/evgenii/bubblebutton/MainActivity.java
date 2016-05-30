package com.evgenii.bubblebutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


class SpringInterpolator implements android.view.animation.Interpolator {
    double mInitialSpringVelocity = 10;
    double mDamping = 2;

    SpringInterpolator(double initialSpringVelocity, double damping) {
        mDamping = damping;
        mInitialSpringVelocity = initialSpringVelocity;
    }

    public float getInterpolation(float t) {
        double time = (double) t;
        double dampingMultiplier = 10;
        double velocityMultiplier = 10;
        return (float) (-Math.pow(Math.E, -mDamping * dampingMultiplier * time) * Math.cos(mInitialSpringVelocity * velocityMultiplier * time) + 1);
    }
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void didTapPlayButton(View view) {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        SpringInterpolator interpolator = new SpringInterpolator(6, 0.2);
        myAnim.setInterpolator(interpolator);
        view.startAnimation(myAnim);
    }
}
