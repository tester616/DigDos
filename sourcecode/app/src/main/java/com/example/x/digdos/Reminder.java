package com.example.x.digdos;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.ArrayList;


//Class for creating objects representing a created reminder.
public class Reminder
{
    private int id,
            type;
    private String name,
            note;
    private ActivationData initialActivation;
    private ImageData imageData;
    private SoundData soundData;
    private RepeatData repeatData;
    private ActivationData latestActivation;
    private int amountOfTimesDisplayed;

    //Empty constructor just in case it is needed for some reason.
    public Reminder()
    {

    }

    //Constructor giving values to all of the variables. Used in updating.
    public Reminder(
            int id,
            int type,
            String name,
            String note,
            ActivationData initialActivation,
            ImageData imageData,
            SoundData soundData,
            RepeatData repeatData,
            ActivationData latestActivation,
            int amountOfTimesDisplayed
    )
    {
        this.id = id;
        this.type = type;
        this.name = name;
        this.note = note;
        this.initialActivation = initialActivation;
        this.imageData = imageData;
        this.soundData = soundData;
        this.repeatData = repeatData;
        this.latestActivation = latestActivation;
        this.amountOfTimesDisplayed = amountOfTimesDisplayed;
    }

    //Constructor giving values to some of the variables. Used in initial creation
    public Reminder(
            int id,
            int type,
            String name,
            String note,
            ActivationData initialActivation,
            ImageData imageData,
            SoundData soundData,
            RepeatData repeatData
    )
    {
        this.id = id;
        this.type = type;
        this.name = name;
        this.note = note;
        this.initialActivation = initialActivation;
        this.imageData = imageData;
        this.soundData = soundData;
        this.repeatData = repeatData;
        latestActivation = new ActivationData(
                new Moment(
                        1,
                        1,
                        8999,
                        0,
                        0
                )
        );
        amountOfTimesDisplayed = 0;
    }

    //Overrides the default toString() method of a class, now instead only returning nameOrTitle which is needed as the visible text in spinners.
    @Override
    public String toString()
    {
        return name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public ActivationData getInitialActivation()
    {
        return initialActivation;
    }

    public void setInitialActivation(ActivationData initialActivation)
    {
        this.initialActivation = initialActivation;
    }

    public ImageData getImageData()
    {
        return imageData;
    }

    public void setImageData(ImageData imageData)
    {
        this.imageData = imageData;
    }

    public SoundData getSoundData()
    {
        return soundData;
    }

    public void setSoundData(SoundData soundData)
    {
        this.soundData = soundData;
    }

    public RepeatData getRepeatData()
    {
        return repeatData;
    }

    public void setRepeatData(RepeatData repeatData)
    {
        this.repeatData = repeatData;
    }

    public ActivationData getLatestActivation()
    {
        return latestActivation;
    }

    public void setLatestActivation(ActivationData latestActivation)
    {
        this.latestActivation = latestActivation;
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
        return "(REMINDER BEGIN"+
                " id = "+id+
                ", type = "+type+
                ", name = "+name+
                ", note = "+note+
                ", initialActivation = "+initialActivation.getClassData()+
                ", imageData = "+imageData.getClassData()+
                ", soundData = "+soundData.getClassData()+
                ", repeatData = "+repeatData.getClassData()+
                ", latestActivation = "+latestActivation.getClassData()+
                ", amountOfTimesDisplayed = "+amountOfTimesDisplayed+
                " REMINDER END)";
    }
}


//Methods needed if implements Parcelable is used.
/*
    //Required for Parcelable.
    public Medicine(Parcel in) {
        id = in.readInt();
        name = in.readString();
        location = in.readString();
        note = in.readString();
        repeat = in.readByte() != 0;
        sound = in.readByte() != 0;
        day = in.readInt();
        month = in.readInt();
        year = in.readInt();
        hour = in.readInt();
        minute = in.readInt();
        repeatFrequencyInt = in.readInt();
        repeatFrequencySpinnerPosition = in.readInt();
    }

    //Required for Parcelable.
    @Override
    public int describeContents()
    {
        return 0;
    }

    //Required for Parcelable.
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(location);
        dest.writeString(note);
        dest.writeByte((byte) (repeat ? 1 : 0));
        dest.writeByte((byte) (sound ? 1 : 0));
        dest.writeInt(day);
        dest.writeInt(month);
        dest.writeInt(year);
        dest.writeInt(hour);
        dest.writeInt(minute);
        dest.writeInt(repeatFrequencyInt);
        dest.writeInt(repeatFrequencySpinnerPosition);
    }
*/