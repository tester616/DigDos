package com.example.x.digdos;


/**
 * Created by rusty on 24.3.2015.
 */

//Class containing information about images that are connected to reminders.
/*public class ImageData
{
    private boolean active;
    private String name;

    //Empty constructor in case it's needed.
    public ImageData()
    {

    }

    //Constructor giving values.
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

    //Return all information about this class as a String.
    public String getClassData()
    {
        return "(ID BEGIN"+
                " active = "+active+
                ", name = "+name+
                " ID END)";
    }
}*/



//CODE WITH SWEDISH COMMENTS BELOW.



//Klass som i programmet representerar data från kolumnen image_data i tabellen reminder.
public class ImageData
{
    private boolean active;
    private String name;

    //Tom konstruktor.
    public ImageData()
    {

    }

    //Denna konstruktor ger värden genast och används vid normala fall.
    public ImageData(boolean active, String name)
    {
        this.active = active;
        this.name = name;
    }

    //Get/set metoderna nedan används för att antingen ändra eller komma åt klassens variabler.
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

    //Returnera alla klassens variabler som teckensträngar.
    //Används för snabb åtkomst vid testkörning.
    public String getClassData()
    {
        return "(ID BEGIN"+
                " active = "+active+
                ", name = "+name+
                " ID END)";
    }
}
