package com.example.x.digdos;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


//Class with methods for checking if any reminders should be displayed.
public class ActivationManagement
{
    private Context ctx;

    //Empty constructor in case it's needed for some reason.
    public ActivationManagement()
    {

    }

    //Constructor taking in Context.
    public ActivationManagement(Context ctx)
    {
        this.ctx = ctx;
    }

    //Check for certain type of reminders that need to be displayed.
    //Reminders to be displayed will be returned in a list.
    public ArrayList<Reminder> getRemindersToDisplay(int type)
    {
        ArrayList<Reminder> RList = new ArrayList<Reminder>();

        DatabaseQuery DQ = new DatabaseQuery(ctx);

        //Create DateTime with current time values. Should hopefully use the correct timezone for the using system.
        DateTime currentDT = new DateTime(DateTimeZone.getDefault());

        //Line for testing purposes when +2 hours don't apply automatically.
        //currentDT = currentDT.hourOfDay().addToCopy(2);

        //Create ActivationData with current time data. DONT USE FOR ANYTHING OTHER THAN THE LOG BELOW!
        ActivationData currentDnT = new ActivationData(
                new Moment(
                        currentDT.getDayOfMonth(),
                        currentDT.getMonthOfYear(),
                        currentDT.getYear(),
                        currentDT.getHourOfDay(),
                        currentDT.getMinuteOfHour()
                )
        );

        Log.v("This should be the current time", currentDnT.toString());

        //Log.v("original", year+" "+month+" "+day+" "+hour+" "+minute);
        //Log.v("datetime", currentDT.year().get()+" "+currentDT.monthOfYear().get()+" "+currentDT.dayOfMonth().get()+" "+currentDT.hourOfDay().get()+" "+currentDT.minuteOfHour().get());

        //Get information about all reminders of a type in CR.
        Cursor CR = DQ.getReminderInformation(type);

        Gson gson = new Gson();

        //Runs through all CR slots, checking if the medicines are suitable for being added to MList.
        while (CR.moveToNext())
        {
            //Create object of Reminder with current CR data.
            Reminder reminderToCheck = new Reminder(
                    CR.getInt(0),
                    CR.getInt(1),
                    CR.getString(2),
                    CR.getString(3),
                    gson.fromJson(CR.getString(4), ActivationData.class),
                    gson.fromJson(CR.getString(5), ImageData.class),
                    gson.fromJson(CR.getString(6), SoundData.class),
                    gson.fromJson(CR.getString(7), RepeatData.class),
                    gson.fromJson(CR.getString(8), ActivationData.class),
                    CR.getInt(9)
            );

            Log.v("Reminder check begins", "For reminder... " + reminderToCheck.getName());

            Log.v("Reminder information", "" + reminderToCheck.getClassData());

            if (checkSpecificReminder(reminderToCheck, currentDT))
            {
                RList.add(reminderToCheck);
            }
        }

        DQ.closeDatabaseHelper();

        //Returns MList.
        return RList;
    }

    //Give this method a Reminder (also the current DateTime) and it returns true if that reminder
    // needs to be displayed or false if not.
    private boolean checkSpecificReminder(Reminder reminderToCheck, DateTime currentDT)
    {
        //If the Reminder has never been displayed a simple check is done on the initial
        // activation moment since there is nothing else that can have an impact on the result.
        //If the Reminder has been displayed, various other checks need to be done in order to
        // decide the return value.
        if(reminderToCheck.getAmountOfTimesDisplayed() == 0)
        {
            //Reminder has never been displayed.

            if(hasDateArrived(
                    new DateTime(
                            reminderToCheck.getInitialActivation().getActivationMoment().getYear(),
                            reminderToCheck.getInitialActivation().getActivationMoment().getMonth(),
                            reminderToCheck.getInitialActivation().getActivationMoment().getDay(),
                            reminderToCheck.getInitialActivation().getActivationMoment().getHour(),
                            reminderToCheck.getInitialActivation().getActivationMoment().getMinute()
                    ),
                    currentDT
            ))
            {
                DatabaseQuery DQ = new DatabaseQuery(ctx);

                //Updates various variables for the reminder that just activated.
                reminderToCheck.getInitialActivation().setAmountOfTimesDisplayed(reminderToCheck.getInitialActivation().getAmountOfTimesDisplayed()+1);
                if(reminderToCheck.getRepeatData().getActive() && reminderToCheck.getRepeatData().getMode() == 0)
                {
                    reminderToCheck.getLatestActivation().setActivationMoment(getLatestActivationMoment(reminderToCheck, currentDT));
                }
                else
                {
                    reminderToCheck.getLatestActivation().setActivationMoment(
                            new Moment(
                                    currentDT.getDayOfMonth(),
                                    currentDT.getMonthOfYear(),
                                    currentDT.getYear(),
                                    currentDT.getHourOfDay(),
                                    currentDT.getMinuteOfHour()
                            )
                    );
                }
                if(reminderToCheck.getType() == GV.TYPE_MEDICINE &&
                        reminderToCheck.getRepeatData().getActive())
                {
                    reminderToCheck.getInitialActivation().setLocation(
                            reminderToCheck.getInitialActivation().getLocation()+1);
                }
                reminderToCheck.setAmountOfTimesDisplayed(reminderToCheck.getAmountOfTimesDisplayed()+1);

                //Updates the reminder in the database.
                DQ.updateReminder(reminderToCheck);

                return true;
            }
            else
            {
                return false;
            }
        }
        else if(reminderToCheck.getAmountOfTimesDisplayed() > 0)
        {
            //Reminder has been displayed at least once.

            //Allows true to be returned from the repeat part only if repeat is active for the
            // Reminder.
            if(reminderToCheck.getRepeatData().getActive())
            {
                //Repeat is active for the Reminder.

                //Different modes require different procedures.
                if(reminderToCheck.getRepeatData().getMode() == 0)
                {
                    //Reminder has repeat mode 0 (periodical).

                    if(reminderRepeatTimerHasExpired(reminderToCheck, currentDT))
                    {
                        DatabaseQuery DQ = new DatabaseQuery(ctx);

                        //Updates various variables for the reminder that just activated.
                        reminderToCheck.getInitialActivation().setAmountOfTimesDisplayed(reminderToCheck.getInitialActivation().getAmountOfTimesDisplayed()+1);
                        reminderToCheck.getLatestActivation().setActivationMoment(getLatestActivationMoment(reminderToCheck, currentDT));
                        if(
                                reminderToCheck.getType() == GV.TYPE_MEDICINE &&
                                        reminderToCheck.getRepeatData().getActive()
                                )
                        {
                            reminderToCheck.getInitialActivation().setLocation(reminderToCheck.getInitialActivation().getLocation()+1);
                        }
                        reminderToCheck.setAmountOfTimesDisplayed(reminderToCheck.getAmountOfTimesDisplayed()+1);

                        //Updates the reminder in the database.
                        DQ.updateReminder(reminderToCheck);

                        return true;
                    }
                }
                else if(reminderToCheck.getRepeatData().getMode() == 1)
                {
                    //Reminder has repeat mode 1 (custom).

                    if(checkReminderCustomList(reminderToCheck, currentDT))
                    {
                        return true;
                    }
                }
                else
                {
                    //Reminder has a different mode somehow (this part should never run normally).

                    Log.v("checkSpecificReminder() error", "mode isn't 0 or 1, but "+reminderToCheck.getRepeatData().getMode());

                    return false;
                }
            }

            //If the check above for either mode 0 or 1 fail, one last check needs to be done with
            // snooze before false can be returned.
            if(reminderToCheck.getInitialActivation().getSnoozeActivityState() &&
                    hasDateArrived(
                            new DateTime(
                                    reminderToCheck.getInitialActivation().getSnoozeActivationMoment().getYear(),
                                    reminderToCheck.getInitialActivation().getSnoozeActivationMoment().getMonth(),
                                    reminderToCheck.getInitialActivation().getSnoozeActivationMoment().getDay(),
                                    reminderToCheck.getInitialActivation().getSnoozeActivationMoment().getHour(),
                                    reminderToCheck.getInitialActivation().getSnoozeActivationMoment().getMinute()
                            ),
                            currentDT
                    )
                    )
            {
                //Snooze is active and the time has run out.

                DatabaseQuery DQ = new DatabaseQuery(ctx);

                reminderToCheck.getInitialActivation().setSnoozeActivityState(false);

                DQ.updateReminder(reminderToCheck);

                DQ.closeDatabaseHelper();

                return true;
            }

            return false;
        }
        else
        {
            //Reminder somehow has been displayed a negative amount of times
            // (this part should never run normally).

            Log.v("checkSpecificReminder() error", "amountOfTimesDisplayed isn't 0 or above, but "+reminderToCheck.getAmountOfTimesDisplayed());

            return false;
        }
    }

    //Loop through the custom repeat list of one Reminder and return true if one or more needs to
    // be displayed.
    private boolean checkReminderCustomList(Reminder reminderToCheck, DateTime currentDT)
    {
        for(int index = 0; index < reminderToCheck.getRepeatData().getCustomActivationDataList().size(); index++)
        {
            //Create DateTime with data from the current object in customActivationDataArrayList.
            DateTime reminderActivationDateTime = new DateTime(
                    reminderToCheck.getRepeatData().getCustomActivationDataList().get(index).getActivationMoment().getYear(),
                    reminderToCheck.getRepeatData().getCustomActivationDataList().get(index).getActivationMoment().getMonth(),
                    reminderToCheck.getRepeatData().getCustomActivationDataList().get(index).getActivationMoment().getDay(),
                    reminderToCheck.getRepeatData().getCustomActivationDataList().get(index).getActivationMoment().getHour(),
                    reminderToCheck.getRepeatData().getCustomActivationDataList().get(index).getActivationMoment().getMinute()
            );

            //Same as for the initial date above.
            if(hasDateArrived(reminderActivationDateTime, currentDT) &&
                    reminderToCheck.getRepeatData().getCustomActivationDataList().get(index).getAmountOfTimesDisplayed() == 0)
            {
                DatabaseQuery DQ = new DatabaseQuery(ctx);

                //Adds +1 to amountOfTimesDisplayed for the current index.
                reminderToCheck.getRepeatData().getCustomActivationDataList().get(index).setAmountOfTimesDisplayed(
                        reminderToCheck.getRepeatData().getCustomActivationDataList().get(index).getAmountOfTimesDisplayed()+1
                );

                //Update latestActivation with data from the current index in customActivationDataList.
                reminderToCheck.setLatestActivation(
                        reminderToCheck.getRepeatData().getCustomActivationDataList().get(index)
                );

                //Adds +1 to amountOfTimesDisplayed for the Reminder.
                reminderToCheck.setAmountOfTimesDisplayed(
                        reminderToCheck.getAmountOfTimesDisplayed()+1
                );

                //Updates the Reminder with new values.
                DQ.updateReminder(reminderToCheck);

                return true;
            }
        }

        return false;
    }

    //Checks if the waiting period for the next repeat of a given reminder has passed.
    //Returns true if this is the case.
    private boolean reminderRepeatTimerHasExpired(Reminder reminderToCheck, DateTime currentDT)
    {
        Log.v(
                "test1",
                reminderToCheck.getLatestActivation().getActivationMoment().getYear()+" "+
                        reminderToCheck.getLatestActivation().getActivationMoment().getMonth()+" "+
                        reminderToCheck.getLatestActivation().getActivationMoment().getDay()+" "+
                        reminderToCheck.getLatestActivation().getActivationMoment().getHour()+" "+
                        reminderToCheck.getLatestActivation().getActivationMoment().getMinute()
        );

        //Runs only if the Reminder has been displayed already.
        if(
                reminderToCheck.getAmountOfTimesDisplayed() != 0 &&
                        reminderToCheck.getLatestActivation().getActivationMoment().getYear() != 8999
        )
        {

            Log.v(
                    "test2",
                    reminderToCheck.getLatestActivation().getActivationMoment().getYear()+" "+
                            reminderToCheck.getLatestActivation().getActivationMoment().getMonth()+" "+
                            reminderToCheck.getLatestActivation().getActivationMoment().getDay()+" "+
                            reminderToCheck.getLatestActivation().getActivationMoment().getHour()+" "+
                            reminderToCheck.getLatestActivation().getActivationMoment().getMinute()
            );

            //Creates DateTime with data from reminderToCheck using data about when it was last shown.
            //This isn't accurate to its name just yet and is modified below.
            DateTime whenToRepeatAgainDT = new DateTime(
                    reminderToCheck.getLatestActivation().getActivationMoment().getYear(),
                    reminderToCheck.getLatestActivation().getActivationMoment().getMonth(),
                    reminderToCheck.getLatestActivation().getActivationMoment().getDay(),
                    reminderToCheck.getLatestActivation().getActivationMoment().getHour(),
                    reminderToCheck.getLatestActivation().getActivationMoment().getMinute()
            );

            //Adds a certain amount of time to whenToRunAgainDT depending on what the user gave while creating the Notification.
            //This is the correct, final value that is used for comparison in hasDateArrived().
            if(reminderToCheck.getRepeatData().getPeriodicalSpinnerPosition() == 0)
            {
                whenToRepeatAgainDT = whenToRepeatAgainDT.plusMinutes(reminderToCheck.getRepeatData().getPeriodicalFrequency());
            }
            if(reminderToCheck.getRepeatData().getPeriodicalSpinnerPosition() == 1)
            {
                whenToRepeatAgainDT = whenToRepeatAgainDT.plusHours(reminderToCheck.getRepeatData().getPeriodicalFrequency());
            }
            if(reminderToCheck.getRepeatData().getPeriodicalSpinnerPosition() == 2)
            {
                whenToRepeatAgainDT = whenToRepeatAgainDT.plusDays(reminderToCheck.getRepeatData().getPeriodicalFrequency());
            }
            if(reminderToCheck.getRepeatData().getPeriodicalSpinnerPosition() == 3)
            {
                whenToRepeatAgainDT = whenToRepeatAgainDT.plusWeeks(reminderToCheck.getRepeatData().getPeriodicalFrequency());
            }
            if(reminderToCheck.getRepeatData().getPeriodicalSpinnerPosition() == 4)
            {
                whenToRepeatAgainDT = whenToRepeatAgainDT.plusMonths(reminderToCheck.getRepeatData().getPeriodicalFrequency());
            }
            if(reminderToCheck.getRepeatData().getPeriodicalSpinnerPosition() == 5)
            {
                whenToRepeatAgainDT = whenToRepeatAgainDT.plusYears(reminderToCheck.getRepeatData().getPeriodicalFrequency());
            }

            Log.v(
                    "run again",
                    whenToRepeatAgainDT.year().get()+" "+
                            whenToRepeatAgainDT.monthOfYear().get()+" "+
                            whenToRepeatAgainDT.dayOfMonth().get()+" "+
                            whenToRepeatAgainDT.hourOfDay().get()+" "+
                            whenToRepeatAgainDT.minuteOfHour().get()
            );

            Log.v(
                    "frequency",
                    reminderToCheck.getRepeatData().getPeriodicalFrequency()+" "+
                            reminderToCheck.getRepeatData().getPeriodicalSpinnerPosition()
            );

            //This DateTime contains information about when to stop repeating the reminder.
            DateTime whenToStopRepeatingDT = new DateTime(
                    reminderToCheck.getRepeatData().getPeriodicalActiveUntil().getActivationMoment().getYear(),
                    reminderToCheck.getRepeatData().getPeriodicalActiveUntil().getActivationMoment().getMonth(),
                    reminderToCheck.getRepeatData().getPeriodicalActiveUntil().getActivationMoment().getDay(),
                    reminderToCheck.getRepeatData().getPeriodicalActiveUntil().getActivationMoment().getHour(),
                    reminderToCheck.getRepeatData().getPeriodicalActiveUntil().getActivationMoment().getMinute()
            );

            //Returns true if the DateTime for repeating the reminder again has arrived AND
            // if the DateTime to stop repeating the reminder hasn't arrived.
            return hasDateArrived(whenToRepeatAgainDT, currentDT) && !hasDateArrived(whenToStopRepeatingDT, currentDT);
        }

        return true;
    }

    //Get the latest Moment when a Reminder with periodical repeat is supposed to be activated.
    //Used to keep for example a daily Reminder always activating at a certain time.
    private Moment getLatestActivationMoment(Reminder reminderToCheck, DateTime currentDT)
    {
        DateTime temporaryTestLatestDT;
        DateTime latestDT;

        //Runs only if the Reminder hasn't been displayed already.
        if(
                reminderToCheck.getAmountOfTimesDisplayed() == 0 &&
                        reminderToCheck.getLatestActivation().getActivationMoment().getYear() == 8999
                )
        {
            temporaryTestLatestDT = new DateTime(
                    reminderToCheck.getInitialActivation().getActivationMoment().getYear(),
                    reminderToCheck.getInitialActivation().getActivationMoment().getMonth(),
                    reminderToCheck.getInitialActivation().getActivationMoment().getDay(),
                    reminderToCheck.getInitialActivation().getActivationMoment().getHour(),
                    reminderToCheck.getInitialActivation().getActivationMoment().getMinute()
            );
        }
        else
        {
            temporaryTestLatestDT = new DateTime(
                    reminderToCheck.getLatestActivation().getActivationMoment().getYear(),
                    reminderToCheck.getLatestActivation().getActivationMoment().getMonth(),
                    reminderToCheck.getLatestActivation().getActivationMoment().getDay(),
                    reminderToCheck.getLatestActivation().getActivationMoment().getHour(),
                    reminderToCheck.getLatestActivation().getActivationMoment().getMinute()
            );
        }

        latestDT = temporaryTestLatestDT;

        //This loops for as long as newLatestDT isn't in the future and keeps adding the repeat
        // period and updating it until it is.
        while(hasDateArrived(temporaryTestLatestDT, currentDT))
        {
            Moment MM = new Moment(
                    latestDT.getDayOfMonth(),
                    latestDT.getMonthOfYear(),
                    latestDT.getYear(),
                    latestDT.getHourOfDay(),
                    latestDT.getMinuteOfHour()
            );

            Log.v("ActivationManagement getLatestActivationMoment()", "Ran for "+MM.getClassData());

            latestDT = temporaryTestLatestDT;

            if(reminderToCheck.getRepeatData().getPeriodicalSpinnerPosition() == 0)
            {
                temporaryTestLatestDT = temporaryTestLatestDT.plusMinutes(reminderToCheck.getRepeatData().getPeriodicalFrequency());
            }
            if(reminderToCheck.getRepeatData().getPeriodicalSpinnerPosition() == 1)
            {
                temporaryTestLatestDT = temporaryTestLatestDT.plusHours(reminderToCheck.getRepeatData().getPeriodicalFrequency());
            }
            if(reminderToCheck.getRepeatData().getPeriodicalSpinnerPosition() == 2)
            {
                temporaryTestLatestDT = temporaryTestLatestDT.plusDays(reminderToCheck.getRepeatData().getPeriodicalFrequency());
            }
            if(reminderToCheck.getRepeatData().getPeriodicalSpinnerPosition() == 3)
            {
                temporaryTestLatestDT = temporaryTestLatestDT.plusWeeks(reminderToCheck.getRepeatData().getPeriodicalFrequency());
            }
            if(reminderToCheck.getRepeatData().getPeriodicalSpinnerPosition() == 4)
            {
                temporaryTestLatestDT = temporaryTestLatestDT.plusMonths(reminderToCheck.getRepeatData().getPeriodicalFrequency());
            }
            if(reminderToCheck.getRepeatData().getPeriodicalSpinnerPosition() == 5)
            {
                temporaryTestLatestDT = temporaryTestLatestDT.plusYears(reminderToCheck.getRepeatData().getPeriodicalFrequency());
            }
        }

        Moment M = new Moment(
                latestDT.getDayOfMonth(),
                latestDT.getMonthOfYear(),
                latestDT.getYear(),
                latestDT.getHourOfDay(),
                latestDT.getMinuteOfHour()
        );

        Log.v("ActivationManagement getLatestActivationMoment()", M.getClassData());

        return M;
    }

    //Check if a date has arrived compared to another (current date used in program).
    private boolean hasDateArrived(DateTime comparisonDT, DateTime currentDT)
    {
        Log.v("hasDateArrived() DTs (comparison first, true for < current)", "year: "+comparisonDT.year().get()+" vs "+currentDT.year().get());
        Log.v("hasDateArrived() DTs (comparison first, true for < current)", "month: "+comparisonDT.monthOfYear().get()+" vs "+currentDT.monthOfYear().get());
        Log.v("hasDateArrived() DTs (comparison first, true for < current)", "day: "+comparisonDT.dayOfMonth().get()+" vs "+currentDT.dayOfMonth().get());
        Log.v("hasDateArrived() DTs (comparison first, true for < current)", "hour: "+comparisonDT.hourOfDay().get()+" vs "+currentDT.hourOfDay().get());
        Log.v("hasDateArrived() DTs (comparison first, true for < current)", "minute: "+comparisonDT.minuteOfHour().get()+" vs "+currentDT.minuteOfHour().get());

        //A series of if/else statements starting from year, moving all the way to minute while checking if
        //the current date/time has passed the activation date/time.
        if(comparisonDT.year().get() < currentDT.year().get())
        {
            Log.v("MTCYear", "first, true");
            return true;
        }
        else if(comparisonDT.year().get() == currentDT.year().get())
        {
            Log.v("MTCYear", "second, neutral");
            if(comparisonDT.monthOfYear().get() < currentDT.monthOfYear().get())
            {
                Log.v("MTCmonth", "first, true");
                return true;
            }
            else if(comparisonDT.monthOfYear().get() == currentDT.monthOfYear().get())
            {
                Log.v("MTCmonth", "second, neutral");
                if(comparisonDT.dayOfMonth().get() < currentDT.dayOfMonth().get())
                {
                    Log.v("MTCday", "first, true");
                    return true;
                }
                else if(comparisonDT.dayOfMonth().get() == currentDT.dayOfMonth().get())
                {
                    Log.v("MTCday", "second, neutral");
                    if(comparisonDT.hourOfDay().get() < currentDT.hourOfDay().get())
                    {
                        Log.v("MTChour", "first, true");
                        return true;
                    }
                    else if(comparisonDT.hourOfDay().get() == currentDT.hourOfDay().get())
                    {
                        Log.v("MTChour", "second, neutral");
                        if(comparisonDT.minuteOfHour().get() <= currentDT.minuteOfHour().get())
                        {
                            Log.v("MTCminute", "first, true");
                            return true;
                        }
                        else
                        {
                            Log.v("MTCminute", "second, false");
                            return false;
                        }
                    }
                    else
                    {
                        Log.v("MTChour", "third, false");
                        return false;
                    }
                }
                else
                {
                    Log.v("MTCday", "third, false");
                    return false;
                }
            }
            else
            {
                Log.v("MTCmonth", "third, false");
                return false;
            }
        }
        else
        {
            Log.v("MTCYear", "third, false");
            return false;
        }
    }

    //Call this method to get a snooze activation point as a list by giving in minutes of snooze.
    public Moment getSnooze(int snoozeTimeInMinutes)
    {
        //An instance of the current time is created here, since the user might not press snooze right away, making usage of the original date and time for alarming incorrect.
        DateTime currentDT = new DateTime(DateTimeZone.getDefault());

        //New DateTime after snooze has been added.
        currentDT = currentDT.plusMinutes(snoozeTimeInMinutes);

        return new Moment(
                currentDT.getDayOfMonth(),
                currentDT.getMonthOfYear(),
                currentDT.getYear(),
                currentDT.getHourOfDay(),
                currentDT.getMinuteOfHour()
        );
    }
}