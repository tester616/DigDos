package com.example.x.digdos;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class DeleteAllUsers extends Activity implements AlertDialogFragment.AlertDialogListener
{
    private Language lang;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Removes the title of the Activity.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Defines which xml file is shown in this Activity and shows it.
        setContentView(R.layout.activity_delete_all_users);

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();

        //Bring values from SharedPreferences and use them in appropriate places.
        setFieldValues();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete_all_users, menu);
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

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick()
    {
        DatabaseQuery DQ = new DatabaseQuery(getApplicationContext());

        //Delete user.
        DQ.deleteAllUsers();

        //Close the database connection.
        DQ.closeDatabaseHelper();

        //Display message.
        Toast.makeText(getBaseContext(), lang.ALL_USERS_DELETED, Toast.LENGTH_LONG).show();

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
        //Variable initialization.
        TextView questionTextView = (TextView) findViewById(R.id.questionTextView);
        TextView hintTextView = (TextView) findViewById(R.id.hintTextView);
        EditText answerEditText = (EditText) findViewById(R.id.answerEditText);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        //Update text.
        questionTextView.setHint(lang.QUESTION_TEXT_VIEW);
        hintTextView.setHint(lang.HINT_TEXT_VIEW);
        answerEditText.setHint(lang.ANSWER_EDIT_TEXT);
        deleteButton.setText(lang.DELETE_BUTTON);
        backButton.setText(lang.BACK_BUTTON);
    }

    //Bring values from SharedPreferences and use them in appropriate places.
    public void setFieldValues()
    {
        //Variable initialization.
        TextView questionTextView = (TextView) findViewById(R.id.questionTextView);
        TextView hintTextView = (TextView) findViewById(R.id.hintTextView);

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Gives values to the safety question fields depending on if there are values to give.
        if(!prefs.getString("question", "").equals(""))
        {
            questionTextView.setText(prefs.getString("question", ""));
        }

        //Gives values to the safety question fields depending on if there are values to give.
        if(!prefs.getString("hint", "").equals(""))
        {
            hintTextView.setText(prefs.getString("hint", ""));
        }
    }

    //On click methods below.
    //----------------------------------------------------------------------------------------------
    //For deleteButton.
    public void deleteButton(View view)
    {
        //Variable initialization/declaration.
        EditText answerEditText = (EditText) findViewById(R.id.answerEditText);

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        if(answerEditText.getText().toString().equals(prefs.getString("answer", "")))
        {
            Bundle args = new Bundle();
            args.putString("text", lang.DELETE_ALL_USERS_DIALOG_ALERT);
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
            Toast.makeText(getBaseContext(), lang.SAFETY_QUESTION_WRONG_ANSWER, Toast.LENGTH_LONG).show();
        }
    }

    //For backButton.
    public void backButton(View view)
    {
        finish();
    }
    //----------------------------------------------------------------------------------------------
}
