package com.example.x.digdos;

import java.util.ArrayList;

public class ActivationData
{
    private Moment activationMoment,
            snoozeActivationMoment;
    private boolean snoozeActivityState;
    private int location,
            amountOfTimesDisplayed;

    //Empty constructor in case it's needed.
    public ActivationData()
    {

    }

    //Constructor giving a chosen value to activationMoment.
    public ActivationData(Moment activationMoment)
    {
        this.activationMoment = activationMoment;
        location = 0;
        snoozeActivityState = false;
        snoozeActivationMoment = new Moment(
                1,
                1,
                8999,
                0,
                0
        );
        amountOfTimesDisplayed = 0;
    }

    //Constructor giving chosen values to activationMoment and location.
    public ActivationData(Moment activationMoment, int location)
    {
        this.activationMoment = activationMoment;
        this.location = location;
        snoozeActivityState = false;
        snoozeActivationMoment = new Moment(
                1,
                1,
                8999,
                0,
                0
        );
        amountOfTimesDisplayed = 0;
    }

    //Overrides the default toString() method of a class. Used to display wanted data in a spinner.
    @Override
    public String toString()
    {
        return activationMoment.toString();
    }

    public Moment getActivationMoment()
    {
        return activationMoment;
    }

    public void setActivationMoment(Moment activationMoment)
    {
        this.activationMoment = activationMoment;
    }

    public Moment getSnoozeActivationMoment()
    {
        return snoozeActivationMoment;
    }

    public void setSnoozeActivationMoment(Moment snoozeActivationMoment)
    {
        this.snoozeActivationMoment = snoozeActivationMoment;
    }

    public boolean getSnoozeActivityState()
    {
        return snoozeActivityState;
    }

    public void setSnoozeActivityState(boolean snoozeActivityState)
    {
        this.snoozeActivityState = snoozeActivityState;
    }

    public int getLocation()
    {
        return location;
    }

    public void setLocation(int location)
    {
        this.location = location;
    }

    public int getAmountOfTimesDisplayed()
    {
        return amountOfTimesDisplayed;
    }

    public void setAmountOfTimesDisplayed(int amountOfTimesDisplayed)
    {
        this.amountOfTimesDisplayed = amountOfTimesDisplayed;
    }

    //Return all information about this class as a String.
    public String getClassData()
    {
        return "(AD BEGIN"+
                " activationMoment = "+activationMoment.getClassData()+
                " snoozeActivationMoment = "+snoozeActivationMoment.getClassData()+
                " snoozeActivityState = "+snoozeActivityState+
                " location = "+location+
                " amountOfTimesDisplayed = "+amountOfTimesDisplayed+
                " AD END)";
    }
}






/*
Note that DateTime objects take in the order of date as year, month, day in the constructor (followed by hour, minute)
instead of day, month, year
*/