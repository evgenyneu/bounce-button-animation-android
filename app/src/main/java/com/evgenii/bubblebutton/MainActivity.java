package com.evgenii.bubblebutton;

import android.content.Context;
import android.graphics.Interpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BaseInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;

class SpringInterpolator extends BaseInterpolator {
    public SpringInterpolator() {
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public SpringInterpolator(Context context, AttributeSet attrs) {
    }

    private static float bounce(float t) {
        return t * t * 8.0f;
    }

    public float getInterpolation(float t) {
        // _b(t) = t * t * 8
        // bs(t) = _b(t) for t < 0.3535
        // bs(t) = _b(t - 0.54719) + 0.7 for t < 0.7408
        // bs(t) = _b(t - 0.8526) + 0.9 for t < 0.9644
        // bs(t) = _b(t - 1.0435) + 0.95 for t <= 1.0
        // b(t) = bs(t * 1.1226)
        t *= 1.1226f;
        if (t < 0.3535f) return bounce(t);
        else if (t < 0.7408f) return bounce(t - 0.54719f) + 0.7f;
        else if (t < 0.9644f) return bounce(t - 0.8526f) + 0.9f;
        else return bounce(t - 1.0435f) + 0.95f;
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
        Interpolator interpolator = new SpringInterpolator();
        myAnim.setInterpolator(interpolator);
        view.startAnimation(myAnim);
    }
}
