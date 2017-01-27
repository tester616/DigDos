package com.example.x.digdos;


/**
 * Created by rusty on 24.3.2015.
 */

//Class used to store and represent information from the table sound_data.
/*public class SoundData
{
    private boolean active;
    private String name;
    private int volume;

    //Empty constructor.
    public SoundData()
    {

    }

    //Constructor giving values.
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

    //Return all information about this class as a String.
    public String getClassData()
    {
        return "(SD BEGIN"+
                " active = "+active+
                ", name = "+name+
                ", volume = "+volume+
                " SD END)";
    }
}*/



//CODE WITH SWEDISH COMMENTS BELOW.


//Klass som i programmet representerar data från kolumnen sound_data i tabellen reminder.
public class SoundData
{
    private boolean active;
    private String name;
    private int volume;

    //Tom konstruktor.
    public SoundData()
    {

    }

    //Denna konstruktor ger värden genast och används vid normala fall.
    public SoundData(boolean active, String name, int volume)
    {
        this.active = active;
        this.name = name;
        this.volume = volume;
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

    public int getVolume()
    {
        return volume;
    }

    public void setVolume(int volume)
    {
        this.volume = volume;
    }

    //Returnera alla klassens variabler som teckensträngar.
    //Används för snabb åtkomst vid testkörning.
    public String getClassData()
    {
        return "(SD BEGIN"+
                " active = "+active+
                ", name = "+name+
                ", volume = "+volume+
                " SD END)";
    }
}
