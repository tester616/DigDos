package com.example.x.digdos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class PublicSettings extends Activity
{
    private Language lang;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Makes so the keyboard doesn't automatically pop up due to ScrollView.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Removes the title of the Activity.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Defines which xml file is shown in this Activity.
        setContentView(R.layout.activity_public_settings);

        //Update the fields with user given data from SharedPreferences.
        setFieldValues();

        //Update certain fields to be either usable or unusable.
        setFieldAccess();

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_public_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();
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
        //Variable initialization.
        TextView languageTextView = (TextView) findViewById(R.id.languageTextView);
        TextView snoozeTimeTextView = (TextView) findViewById(R.id.snoozeTimeTextView);
        TextView snoozeMinutesTextView = (TextView) findViewById(R.id.snoozeMinutesTextView);
        TextView reminderVisibilityTextView = (TextView) findViewById(R.id.reminderVisibilityTextView);
        CheckBox checkForRemindersCheckBox = (CheckBox) findViewById(R.id.checkForRemindersCheckBox);
        CheckBox showMedicinesCheckBox = (CheckBox) findViewById(R.id.showMedicinesCheckBox);
        CheckBox showNotificationsCheckBox = (CheckBox) findViewById(R.id.showNotificationsCheckBox);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        //Update text.
        languageTextView.setText(lang.LANGUAGE_TEXT_VIEW);
        snoozeTimeTextView.setText(lang.SNOOZE_TIME_TEXT_VIEW);
        snoozeMinutesTextView.setText(lang.SNOOZE_MINUTES_TEXT_VIEW);
        reminderVisibilityTextView.setText(lang.REMINDER_VISIBILITY_TEXT_VIEW);
        checkForRemindersCheckBox.setText(lang.ACTIVATE_REMINDERS_CHECK_BOX);
        showMedicinesCheckBox.setText(lang.SHOW_MEDICINES_CHECK_BOX);
        showNotificationsCheckBox.setText(lang.SHOW_NOTIFICATIONS_CHECK_BOX);
        saveButton.setText(lang.SAVE_BUTTON);
        backButton.setText(lang.BACK_BUTTON);
    }

    //Bring values from SharedPreferences and use them in appropriate places.
    public void setFieldValues()
    {
        RadioButton englishRadioButton = (RadioButton) findViewById(R.id.englishRadioButton);
        RadioButton finnishRadioButton = (RadioButton) findViewById(R.id.finnishRadioButton);
        RadioButton swedishRadioButton = (RadioButton) findViewById(R.id.swedishRadioButton);
        EditText snoozeTimeEditText = (EditText) findViewById(R.id.snoozeTimeEditText);
        CheckBox checkForRemindersCheckBox = (CheckBox) findViewById(R.id.checkForRemindersCheckBox);
        CheckBox showMedicinesCheckBox = (CheckBox) findViewById(R.id.showMedicinesCheckBox);
        CheckBox showNotificationsCheckBox = (CheckBox) findViewById(R.id.showNotificationsCheckBox);

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Check the correct RadioButton for language based on what's saved in prefs.
        if(prefs.getInt("language", GV.LANGUAGE_ENGLISH) == GV.LANGUAGE_ENGLISH)
        {
            englishRadioButton.setChecked(true);
        }
        else if(prefs.getInt("language", GV.LANGUAGE_ENGLISH) == GV.LANGUAGE_FINNISH)
        {
            finnishRadioButton.setChecked(true);
        }
        else if(prefs.getInt("language", GV.LANGUAGE_ENGLISH) == GV.LANGUAGE_SWEDISH)
        {
            swedishRadioButton.setChecked(true);
        }

        //Set value for snoozeTimeEditText.
        snoozeTimeEditText.setText(""+(prefs.getInt("snoozeTime", 10)));

        //Check checkboxes depending on their prefs counterparts.
        checkForRemindersCheckBox.setChecked(prefs.getBoolean("checkForReminders", true));
        showMedicinesCheckBox.setChecked(prefs.getBoolean("showMedicines", true));
        showNotificationsCheckBox.setChecked(prefs.getBoolean("showNotifications", true));
    }

    //Makes certain fields usable or unusable.
    private void setFieldAccess()
    {
        //Variable initialization.
        TextView reminderVisibilityTextView = (TextView) findViewById(R.id.reminderVisibilityTextView);
        CheckBox checkForRemindersCheckBox = (CheckBox) findViewById(R.id.checkForRemindersCheckBox);
        CheckBox showMedicinesCheckBox = (CheckBox) findViewById(R.id.showMedicinesCheckBox);
        CheckBox showNotificationsCheckBox = (CheckBox) findViewById(R.id.showNotificationsCheckBox);

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Sets the CheckBoxes to either be active or not depending on what a user has saved to SharedPreferences.
        if(prefs.getBoolean("allowAlertChangesInSettings", false))
        {
            //Activates.
            reminderVisibilityTextView.setEnabled(true);
            checkForRemindersCheckBox.setEnabled(true);
            showMedicinesCheckBox.setEnabled(true);
            showNotificationsCheckBox.setEnabled(true);
        }
        else
        {
            //Deactivates.
            reminderVisibilityTextView.setEnabled(false);
            checkForRemindersCheckBox.setEnabled(false);
            showMedicinesCheckBox.setEnabled(false);
            showNotificationsCheckBox.setEnabled(false);
        }
    }

    //Update repeating alarm to either active or inactive.
    private void updateRepeatingAlarm()
    {
        //Creates object of AlarmManagerControls.
        AlarmManagerControls AMC = new AlarmManagerControls(getApplicationContext());

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, MODE_PRIVATE);

        //Activates or deactivates the repeating alarm depending on what's saved to SharedPreferences.
        AMC.setRepeatingAlarmState(prefs.getBoolean("checkForReminders", false));
    }

    //On click methods below.
    //--------------------------------------------------------------------------------------------------------------------------------------------------
    //For checkForAlertsCheckBox.
    public void checkForRemindersCheckBox(View view)
    {
        //Nothing needs to be done here for now.
    }

    //For showMedicinesCheckBox.
    public void showMedicinesCheckBox(View view)
    {
        //Nothing needs to be done here for now.
    }

    //For showNotificationsCheckBox.
    public void showNotificationsCheckBox(View view)
    {
        //Nothing needs to be done here for now.
    }

    //For saveButton.
    public void saveButton(View view)
    {
        //Variable initialization.
        RadioButton englishRadioButton = (RadioButton) findViewById(R.id.englishRadioButton);
        RadioButton finnishRadioButton = (RadioButton) findViewById(R.id.finnishRadioButton);
        RadioButton swedishRadioButton = (RadioButton) findViewById(R.id.swedishRadioButton);
        EditText snoozeTimeEditText = (EditText) findViewById(R.id.snoozeTimeEditText);
        CheckBox checkForRemindersCheckBox = (CheckBox) findViewById(R.id.checkForRemindersCheckBox);
        CheckBox showMedicinesCheckBox = (CheckBox) findViewById(R.id.showMedicinesCheckBox);
        CheckBox showNotificationsCheckBox = (CheckBox) findViewById(R.id.showNotificationsCheckBox);

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, MODE_PRIVATE);

        //Opens editor for prefs.
        SharedPreferences.Editor editor = prefs.edit();

        //Save the language choice depending on which RadioButton is checked.
        if(englishRadioButton.isChecked())
        {
            editor.putInt("language", GV.LANGUAGE_ENGLISH);
        }
        else if(finnishRadioButton.isChecked())
        {
            editor.putInt("language", GV.LANGUAGE_FINNISH);
        }
        else if(swedishRadioButton.isChecked())
        {
            editor.putInt("language", GV.LANGUAGE_SWEDISH);
        }
        else
        {
            Log.v("Error in saveButton()", "No RadioButton for language was checked.");
        }

        //Tries to save the user given integer to snoozeTime.
        //If there is an error in the parsing, saves 10 minutes as default value.
        try
        {
            //Save the snooze time to SharedPreferences.
            editor.putInt("snoozeTime", Integer.parseInt(snoozeTimeEditText.getText().toString()));
        }
        catch(Exception e)
        {
            //Save the snooze time to SharedPreferences.
            editor.putInt("snoozeTime", 10);
        }

        //Updates values from checkboxes to prefs counterparts.
        editor.putBoolean("checkForReminders", checkForRemindersCheckBox.isChecked());
        editor.putBoolean("showMedicines", showMedicinesCheckBox.isChecked());
        editor.putBoolean("showNotifications", showNotificationsCheckBox.isChecked());

        //Saving the change.
        editor.commit();

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();

        //Update the fields with user given data from SharedPreferences.
        setFieldValues();

        //Updates repeating alarm status.
        updateRepeatingAlarm();

        //Display message.
        Toast.makeText(getBaseContext(), lang.SETTINGS_SAVED, Toast.LENGTH_LONG).show();
    }

    //For backButton.
    public void backButton(View view)
    {
        //Ends activity and returns to previous.
        finish();
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------
}
