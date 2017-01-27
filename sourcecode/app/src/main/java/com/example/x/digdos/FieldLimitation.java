package com.example.x.digdos;

import android.widget.EditText;

import org.joda.time.DateTime;

/**
 * Created by rusty on 25.2.2015.
 */

//Used to check whether or not a certain field filled by the user has bad information or not.
public class FieldLimitation
{
    public boolean checkName(String fieldContent)
    {
        return fieldContent.length() <= 100;
    }

    public boolean checkNote(String fieldContent)
    {
        return fieldContent.length() <= 1500;
    }

    public boolean checkMomentFieldValues(
            EditText dayEditText,
            EditText monthEditText,
            EditText yearEditText,
            EditText hourEditText,
            EditText minuteEditText
    )
    {
        try
        {
            int dayInt = Integer.parseInt(dayEditText.getText().toString());
            int monthInt = Integer.parseInt(monthEditText.getText().toString());
            int yearInt = Integer.parseInt(yearEditText.getText().toString());
            int hourInt = Integer.parseInt(hourEditText.getText().toString());
            int minuteInt = Integer.parseInt(minuteEditText.getText().toString());
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }

    public boolean checkMoment(Moment M)
    {
        try
        {
            DateTime testDT = new DateTime(
                    M.getYear(),
                    M.getMonth(),
                    M.getDay(),
                    M.getHour(),
                    M.getMinute()
            );
        }
        catch(Exception e)
        {
            return false;
        }

        return true;
    }

    public boolean checkLocation(String fieldContent)
    {
        int fieldContentAsInt;

        try
        {
            fieldContentAsInt = Integer.parseInt(fieldContent);
        }
        catch(Exception e)
        {
            return false;
        }

        return fieldContent.length() <= 6 && fieldContentAsInt >= 0;
    }

    public boolean checkPeriodicalFrequency(String fieldContent)
    {
        int fieldContentAsInt;

        try
        {
            fieldContentAsInt = Integer.parseInt(fieldContent);
        }
        catch(Exception e)
        {
            return false;
        }

        return fieldContentAsInt > 0;
    }

    public boolean checkUsername(String fieldContent)
    {
        return fieldContent.length() > 0 && fieldContent.length() <= 100;
    }

    public boolean checkPassword(String fieldContent)
    {
        return fieldContent.length() > 0 && fieldContent.length() <= 100;
    }
}
