package com.example.x.digdos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class UserSettings extends Activity
{
    private Language lang;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Removes the title of the Activity.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_user_settings);

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();

        //Uses saved values from SharedPreferences to give various fields their values.
        setFieldValues();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_settings, menu);
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
        if (id == R.id.action_settings) {
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
        TextView reminderVisibilityTextView = (TextView) findViewById(R.id.reminderVisibilityTextView);
        CheckBox checkForRemindersCheckBox = (CheckBox) findViewById(R.id.checkForRemindersCheckBox);
        CheckBox showMedicinesCheckBox = (CheckBox) findViewById(R.id.showMedicinesCheckBox);
        CheckBox showNotificationsCheckBox = (CheckBox) findViewById(R.id.showNotificationsCheckBox);
        TextView accessTextView = (TextView) findViewById(R.id.accessTextView);
        CheckBox allowAlertChangesInSettingsCheckBox = (CheckBox) findViewById(R.id.allowAlertChangesInSettingsCheckBox);
        CheckBox lockAccountCreationCheckBox = (CheckBox) findViewById(R.id.lockAccountCreationCheckBox);
        Button safetyQuestionButton = (Button) findViewById(R.id.safetyQuestionButton);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        //Update text.
        reminderVisibilityTextView.setText(lang.REMINDER_VISIBILITY_TEXT_VIEW);
        checkForRemindersCheckBox.setText(lang.ACTIVATE_REMINDERS_CHECK_BOX);
        showMedicinesCheckBox.setText(lang.SHOW_MEDICINES_CHECK_BOX);
        showNotificationsCheckBox.setText(lang.SHOW_NOTIFICATIONS_CHECK_BOX);
        accessTextView.setText(lang.ACCESS_TEXT_VIEW);
        allowAlertChangesInSettingsCheckBox.setText(lang.ALLOW_ALERT_CHANGES_IN_SETTINGS_CHECK_BOX);
        lockAccountCreationCheckBox.setText(lang.LOCK_ACCOUNT_CREATION_CHECK_BOX);
        safetyQuestionButton.setText(lang.SAFETY_QUESTION_BUTTON);
        saveButton.setText(lang.SAVE_BUTTON);
        backButton.setText(lang.BACK_BUTTON);
    }

    //Bring values from SharedPreferences and use them in appropriate places.
    private void setFieldValues()
    {
        CheckBox checkForRemindersCheckBox = (CheckBox) findViewById(R.id.checkForRemindersCheckBox);
        CheckBox showMedicinesCheckBox = (CheckBox) findViewById(R.id.showMedicinesCheckBox);
        CheckBox showNotificationsCheckBox = (CheckBox) findViewById(R.id.showNotificationsCheckBox);
        CheckBox allowAlertChangesInSettingsCheckBox = (CheckBox) findViewById(R.id.allowAlertChangesInSettingsCheckBox);
        CheckBox lockAccountCreationCheckBox = (CheckBox) findViewById(R.id.lockAccountCreationCheckBox);

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Initialize an editor for prefs.
        SharedPreferences.Editor editor = prefs.edit();

        //Updates checked values of the checkboxes this activity uses to match saved settings in prefs.
        checkForRemindersCheckBox.setChecked(prefs.getBoolean("checkForReminders", false));
        showMedicinesCheckBox.setChecked(prefs.getBoolean("showMedicines", true));
        showNotificationsCheckBox.setChecked(prefs.getBoolean("showNotifications", true));
        allowAlertChangesInSettingsCheckBox.setChecked(prefs.getBoolean("allowAlertChangesInSettings", false));
        lockAccountCreationCheckBox.setChecked(prefs.getBoolean("lockAccountCreation", false));

        //Commits the changes.
        editor.commit();
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
    //----------------------------------------------------------------------------------------------
    //For checkForRemindersCheckBox.
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

    //For allowAlertChangesInSettingsCheckBox.
    public void allowAlertChangesInSettingsCheckBox(View view)
    {
        //Nothing needs to be done here for now.
    }

    //For lockAccountCreationCheckBox.
    public void lockAccountCreationCheckBox(View view)
    {
        //Nothing needs to be done here for now.
    }

    //For safetyQuestionButton.
    public void safetyQuestionButton(View view)
    {
        //Start another activity.
        Intent safetyQuestionIntent = new Intent(this, SafetyQuestion.class);
        startActivity(safetyQuestionIntent);
    }

    //For saveButton.
    public void saveButton(View view)
    {
        CheckBox checkForRemindersCheckBox = (CheckBox) findViewById(R.id.checkForRemindersCheckBox);
        CheckBox showMedicinesCheckBox = (CheckBox) findViewById(R.id.showMedicinesCheckBox);
        CheckBox showNotificationsCheckBox = (CheckBox) findViewById(R.id.showNotificationsCheckBox);
        CheckBox allowAlertChangesInSettingsCheckBox = (CheckBox) findViewById(R.id.allowAlertChangesInSettingsCheckBox);
        CheckBox lockAccountCreationCheckBox = (CheckBox) findViewById(R.id.lockAccountCreationCheckBox);

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Opens editor for prefs.
        SharedPreferences.Editor editor = prefs.edit();

        //Saves each of the CheckBox checked states to SharedPreferences.
        editor.putBoolean("checkForReminders", checkForRemindersCheckBox.isChecked());
        editor.putBoolean("showMedicines", showMedicinesCheckBox.isChecked());
        editor.putBoolean("showNotifications", showNotificationsCheckBox.isChecked());
        editor.putBoolean("allowAlertChangesInSettings", allowAlertChangesInSettingsCheckBox.isChecked());
        editor.putBoolean("lockAccountCreation", lockAccountCreationCheckBox.isChecked());

        //Saving the changes.
        editor.commit();

        //Updates repeating alarm status.
        updateRepeatingAlarm();

        //Display message.
        Toast.makeText(getBaseContext(), lang.SETTINGS_SAVED, Toast.LENGTH_LONG).show();
    }

    //For backButton.
    public void backButton(View view)
    {
        finish();
    }
    //----------------------------------------------------------------------------------------------
}
