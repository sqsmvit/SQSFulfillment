package com.sqsmv.sqsfulfillment;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class SwipeUpMenuListener extends GestureDetector.SimpleOnGestureListener
{
    private Activity originActivity;

    public SwipeUpMenuListener(Activity activity)
    {
        originActivity = activity;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY)
    {
        float deltaX = Math.abs(event2.getX() - event1.getX());
        float deltaY = Math.abs(event2.getY() - event1.getY());
        if(deltaY > deltaX && Math.abs(velocityY) > 5000)
        {
            if((event2.getY() - event1.getY()) < 0)
            {
                originActivity.openOptionsMenu();
            }
        }
        return true;
    }
}
