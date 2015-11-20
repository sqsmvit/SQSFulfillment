package com.sqsmv.sqsfulfillment;

import android.view.View;

public class WakeTimer
{
    private Thread timerThread;
    private int timerCount;
    private View lockView;

    public WakeTimer(View view)
    {
        lockView = view;
    }

    public void updateTimerThread()
    {
        if(timerThread == null)
        {
            timerThread = new Thread()
            {
                @Override
                public void run()
                {
                    timerCount = 0;
                    lockView.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            lockView.setKeepScreenOn(true);
                        }
                    });
                    try
                    {
                        while(timerCount < 30)//300)
                        {
                            sleep(1000);
                            System.out.println(timerCount);
                            timerCount++;
                        }
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    lockView.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            lockView.setKeepScreenOn(false);
                        }
                    });
                    timerThread = null;
                }
            };
            timerThread.start();
        }
        else
        {
            timerCount = 0;
        }
    }

    public void killTimer()
    {
        if(timerThread != null)
        {
            timerThread.interrupt();
            timerThread = null;
        }
    }
}
