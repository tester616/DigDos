package com.example.x.digdos;


public class ImageData
{
    private boolean active;
    private String name;

    public ImageData()
    {

    }

    public ImageData(boolean active, String name)
    {
        this.active = active;
        this.name = name;
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

    public String getClassData()
    {
        return "(ID BEGIN"+
                " active = "+active+
                ", name = "+name+
                " ID END)";
    }
}
