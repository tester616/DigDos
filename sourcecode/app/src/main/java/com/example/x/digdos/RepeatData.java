package com.example.x.digdos;

import java.util.ArrayList;


public class RepeatData
{
    private boolean active;
    private int mode,
            periodicalFrequency,
            periodicalSpinnerPosition;
    private ActivationData periodicalActiveUntil;
    private ArrayList<ActivationData> customActivationDataList;

    //Empty constructor in case it's needed.
    public RepeatData()
    {

    }

    //Constructor giving values.
    public RepeatData(
            boolean active,
            int mode,
            int periodicalFrequency,
            int periodicalSpinnerPosition,
            ActivationData periodicalActiveUntil,
            ArrayList<ActivationData> customActivationDataList
    )
    {
        this.active = active;
        this.mode = mode;
        this.periodicalFrequency = periodicalFrequency;
        this.periodicalSpinnerPosition = periodicalSpinnerPosition;
        this.periodicalActiveUntil = periodicalActiveUntil;
        this.customActivationDataList = customActivationDataList;
    }

    public boolean getActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public int getMode()
    {
        return mode;
    }

    public void setMode(int mode)
    {
        this.mode = mode;
    }

    public int getPeriodicalFrequency()
    {
        return periodicalFrequency;
    }

    public void setPeriodicalFrequency(int periodicalFrequency)
    {
        this.periodicalFrequency = periodicalFrequency;
    }

    public int getPeriodicalSpinnerPosition()
    {
        return periodicalSpinnerPosition;
    }

    public void setPeriodicalSpinnerPosition(int periodicalSpinnerPosition)
    {
        this.periodicalSpinnerPosition = periodicalSpinnerPosition;
    }

    public ActivationData getPeriodicalActiveUntil()
    {
        return periodicalActiveUntil;
    }

    public void setPeriodicalActiveUntil(ActivationData periodicalActiveUntil)
    {
        this.periodicalActiveUntil = periodicalActiveUntil;
    }

    public ArrayList<ActivationData> getCustomActivationDataList()
    {
        return customActivationDataList;
    }

    public void setCustomActivationDataList(ArrayList<ActivationData> customActivationDataList)
    {
        this.customActivationDataList = customActivationDataList;
    }

    //Return all information about this class as a String.
    public String getClassData()
    {
        return "(RD BEGIN"+
                " active = "+active+
                ", mode = "+mode+
                ", periodicalFrequency = "+periodicalFrequency+
                ", periodicalSpinnerPosition = "+periodicalSpinnerPosition+
                ", periodicalActiveUntil = "+periodicalActiveUntil.getClassData()+
                ", customActivationDataList = "+customActivationDataList.toString()+
                " RD END)";
    }
}
