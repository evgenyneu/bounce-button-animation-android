package com.evgenii.bubblebutton;

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
