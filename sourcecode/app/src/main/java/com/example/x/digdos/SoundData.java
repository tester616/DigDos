package com.example.x.digdos;


public class SoundData
{
    private boolean active;
    private String name;
    private int volume;

    public SoundData()
    {

    }

    public SoundData(boolean active, String name, int volume)
    {
        this.active = active;
        this.name = name;
        this.volume = volume;
    }

    public boolean getActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getVolume()
    {
        return volume;
    }

    public void setVolume(int volume)
    {
        this.volume = volume;
    }

    public String getClassData()
    {
        return "(SD BEGIN"+
                " active = "+active+
                ", name = "+name+
                ", volume = "+volume+
                " SD END)";
    }
}
