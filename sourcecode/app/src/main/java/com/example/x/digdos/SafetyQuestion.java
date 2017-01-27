package com.example.x.digdos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class SafetyQuestion extends Activity
{
    private Language lang;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Removes the title of the Activity.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_safety_question);

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();

        //Uses saved values from SharedPreferences to give various fields their values.
        setFieldValues();

        //Update certain fields to be either usable or unusable.
        setFieldAccess();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_safety_question, menu);
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
        EditText questionEditText = (EditText) findViewById(R.id.questionEditText);
        EditText hintEditText = (EditText) findViewById(R.id.hintEditText);
        EditText answerEditText = (EditText) findViewById(R.id.answerEditText);
        EditText repeatAnswerEditText = (EditText) findViewById(R.id.repeatAnswerEditText);
        EditText currentAnswerEditText = (EditText) findViewById(R.id.currentAnswerEditText);
        CheckBox useSafetyQuestionCheckBox = (CheckBox) findViewById(R.id.useSafetyQuestionCheckBox);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        //Update text.
        questionEditText.setHint(lang.QUESTION_EDIT_TEXT);
        hintEditText.setHint(lang.HINT_EDIT_TEXT);
        answerEditText.setHint(lang.ANSWER_EDIT_TEXT);
        repeatAnswerEditText.setHint(lang.REPEAT_ANSWER_EDIT_TEXT);
        currentAnswerEditText.setHint(lang.CURRENT_ANSWER_EDIT_TEXT);
        useSafetyQuestionCheckBox.setText(lang.USE_SAFETY_QUESTION_CHECK_BOX);
        saveButton.setText(lang.SAVE_BUTTON);
        backButton.setText(lang.BACK_BUTTON);
    }

    //Bring values from SharedPreferences and use them in appropriate places.
    public void setFieldValues()
    {
        //Variable initialization.
        EditText questionEditText = (EditText) findViewById(R.id.questionEditText);
        EditText hintEditText = (EditText) findViewById(R.id.hintEditText);
        EditText answerEditText = (EditText) findViewById(R.id.answerEditText);
        EditText repeatAnswerEditText = (EditText) findViewById(R.id.repeatAnswerEditText);
        EditText currentAnswerEditText = (EditText) findViewById(R.id.currentAnswerEditText);
        CheckBox useSafetyQuestionCheckBox = (CheckBox) findViewById(R.id.useSafetyQuestionCheckBox);

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Gives values to the safety question fields depending on if there are values to give.
        if(!prefs.getString("question", "").equals(""))
        {
            questionEditText.setText(prefs.getString("question", ""));
        }

        //See above.
        if(!prefs.getString("hint", "").equals(""))
        {
            hintEditText.setText(prefs.getString("hint", ""));
        }

        //The answer fields are always created empty.
        answerEditText.setText("");
        repeatAnswerEditText.setText("");
        currentAnswerEditText.setText("");

        //Checks or un-checks useSafetyQuestion depending on what's found on that key in SharedPreferences.
        useSafetyQuestionCheckBox.setChecked(prefs.getBoolean("useSafetyQuestion", false));
    }

    //Makes certain fields usable or unusable.
    private void setFieldAccess()
    {
        //Variable initialization.
        EditText currentAnswerEditText = (EditText) findViewById(R.id.currentAnswerEditText);

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Sets the CheckBoxes to either be active or not depending on what a user has saved to SharedPreferences.
        if(prefs.getString("answer", "").equals(""))
        {
            //Deactivates.
            currentAnswerEditText.setEnabled(false);
        }
        else
        {
            //Activates.
            currentAnswerEditText.setEnabled(true);
        }
    }

    //On click methods below.
    //----------------------------------------------------------------------------------------------
    //For useSafetyQuestionCheckBox.
    public void useSafetyQuestionCheckBox(View view)
    {
        //Nothing needs to be done here for now. The CheckBox can be checked/un-checked without the use for any manually given listeners.
    }

    //For saveButton.
    public void saveButton(View view)
    {
        //Variable initialization.
        EditText questionEditText = (EditText) findViewById(R.id.questionEditText);
        EditText hintEditText = (EditText) findViewById(R.id.hintEditText);
        EditText answerEditText = (EditText) findViewById(R.id.answerEditText);
        EditText repeatAnswerEditText = (EditText) findViewById(R.id.repeatAnswerEditText);
        EditText currentAnswerEditText = (EditText) findViewById(R.id.currentAnswerEditText);
        CheckBox useSafetyQuestionCheckBox = (CheckBox) findViewById(R.id.useSafetyQuestionCheckBox);

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Initialize an editor for prefs.
        SharedPreferences.Editor editor = prefs.edit();

        //Runs if the user has given the correct answer to the previous question.
        if(currentAnswerEditText.getText().toString().equals(prefs.getString("answer", "")))
        {
            //Makes sure both answers match. If not, nothing is saved.
            if(answerEditText.getText().toString().equals(repeatAnswerEditText.getText().toString()))
            {
                //Edits/creates the question related keys in prefs with user given values.
                editor.putString("question", questionEditText.getText().toString());
                editor.putString("hint", hintEditText.getText().toString());
                editor.putString("answer", answerEditText.getText().toString());
                editor.putString("repeatAnswer", repeatAnswerEditText.getText().toString());
                editor.putBoolean("useSafetyQuestion", useSafetyQuestionCheckBox.isChecked());

                //Commits the changes.
                editor.commit();

                //Uses saved values from SharedPreferences to give various fields their values.
                setFieldValues();

                //Display message.
                Toast.makeText(getBaseContext(), lang.SETTINGS_SAVED, Toast.LENGTH_LONG).show();
            }
            else
            {
                //Display message.
                Toast.makeText(getBaseContext(), lang.SAFETY_QUESTION_ANSWERS_DO_NOT_MATCH, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            //Display message.
            Toast.makeText(getBaseContext(), lang.SAFETY_QUESTION_WRONG_PREVIOUS_ANSWER, Toast.LENGTH_LONG).show();
        }
    }

    //For backButton.
    public void backButton(View view)
    {
        finish();
    }
    //----------------------------------------------------------------------------------------------
}
