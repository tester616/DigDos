package com.example.x.digdos;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;


public class AlarmManagerControls
{
    private Context ctx;
    private int frequency = 60;

    //Default constructor in case it's needed for something.
    public AlarmManagerControls()
    {

    }

    //Constructor.
    public AlarmManagerControls(Context ctx)
    {
        this.ctx = ctx;
    }

    //Control how often an active repeating alarm performs checks.
    //Needs to be called before the repeating alarm is activated for the new frequency
    //to have effect.
    public void setFrequency(int frequency)
    {
        this.frequency = frequency;
    }

    //Activate a repeating alarm that checks for either Medicine or Notification data to show
    //to the user.
    public void setRepeatingAlarmState(boolean status)
    {
        //Create alarmMgr and launch a it repeatedly with alarmIntent using intent
        //of AlarmManagerWithActivity.
        AlarmManager alarmMgr = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        Intent alarmManagerActivityIntent = new Intent(ctx, AlarmManagerActivity.class);
        PendingIntent alarmIntent = PendingIntent.getActivity(
                ctx,
                0,
                alarmManagerActivityIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        if(status)
        {
            alarmMgr.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + 5 * 1000,
                    frequency * 1000,
                    alarmIntent
            );
        }
        else
        {
            alarmMgr.cancel(alarmIntent);
        }
    }
}
