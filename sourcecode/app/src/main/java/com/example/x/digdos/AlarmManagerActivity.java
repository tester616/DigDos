package com.example.x.digdos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//This class is accessed whenever a check is made to the database.
//The visual activity is shown to the user only if the check returns 1 or more reminders to show.
//If nothing is returned it finishes automatically like nothing ever happened.
public class AlarmManagerActivity extends Activity
{
    private Language lang;
    private PowerManager.WakeLock PMWakeLock;
    private ArrayList<Reminder> remindersWaitingToBeShown = new ArrayList<Reminder>();
    private MediaPlayer mPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Takes away the name of the class from the UI.
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        PMWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My wake lock");
        PMWakeLock.acquire();

        //For unlocking the screen as well on alert. Likely only works without pin.
        /*Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        this.finish();
        return;*/

        //Check if anything needs to be shown.
        //Prepare Activity for opening if yes, finish Activity otherwise.
        checkDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setLanguage()
    {
        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Create object of Language depending on what prefs has saved as language.
        lang = new Language(prefs.getInt("language", GV.LANGUAGE_ENGLISH));
    }

    //Replaces text in various fields (like TextViews and Buttons) with the current language counterparts.
    private void createFieldsWithCurrentLanguage()
    {
        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Variable initialization.
        Button snoozeButton = (Button) findViewById(R.id.snoozeButton);

        //Update text.
        snoozeButton.setText("+"+prefs.getInt("snoozeTime", 10)+" MIN.");
    }

    //Runs a check on the current database and searches for rows of Medicine or Notification.
    //Ones that meet the requirements for viewing are added to lists and shown later.
    private void checkDatabase()
    {
        //Create new object of ActivationManagement..
        ActivationManagement AM = new ActivationManagement(getApplicationContext());

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Only runs code responsible of adding reminders of medicine type to remindersWaitingToBeShown if showMedicines is true (1).
        if(prefs.getBoolean("showMedicines", true))
        {
            //Loops through each position in a list of reminders with medicine as type that need to be displayed and adds them to medicinesWaitingToBeShown.
            for(Reminder R : AM.getRemindersToDisplay(GV.TYPE_MEDICINE))
            {
                remindersWaitingToBeShown.add(R);
            }
        }

        //Only runs code responsible of adding reminders of notification type to remindersWaitingToBeShown if showNotifications is true (1).
        if(prefs.getBoolean("showNotifications", true))
        {
            //Loops through each position in a list of reminders with notification as type that need to be displayed and adds them to notificationsWaitingToBeShown.
            for(Reminder R : AM.getRemindersToDisplay(GV.TYPE_NOTIFICATION))
            {
                remindersWaitingToBeShown.add(R);
            }
        }

        //Initialize view if remindersWaitingToBeShown isn't empty, otherwise call finish().
        if(!remindersWaitingToBeShown.isEmpty())
        {
            Log.v("something to show", "opening...");

            //Prepares the Activity to show visual data.
            initializeView();

            //Set language.
            setLanguage();

            //Update various fields with current language.
            createFieldsWithCurrentLanguage();

            //Shows the first reminder in remindersWaitingToBeShown or calls finish() if there are none left (can't happen here since this code wouldn't reach if it was empty already).
            showNext();
        }
        else
        {
            //Finish activity.
            finish();
        }
    }

    //This is called when the previous code has decided there's something visual that needs to be shown.
    private void initializeView()
    {
        //Creates the visible content in the Activity.
        setContentView(R.layout.activity_alarm_manager);
    }

    //Shows the first Reminder in remindersWaitingToBeShown and removes it or calls finish() if there are none left.
    private void showNext()
    {
        //Checks if there are any medicines or notifications waiting to be shown.
        if(!remindersWaitingToBeShown.isEmpty())
        {
            showReminderWrittenInformation(
                    remindersWaitingToBeShown.get(0).getType(),
                    remindersWaitingToBeShown.get(0).getName(),
                    remindersWaitingToBeShown.get(0).getNote()
            );

            //Set attachedImageImageView image if image is true for that medicine.
            if(remindersWaitingToBeShown.get(0).getImageData().getActive())
            {
                //Give full path to an image and this method will show it in the ImageView.
                showReminderImage(Environment.getExternalStorageDirectory()+"/"+GV.PROGRAM_NAME+"/"+GV.IMAGE_FOLDER_NAME+"/"+remindersWaitingToBeShown.get(0).getImageData().getName());
            }
            else
            {
                emptyImageView();
            }

            //Plays sound if sound is true for that medicine.
            if(remindersWaitingToBeShown.get(0).getSoundData().getActive())
            {
                playReminderSound(
                        Environment.getExternalStorageDirectory() + "/" + GV.PROGRAM_NAME + "/" + GV.SOUND_FOLDER_NAME + "/" + remindersWaitingToBeShown.get(0).getSoundData().getName(),
                        remindersWaitingToBeShown.get(0).getSoundData().getVolume()
                );
            }
        }
        else
        {
            Log.v("finishing", "cool");

            //Various resets/releases to do at end of the Activity.
            stopListening();
            releaseWakeLock();

            //Finish activity.
            finish();
        }
    }

    //Update the three textviews.
    private void showReminderWrittenInformation(int type, String name, String note)
    {
        TextView alertTextView = (TextView) findViewById(R.id.alertTextView);
        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        TextView noteTextView = (TextView) findViewById(R.id.noteTextView);

        if(type == GV.TYPE_MEDICINE)
        {
            alertTextView.setText(lang.ALERT_MEDICINE_TEXT_VIEW);
        }
        else if(type == GV.TYPE_NOTIFICATION)
        {
            alertTextView.setText(lang.ALERT_NOTIFICATION_TEXT_VIEW);
        }
        else
        {
            alertTextView.setText("");
        }
        nameTextView.setText(name);
        noteTextView.setText(note);
    }

    //Take the path to an image and display it jn the ImageView.
    private void showReminderImage(String imagePath)
    {
        ImageView attachedImageImageView = (ImageView) findViewById(R.id.attachedImageImageView);
        Support S = new Support(getApplicationContext());

        //If width or height of attachedImageImageView hasn't been measured yet, it will be done here.
        if(attachedImageImageView.getWidth() == 0 || attachedImageImageView.getHeight() == 0)
        {
            attachedImageImageView.setImageBitmap(
                    S.getScaledBitmap(
                            imagePath,
                            345,
                            255,
                            S.getCorrectedBitmapRotationMatrix(imagePath)
                    )
            );
        }
        else
        {
            attachedImageImageView.setImageBitmap(
                    S.getScaledBitmap(
                            imagePath,
                            attachedImageImageView.getWidth(),
                            attachedImageImageView.getHeight(),
                            S.getCorrectedBitmapRotationMatrix(imagePath)
                    )
            );
        }
    }

    //Empty the ImageView.
    private void emptyImageView()
    {
        ImageView attachedImageImageView = (ImageView) findViewById(R.id.attachedImageImageView);

        attachedImageImageView.setImageDrawable(null);
    }

    //Play sound from a given filename (within the programs own folder) with a given volume.
    private void playReminderSound(String soundName, int volume)
    {
        //Create AudioManager.
        AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        //Set volume.
        manager.setStreamVolume(AudioManager.STREAM_MUSIC, remindersWaitingToBeShown.get(0).getSoundData().getVolume(), 0);

        //Play either the default sound or one of the user given ones.
        if(soundName.contains(GV.PROGRAM_NAME + "/" + GV.SOUND_FOLDER_NAME + "/Default"))
        {
            //Creates MediaPlayer with the default alert sound.
            mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alert);
        }
        else
        {
            //The sound filename might not exist anymore, so error handling is needed.
            try
            {
                //Uri for sound path.
                Uri soundUri = Uri.parse(soundName);

                //Create MediaPlayer with one of the user given sounds.
                mPlayer = MediaPlayer.create(getApplicationContext(), soundUri);
            }
            catch(Exception e)
            {
                Log.v("Error in playReminderSound()", "Couldn't create MediaPlayer, likely due to a filename that doesn't exist.");

                stopListening();
            }
        }

        if(mPlayer != null)
        {
            //Play sound.
            mPlayer.start();
        }
    }

    //Stop and release MediaPlayer.
    private void stopListening()
    {
        //Reset mPlayer if it isn't null.
        if(mPlayer != null)
        {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    //Release WakeLock.
    private void releaseWakeLock()
    {
        //Reset PMWakeLock if it isn't null.
        if(PMWakeLock != null)
        {
            try
            {
                PMWakeLock.release();
            }
            catch (Throwable th)
            {
                //Ignoring this exception, PMWakeLock was probably already released.
                Log.v("Error in releaseWakeLock", "Couldn't release properly, was probably already released.");
            }
        }
    }

    //On click methods below.
    //----------------------------------------------------------------------------------------------
    //For confirmButton.
    public void confirmButton(View view)
    {
        //Removes the first Medicine in medicinesWaitingToBeShown.
        remindersWaitingToBeShown.remove(0);

        //Show next or finish Activity if there is nothing to show.
        showNext();
    }

    //For remindLaterButton.
    public void remindLaterButton(View view)
    {
        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Create new object of ActivationManagement..
        ActivationManagement AM = new ActivationManagement(getApplicationContext());

        DatabaseQuery DQ = new DatabaseQuery(getApplicationContext());

        //Activates snooze.
        remindersWaitingToBeShown.get(0).getInitialActivation().setSnoozeActivityState(true);

        //Add snooze to the reminder (for now always in initialActivation.
        remindersWaitingToBeShown.get(0).getInitialActivation().setSnoozeActivationMoment(
                AM.getSnooze(prefs.getInt("snoozeTime", 10))
        );

        //Tries to execute updateReminder().
        try
        {
            //Launches updateReminder().
            DQ.updateReminder(remindersWaitingToBeShown.get(0));
        }
        catch(Exception e)    //Catch possible errors.
        {
            //Display message.
            Log.v("Reminder editing error", e.getMessage());
        }

        //Closes the database connection.
        DQ.closeDatabaseHelper();

        //Removes the first Medicine in medicinesWaitingToBeShown.
        remindersWaitingToBeShown.remove(0);

        //Show next or finish Activity if there is nothing to show.
        showNext();
    }
    //----------------------------------------------------------------------------------------------
}
