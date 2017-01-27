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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

//Class for the activity where users can navigate to medicine/notification creation/editing or lock account creation.
public class UserMenu extends Activity
{
    private Language lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Removes the title of the Activity.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Defines which xml file is shown in this Activity.
        setContentView(R.layout.activity_user_menu);

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_menu, menu);
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
    protected void onStart() {
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
        Button remindersButton = (Button) findViewById(R.id.remindersButton);
        Button userSettingsButton = (Button) findViewById(R.id.userSettingsButton);
        Button logOutButton = (Button) findViewById(R.id.logOutButton);

        //Update text.
        remindersButton.setText(lang.REMINDERS_BUTTON);
        userSettingsButton.setText(lang.USER_SETTINGS_BUTTON);
        logOutButton.setText(lang.LOG_OUT_BUTTON);
    }

    //Button methods below (called on click).
    //----------------------------------------------------------------------------------------------
    //For remindersButton.
    public void remindersButton(View view)
    {
        //New activities are started by creating an Intent of the class and running startActivity on it.
        Intent reminderActivityIntent = new Intent(this, ReminderActivity.class);
        startActivity(reminderActivityIntent);
    }

    //For userSettingsButton.
    public void userSettingsButton(View view)
    {
        //New activities are started by creating an Intent of the class and running startActivity on it.
        Intent UserSettingsIntent = new Intent(this, UserSettings.class);
        startActivity(UserSettingsIntent);
    }

    //For logoutButton.
    public void logOutButton(View view)
    {
        //Ends activity and returns to previous.
        finish();
    }
    //----------------------------------------------------------------------------------------------
}
