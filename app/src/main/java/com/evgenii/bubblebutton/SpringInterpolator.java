package com.evgenii.bubblebutton;

//
// Interpolator to be used with animation to achieve the spring-bounce effect.
// License: MIT
// Source: http://evgenii.com
//
// Usage example:
// ------------
//
//    // Start animation
//    final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
//    double animationDuration = getDurationValue() * 1000;
//    SpringInterpolator interpolator = new SpringInterpolator(getVelocityValue(), getDampingValue());
//    myAnim.setInterpolator(interpolator);
//    Button button = (Button)findViewById(R.id.play_button);
//    button.startAnimation(myAnim);
//
// anim/bounce.xml file:
// --------------------
//
//    <?xml version="1.0" encoding="utf-8"?>
//    <set xmlns:android="http://schemas.android.com/apk/res/android" >
//
//    <scale
//    android:duration="2000"
//            android:fromXScale="0.3"
//            android:toXScale="1.0"
//            android:fromYScale="0.3"
//            android:toYScale="1.0"
//            android:pivotX="50%"
//            android:pivotY="50%" />
//    </set>
//
//
class SpringInterpolator implements android.view.animation.Interpolator {
    // The speed at the start of the animation
    double mInitialSpringVelocity = 10;

    // The damping ratio of the animation. Values between 0 and 1 produce more wobbly effect.
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
