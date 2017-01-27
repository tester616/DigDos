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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//Class for the activity where you can create a new user.
public class NewUser extends Activity
{
    private Language lang;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Removes the title of the Activity.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Defines which xml file is shown in this Activity.
        setContentView(R.layout.activity_new_user);

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
        getMenuInflater().inflate(R.menu.menu_new_user, menu);
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
        EditText repeatPasswordEditText = (EditText) findViewById(R.id.repeatPasswordEditText);
        Button createButton = (Button) findViewById(R.id.createButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        //Update text.
        usernameEditText.setHint(lang.USERNAME_EDIT_TEXT);
        passwordEditText.setHint(lang.PASSWORD_EDIT_TEXT);
        repeatPasswordEditText.setHint(lang.REPEAT_PASSWORD_TEXT_VIEW);
        createButton.setText(lang.CREATE_BUTTON);
        backButton.setText(lang.BACK_BUTTON);
    }

    //Checks if any fields filled by the user contain bad values.
    //Returns true if not, false if yes.
    private boolean checkFields()
    {
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        EditText repeatPasswordEditText = (EditText) findViewById(R.id.repeatPasswordEditText);

        //Variable initialization.
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

        if(!FL.checkPassword(repeatPasswordEditText.getText().toString()))
        {
            fieldCheck = false;
        }

        return fieldCheck;
    }

    //Button methods below (called on click).
    //----------------------------------------------------------------------------------------------
    //For createButton.
    public void createButton(View view)
    {
        //Runs only if checkFields() returns true, meaning no bad values have been given.
        if(checkFields())
        {
            EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
            EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
            EditText repeatPasswordEditText = (EditText) findViewById(R.id.repeatPasswordEditText);

            //Saves the username and password to strings for clarity, which are then used later in the method.
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String repeatPassword = repeatPasswordEditText.getText().toString();

            //If both passwords match the creation process moves forward, otherwise only a message is shown.
            if(password.equals(repeatPassword))
            {
                DatabaseQuery DQ = new DatabaseQuery(getApplicationContext());

                //Creates the user if no such username exists yet.
                if(!DQ.userExists(username))
                {
                    try
                    {
                        //Launches createUser(String username, String password), sending in the username and password given by the user.
                        DQ.createUser(username, password);

                        //Close the database connection.
                        DQ.closeDatabaseHelper();

                        //Display message.
                        Toast.makeText(getBaseContext(), lang.USER_CREATED, Toast.LENGTH_LONG).show();
                    }
                    catch(Exception e)    //Catch possible errors.
                    {
                        //Display message.
                        Log.v("User creation error", e.getMessage());
                    }

                    //End activity, return to previous.
                    finish();
                }
                else
                {
                    //Display message.
                    Toast.makeText(getBaseContext(), lang.USER_CREATION_USERNAME_ALREADY_EXISTS, Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                //Display message.
                Toast.makeText(getBaseContext(), lang.USER_CREATION_PASSWORDS_DO_NOT_MATCH, Toast.LENGTH_LONG).show();
            }
        }
    }

    //For backButton.
    public void backButton(View view)
    {
        finish();
    }
    //----------------------------------------------------------------------------------------------
}
