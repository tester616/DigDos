package com.example.x.digdos;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
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

//Class for the activity where you can delete a user.
public class DeleteUser extends Activity implements AlertDialogFragment.AlertDialogListener
{
    private Language lang;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Removes the title of the Activity.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Defines which xml file is shown in this Activity and shows it.
        setContentView(R.layout.activity_delete_user);

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();

        //Determine which fields are usable and which aren't.
        setFieldAccess();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete_user, menu);
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

    @Override
    protected void onStart() {
        super.onStart();

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick()
    {
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);

        DatabaseQuery DQ = new DatabaseQuery(getApplicationContext());

        //Delete user.
        DQ.deleteUser(usernameEditText.getText().toString());

        //Close the database connection.
        DQ.closeDatabaseHelper();

        //Display message.
        Toast.makeText(getBaseContext(), lang.USER_DELETED, Toast.LENGTH_LONG).show();

        //Finish activity, returning to previous.
        finish();
    }

    @Override
    public void onDialogNegativeClick()
    {

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
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        Button deleteAllUsersButton = (Button) findViewById(R.id.deleteAllUsersButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        //Update text.
        usernameEditText.setHint(lang.USERNAME_EDIT_TEXT);
        passwordEditText.setHint(lang.PASSWORD_EDIT_TEXT);
        deleteButton.setText(lang.DELETE_USER_BUTTON);
        deleteAllUsersButton.setText(lang.DELETE_ALL_USERS_BUTTON);
        backButton.setText(lang.BACK_BUTTON);
    }

    //Determine which fields are usable and which aren't.
    private void setFieldAccess()
    {
        Button deleteAllUsersButton = (Button) findViewById(R.id.deleteAllUsersButton);

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Enable or disable deleteAllUsersButton depending on useSafetyQuestion in prefs.
        deleteAllUsersButton.setEnabled(prefs.getBoolean("useSafetyQuestion", false));
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

    //On click methods below.
    //----------------------------------------------------------------------------------------------
    //For deleteButton.
    public void deleteButton(View view)
    {
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        //Runs only if checkFields() returns true, meaning no bad values have been given.
        if(checkFields())
        {
            //Saves the username and password to strings for clarity, which are then sent with the method.
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            //Cursor initialization.
            Cursor CR;

            DatabaseQuery DQ = new DatabaseQuery(getApplicationContext());

            //Try to delete user.
            try
            {
                //Searches for the given username in the database and returns information if found as a Cursor.
                CR = DQ.getPasswordForUsername(username);

                //Checks for equal passwords if the returned cursor contains something.
                if(CR.moveToFirst())
                {
                    //Password check happens here. Continues deleting process if it matches.
                    if(CR.getString(0).equals(password))
                    {
                        Bundle args = new Bundle();
                        args.putString("text", lang.DELETE_USER_DIALOG_ALERT);
                        args.putString("positive", lang.DELETE_USER_DIALOG_POSITIVE);
                        args.putString("negative", lang.DELETE_USER_DIALOG_NEGATIVE);
                        DialogFragment DF = new AlertDialogFragment();
                        FragmentManager FM = getFragmentManager();
                        DF.setArguments(args);
                        DF.show(FM, "deleteConfirmation");
                    }
                    else
                    {
                        //Display message.
                        Toast.makeText(getBaseContext(), lang.DELETE_FAILED_WRONG_PASSWORD, Toast.LENGTH_LONG).show();
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
                Log.v("Delete user error", "Error returned, something went wrong. " + e.getMessage());
            }
        }
    }

    //For deleteAllUsersButton.
    public void deleteAllUsersButton(View view)
    {
        //Start another activity.
        Intent deleteAllUsersIntent = new Intent(this, DeleteAllUsers.class);
        startActivity(deleteAllUsersIntent);
    }

    //For backButton.
    public void backButton(View view)
    {
        finish();
    }
    //----------------------------------------------------------------------------------------------
}
