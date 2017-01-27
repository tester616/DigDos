package com.example.x.digdos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;


public class MainActivity extends Activity
{
    private Language lang;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Removes the title of the Activity.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Defines which xml file is shown in this Activity.
        setContentView(R.layout.activity_main);

        //Only used to delete current tables from the database and recreating them empty.
        //recreateDatabase();

        //Only used to delete current SharedPreferences and starting empty again.
        //recreateSharedPreferences();

        //Performs a check to see if all SharedPreferences values the program uses are created.
        //If not, creates them with default values.
        checkSharedPreferences();

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();

        //Determine which fields are usable and which aren't.
        setFieldAccess();

        //Updates the repeating alarm, either activating it or deactivating it depending on settings.
        updateRepeatingAlarmState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected void onStart()
    {
        super.onStart();

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();

        //Determine which fields are usable and which aren't.
        setFieldAccess();
    }

    //Creates object of Language with values for the currently selected language.
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
        Button newUserButton = (Button) findViewById(R.id.newUserButton);
        Button deleteUserButton = (Button) findViewById(R.id.deleteUserButton);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button settingsButton = (Button) findViewById(R.id.settingsButton);
        Button exitButton = (Button) findViewById(R.id.exitButton);

        //Update text.
        newUserButton.setText(lang.NEW_USER_BUTTON);
        deleteUserButton.setText(lang.DELETE_USER_BUTTON);
        loginButton.setText(lang.LOGIN_BUTTON);
        settingsButton.setText(lang.SETTINGS_BUTTON);
        exitButton.setText(lang.EXIT_BUTTON);
    }

    //Determine which fields are usable and which aren't.
    private void setFieldAccess()
    {
        Button newUserButton = (Button) findViewById(R.id.newUserButton);

        DatabaseQuery DQ = new DatabaseQuery(getApplicationContext());

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Locks newUserButton only if lockAccountCreation is true AND user table has content.
        if(prefs.getBoolean("lockAccountCreation", false) && DQ.getTableAmountOfRows(DatabaseHelper.TABLE_USER) != 0)
        {
            newUserButton.setEnabled(false);
        }
        else
        {
            newUserButton.setEnabled(true);
        }
    }

    //Run this if there isn't a database or if it's out of date.
    private void recreateDatabase()
    {
        DatabaseQuery DQ = new DatabaseQuery(getApplicationContext());
        DQ.upgradeDatabase();
    }

    //Only used to delete current SharedPreferences and starting empty again.
    private void recreateSharedPreferences()
    {
        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Opens editor for prefs.
        SharedPreferences.Editor editor = prefs.edit();

        //Clear all data from SharedPreferences.
        editor.clear();

        //Commit the changes.
        editor.commit();
    }

    private void checkSharedPreferences()
    {
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();

        if(!prefs.contains("showMedicines"))
        {
            editor.putBoolean("showMedicines", true);
        }

        //Perform the checks here.
        //Runs only if the key checkForReminders isn't found in SharedPreferences.
        if(!prefs.contains("checkForReminders"))
        {
            //Create key checkForReminders with value 1.
            editor.putBoolean("checkForReminders", false);
        }

        //Run this version as long as testing and checking for alerts isn't reasonable to keep on at start.
        //editor.putBoolean("checkForReminders", false);

        if(!prefs.contains("showNotifications"))
        {
            editor.putBoolean("showNotifications", true);
        }

        if(!prefs.contains("allowAlertChangesInSettings"))
        {
            editor.putBoolean("allowAlertChangesInSettings", false);
        }

        if(!prefs.contains("lockAccountCreation"))
        {
            editor.putBoolean("lockAccountCreation", false);
        }

        if(!prefs.contains("question"))
        {
            editor.putString("question", "");
        }

        if(!prefs.contains("hint"))
        {
            editor.putString("hint", "");
        }

        if(!prefs.contains("answer"))
        {
            editor.putString("answer", "");
        }

        if(!prefs.contains("repeatAnswer"))
        {
            editor.putString("repeatAnswer", "");
        }

        if(!prefs.contains("useSafetyQuestion"))
        {
            editor.putBoolean("useSafetyQuestion", false);
        }

        if(!prefs.contains("language"))
        {
            editor.putInt("language", GV.LANGUAGE_ENGLISH);
        }

        if(!prefs.contains("typeMedicine"))
        {
            editor.putBoolean("typeMedicine", true);
        }

        if(!prefs.contains("typeNotification"))
        {
            editor.putBoolean("typeNotification", false);
        }

        if(!prefs.contains("modeNew"))
        {
            editor.putBoolean("modeNew", true);
        }

        if(!prefs.contains("modeEdit"))
        {
            editor.putBoolean("modeEdit", false);
        }

        if(!prefs.contains("snoozeTime"))
        {
            editor.putInt("snoozeTime", 10);
        }

        editor.commit();
    }

    private void updateRepeatingAlarmState()
    {
        AlarmManagerControls AMC = new AlarmManagerControls(getApplicationContext());

        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        AMC.setRepeatingAlarmState(prefs.getBoolean("checkForReminders", false));
    }

    //On click methods below.
    //----------------------------------------------------------------------------------------------
    //For newUserButton.
    public void newUserButton(View view)
    {
        //New activities are started by creating an Intent of the class and running startActivity on it.
        Intent newUserIntent = new Intent(this, NewUser.class);
        startActivity(newUserIntent);
    }

    //For deleteUserButton.
    public void deleteUserButton(View view)
    {
        //Start another activity.
        Intent deleteUserIntent = new Intent(this, DeleteUser.class);
        startActivity(deleteUserIntent);
    }

    //For loginButton.
    public void loginButton(View view)
    {
        //Start another activity.
        Intent loginIntent = new Intent(this, Login.class);
        startActivity(loginIntent);
    }

    //For settingsButton.
    public void settingsButton(View view)
    {
        //Start another activity.
        Intent settingsIntent = new Intent(this, PublicSettings.class);
        startActivity(settingsIntent);
    }

    //For exitButton.
    public void exitButton(View view)
    {
        //Exit program.
        finish();
    }
    //----------------------------------------------------------------------------------------------
}









/*
Some general information about what different variables mean when used in the SQL methods.
different queries returning cursors
public Cursor query (String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
table 	The table name to compile the query against.
columns 	A list of which columns to return. Passing null will return all columns, which is discouraged to prevent reading data from storage that isn't going to be used.
selection 	A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
selectionArgs 	You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
groupBy 	A filter declaring how to group rows, formatted as an SQL GROUP BY clause (excluding the GROUP BY itself). Passing null will cause the rows to not be grouped.
having 	A filter declare which row groups to include in the cursor, if row grouping is being used, formatted as an SQL HAVING clause (excluding the HAVING itself). Passing null will cause all row groups to be included, and is required when row grouping is not being used.
orderBy 	How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort order, which may be unordered.
limit 	Limits the number of rows returned by the query, formatted as LIMIT clause. Passing null denotes no LIMIT clause.

public Cursor query (boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, CancellationSignal cancellationSignal)
distinct 	true if you want each row to be unique, false otherwise.
cancellationSignal 	A signal to cancel the operation in progress, or null if none. If the operation is canceled, then OperationCanceledException will be thrown when the query is executed.

public Cursor query (String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
*/