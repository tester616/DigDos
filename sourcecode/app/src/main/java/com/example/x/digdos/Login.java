package com.example.x.digdos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
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

//Class for the activity where users can log in.
public class Login extends Activity
{
    private Language lang;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Removes the title of the Activity.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Defines which xml file is shown in this Activity.
        setContentView(R.layout.activity_login);

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
        //Variable initialization.
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        Button logInButton = (Button) findViewById(R.id.logInButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        //Update text.
        usernameEditText.setHint(lang.USERNAME_EDIT_TEXT);
        passwordEditText.setHint(lang.PASSWORD_EDIT_TEXT);
        logInButton.setText(lang.LOG_IN_BUTTON);
        backButton.setText(lang.BACK_BUTTON);
    }

    //Checks if any fields filled by the user contain bad values.
    //Returns true if not, false if yes.
    private boolean checkFields()
    {
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        boolean fieldCheck = true;

        //Object creation of FieldLimitation.
        FieldLimitation FL = new FieldLimitation();

        //Checks on all fields by running the corresponding method in FL.
        if(!FL.checkUsername(usernameEditText.getText().toString()))
        {
            fieldCheck = false;
        }

        if(!FL.checkPassword(passwordEditText.getText().toString()))
        {
            fieldCheck = false;
        }

        return fieldCheck;
    }

    //Button methods below (called on click).
    //----------------------------------------------------------------------------------------------
    //For loginButton.
    public void logInButton(View view)
    {
        //Runs only if checkFields() returns true, meaning no bad values have been given.
        if(checkFields())
        {
            EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
            EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);

            //Initializes cursor.
            Cursor CR;

            DatabaseQuery DQ = new DatabaseQuery(getApplicationContext());

            //Try to log in.
            try
            {
                //Searches for the given username in the database and returns information if found as a Cursor.
                CR = DQ.getPasswordForUsername(usernameEditText.getText().toString());

                //Checks for equal passwords if the returned cursor contains something.
                if(CR.moveToFirst())
                {
                    //Password check happens here. Logs in if they match.
                    if(CR.getString(0).equals(passwordEditText.getText().toString()))
                    {
                        //Closes the database connection.
                        DQ.closeDatabaseHelper();

                        //Display message.
                        Toast.makeText(getBaseContext(), lang.LOGIN_SUCCESSFUL, Toast.LENGTH_LONG).show();

                        //Start another activity.
                        Intent UserMenuIntent = new Intent(this, UserMenu.class);
                        startActivity(UserMenuIntent);
                    }
                    else
                    {
                        //Closes the database connection.
                        DQ.closeDatabaseHelper();

                        //Display message.
                        Toast.makeText(getBaseContext(), lang.LOGIN_FAILED_WRONG_PASSWORD, Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    //Display message.
                    Toast.makeText(getBaseContext(), lang.LOGIN_FAILED_NO_SUCH_USER, Toast.LENGTH_LONG).show();
                }
            }
            catch(Exception e)    //Catch possible errors.
            {
                //Display message.
                Log.v("Login error", "Error returned, something went wrong. " + e.getMessage());
            }
        }
    }

    //For backButton.
    public void backButton(View view)
    {
        //Ends activity and returns to previous.
        finish();
    }
    //----------------------------------------------------------------------------------------------
}
