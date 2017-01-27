package com.example.x.digdos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AddRepeat extends Activity
{
    private Language lang;
    private ArrayList<ActivationData> customRepeatArrayList;
    private RepeatData repeatData;
    private boolean isTypeMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Makes so the keyboard doesn't automatically pop up due to ScrollView.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Removes the title of the Activity.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Defines which xml file is shown in this Activity and shows it.
        setContentView(R.layout.activity_add_repeat);

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();

        //Fill repeatSpinner with data (minute, hour etc.).
        fillRepeatSpinner();

        //Update data based on what is received.
        updateDataWithReceivedData();

        //Initialize various fields.
        initializeFields();

        //Determine which fields are usable and which aren't.
        setFieldAccess();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_repeat, menu);
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
        CheckBox repeatCheckBox = (CheckBox) findViewById(R.id.repeatCheckBox);
        TextView repeatModeTextView = (TextView) findViewById(R.id.repeatModeTextView);
        RadioButton periodicalRadioButton = (RadioButton) findViewById(R.id.periodicalRadioButton);
        RadioButton customRadioButton = (RadioButton) findViewById(R.id.customRadioButton);
        TextView periodicalTextView = (TextView) findViewById(R.id.periodicalTextView);
        TextView repeatTextView = (TextView) findViewById(R.id.repeatTextView);
        TextView repeatEndTextView = (TextView) findViewById(R.id.repeatEndTextView);
        TextView customTextView = (TextView) findViewById(R.id.customTextView);
        EditText locationEditText = (EditText) findViewById(R.id.locationEditText);
        Button addButton = (Button) findViewById(R.id.addButton);
        Button removeButton = (Button) findViewById(R.id.removeButton);
        Button useButton = (Button) findViewById(R.id.useButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        //Update text.
        repeatCheckBox.setText(lang.REPEAT_CHECK_BOX);
        repeatModeTextView.setText(lang.REPEAT_MODE_TEXT_VIEW);
        periodicalRadioButton.setText(lang.PERIODICAL_RADIO_BUTTON);
        customRadioButton.setText(lang.CUSTOM_RADIO_BUTTON);
        periodicalTextView.setText(lang.PERIODICAL_RADIO_BUTTON);
        repeatTextView.setText(lang.REPEAT_TEXT_VIEW);
        repeatEndTextView.setText(lang.REPEAT_END_TEXT_VIEW);
        customTextView.setText(lang.CUSTOM_RADIO_BUTTON);
        locationEditText.setHint(lang.LOCATION_EDIT_TEXT);
        addButton.setText(lang.ADD_BUTTON);
        removeButton.setText(lang.REMOVE_BUTTON);
        useButton.setText(lang.USE_BUTTON);
        backButton.setText(lang.BACK_BUTTON);
    }

    //Determine which fields are usable and which aren't.
    private void setFieldAccess()
    {
        CheckBox repeatCheckBox = (CheckBox) findViewById(R.id.repeatCheckBox);
        RadioGroup repeatModeRadioGroup = (RadioGroup) findViewById(R.id.repeatModeRadioGroup);
        TextView repeatModeTextView = (TextView) findViewById(R.id.repeatModeTextView);
        RadioButton periodicalRadioButton = (RadioButton) findViewById(R.id.periodicalRadioButton);
        RadioButton customRadioButton = (RadioButton) findViewById(R.id.customRadioButton);
        TextView periodicalTextView = (TextView) findViewById(R.id.periodicalTextView);
        TextView repeatTextView = (TextView) findViewById(R.id.repeatTextView);
        EditText repeatFrequencyEditText = (EditText) findViewById(R.id.repeatFrequencyEditText);
        Spinner repeatFrequencySpinner = (Spinner) findViewById(R.id.repeatFrequencySpinner);
        TextView repeatEndTextView = (TextView) findViewById(R.id.repeatEndTextView);
        EditText endDayEditText = (EditText) findViewById(R.id.endDayEditText);
        TextView endDayMonthDotTextView = (TextView) findViewById(R.id.endDayMonthDotTextView);
        EditText endMonthEditText = (EditText) findViewById(R.id.endMonthEditText);
        TextView endMonthYearDotTextView = (TextView) findViewById(R.id.endMonthYearDotTextView);
        EditText endYearEditText = (EditText) findViewById(R.id.endYearEditText);
        EditText endHourEditText = (EditText) findViewById(R.id.endHourEditText);
        TextView endHourMinuteColonTextView = (TextView) findViewById(R.id.endHourMinuteColonTextView);
        EditText endMinuteEditText = (EditText) findViewById(R.id.endMinuteEditText);
        TextView customTextView = (TextView) findViewById(R.id.customTextView);
        EditText dayEditText = (EditText) findViewById(R.id.dayEditText);
        TextView dayMonthDotTextView = (TextView) findViewById(R.id.dayMonthDotTextView);
        EditText monthEditText = (EditText) findViewById(R.id.monthEditText);
        TextView monthYearDotTextView = (TextView) findViewById(R.id.monthYearDotTextView);
        EditText yearEditText = (EditText) findViewById(R.id.yearEditText);
        EditText hourEditText = (EditText) findViewById(R.id.hourEditText);
        TextView hourMinuteColonTextView = (TextView) findViewById(R.id.hourMinuteColonTextView);
        EditText minuteEditText = (EditText) findViewById(R.id.minuteEditText);
        EditText locationEditText = (EditText) findViewById(R.id.locationEditText);
        Button addButton = (Button) findViewById(R.id.addButton);
        Button removeButton = (Button) findViewById(R.id.removeButton);
        Spinner customRepeatSpinner = (Spinner) findViewById(R.id.customRepeatSpinner);

        //Set enabled status of various things on the screen depending on certain selections.
        if(!repeatCheckBox.isChecked())
        {
            repeatModeRadioGroup.setEnabled(false);
            repeatModeTextView.setEnabled(false);
            periodicalRadioButton.setEnabled(false);
            customRadioButton.setEnabled(false);
            periodicalTextView.setEnabled(false);
            repeatTextView.setEnabled(false);
            repeatFrequencyEditText.setEnabled(false);
            repeatFrequencySpinner.setEnabled(false);
            repeatEndTextView.setEnabled(false);
            endDayEditText.setEnabled(false);
            endDayMonthDotTextView.setEnabled(false);
            endMonthEditText.setEnabled(false);
            endMonthYearDotTextView.setEnabled(false);
            endYearEditText.setEnabled(false);
            endHourEditText.setEnabled(false);
            endHourMinuteColonTextView.setEnabled(false);
            endMinuteEditText.setEnabled(false);
            customTextView.setEnabled(false);
            dayEditText.setEnabled(false);
            dayMonthDotTextView.setEnabled(false);
            monthEditText.setEnabled(false);
            monthYearDotTextView.setEnabled(false);
            yearEditText.setEnabled(false);
            hourEditText.setEnabled(false);
            hourMinuteColonTextView.setEnabled(false);
            minuteEditText.setEnabled(false);
            locationEditText.setEnabled(false);
            addButton.setEnabled(false);
            removeButton.setEnabled(false);
            customRepeatSpinner.setEnabled(false);
        }
        else
        {
            repeatModeRadioGroup.setEnabled(true);
            repeatModeTextView.setEnabled(true);
            periodicalRadioButton.setEnabled(true);
            customRadioButton.setEnabled(true);

            if(periodicalRadioButton.isChecked())
            {
                periodicalTextView.setEnabled(true);
                repeatTextView.setEnabled(true);
                repeatFrequencyEditText.setEnabled(true);
                repeatFrequencySpinner.setEnabled(true);
                repeatEndTextView.setEnabled(true);
                endDayEditText.setEnabled(true);
                endDayMonthDotTextView.setEnabled(true);
                endMonthEditText.setEnabled(true);
                endMonthYearDotTextView.setEnabled(true);
                endYearEditText.setEnabled(true);
                endHourEditText.setEnabled(true);
                endHourMinuteColonTextView.setEnabled(true);
                endMinuteEditText.setEnabled(true);
                customTextView.setEnabled(false);
                dayEditText.setEnabled(false);
                dayMonthDotTextView.setEnabled(false);
                monthEditText.setEnabled(false);
                monthYearDotTextView.setEnabled(false);
                yearEditText.setEnabled(false);
                hourEditText.setEnabled(false);
                hourMinuteColonTextView.setEnabled(false);
                minuteEditText.setEnabled(false);
                locationEditText.setEnabled(false);
                addButton.setEnabled(false);
                removeButton.setEnabled(false);
                customRepeatSpinner.setEnabled(false);
            }
            else if(customRadioButton.isChecked())
            {
                periodicalTextView.setEnabled(false);
                repeatTextView.setEnabled(false);
                repeatFrequencyEditText.setEnabled(false);
                repeatFrequencySpinner.setEnabled(false);
                repeatEndTextView.setEnabled(false);
                endDayEditText.setEnabled(false);
                endDayMonthDotTextView.setEnabled(false);
                endMonthEditText.setEnabled(false);
                endMonthYearDotTextView.setEnabled(false);
                endYearEditText.setEnabled(false);
                endHourEditText.setEnabled(false);
                endHourMinuteColonTextView.setEnabled(false);
                endMinuteEditText.setEnabled(false);
                customTextView.setEnabled(true);
                dayEditText.setEnabled(true);
                dayMonthDotTextView.setEnabled(true);
                monthEditText.setEnabled(true);
                monthYearDotTextView.setEnabled(true);
                yearEditText.setEnabled(true);
                hourEditText.setEnabled(true);
                hourMinuteColonTextView.setEnabled(true);
                minuteEditText.setEnabled(true);
                locationEditText.setEnabled(isTypeMedicine);
                addButton.setEnabled(true);
                removeButton.setEnabled(true);
                customRepeatSpinner.setEnabled(true);
            }
            else
            {
                Log.v("setFieldAccess() error", "Neither RadioButton of repeatModeRadioGroup is checked.");

                periodicalTextView.setEnabled(true);
                repeatTextView.setEnabled(true);
                repeatFrequencyEditText.setEnabled(true);
                repeatFrequencySpinner.setEnabled(true);
                repeatEndTextView.setEnabled(true);
                endDayEditText.setEnabled(true);
                endDayMonthDotTextView.setEnabled(true);
                endMonthEditText.setEnabled(true);
                endMonthYearDotTextView.setEnabled(true);
                endYearEditText.setEnabled(true);
                endHourEditText.setEnabled(true);
                endHourMinuteColonTextView.setEnabled(true);
                endMinuteEditText.setEnabled(true);
                customTextView.setEnabled(true);
                dayEditText.setEnabled(true);
                dayMonthDotTextView.setEnabled(true);
                monthEditText.setEnabled(true);
                monthYearDotTextView.setEnabled(true);
                yearEditText.setEnabled(true);
                hourEditText.setEnabled(true);
                hourMinuteColonTextView.setEnabled(true);
                minuteEditText.setEnabled(true);
                locationEditText.setEnabled(true);
                addButton.setEnabled(true);
                removeButton.setEnabled(true);
                customRepeatSpinner.setEnabled(true);
            }
        }
    }

    //Checks if any fields filled by the user contain bad values.
    //Returns true if not, false if yes.
    private boolean checkFieldsForAdd() {
        EditText dayEditText = (EditText) findViewById(R.id.dayEditText);
        EditText monthEditText = (EditText) findViewById(R.id.monthEditText);
        EditText yearEditText = (EditText) findViewById(R.id.yearEditText);
        EditText hourEditText = (EditText) findViewById(R.id.hourEditText);
        EditText minuteEditText = (EditText) findViewById(R.id.minuteEditText);
        EditText locationEditText = (EditText) findViewById(R.id.locationEditText);

        //The boolean starts off as true.
        //If any of the checks make it false, it's returned as such.
        boolean fieldCheck = true;

        //Object creation of FieldLimitation.
        FieldLimitation FL = new FieldLimitation();

        if(!FL.checkMomentFieldValues(
                dayEditText,
                monthEditText,
                yearEditText,
                hourEditText,
                minuteEditText
        ))
        {
            fieldCheck = false;
        }

        if(fieldCheck)
        {
            if(!FL.checkMoment(new Moment(
                    Integer.parseInt(dayEditText.getText().toString()),
                    Integer.parseInt(monthEditText.getText().toString()),
                    Integer.parseInt(yearEditText.getText().toString()),
                    Integer.parseInt(hourEditText.getText().toString()),
                    Integer.parseInt(minuteEditText.getText().toString())
            )))
            {
                fieldCheck = false;
            }
        }

        if(isTypeMedicine)
        {
            if(!FL.checkLocation(locationEditText.getText().toString()))
            {
                fieldCheck = false;
            }
        }

        return fieldCheck;
    }

    //Update data based on what is received.
    private void updateDataWithReceivedData()
    {
        //Get the extra data sent to this Intent in a Bundle.
        Bundle extras = getIntent().getExtras();

        //Get the String version of repeatData from the Bundle.
        String repeatDataString = extras.getString("repeatDataInitial");

        //Create new object of Gson.
        Gson gson = new Gson();

        //Transform repeatDataString to an actual object of the class.
        repeatData = gson.fromJson(repeatDataString, RepeatData.class);

        //Get the boolean sent to this Activity.
        isTypeMedicine = extras.getBoolean("booleanMedicineType");
    }

    //Fill repeatSpinner with data (minute, hour etc.).
    private void fillRepeatSpinner()
    {
        //Initialize repeatSpinner.
        Spinner repeatFrequencySpinner = (Spinner) findViewById(R.id.repeatFrequencySpinner);

        //A list is needed for the ArrayAdapter.
        ArrayList<String> repeatSpinnerArray =  new ArrayList<String>();

        //The list needs to be filled with appropriate data.
        repeatSpinnerArray.add(lang.REPEAT_SPINNER_0);
        repeatSpinnerArray.add(lang.REPEAT_SPINNER_1);
        repeatSpinnerArray.add(lang.REPEAT_SPINNER_2);
        repeatSpinnerArray.add(lang.REPEAT_SPINNER_3);
        repeatSpinnerArray.add(lang.REPEAT_SPINNER_4);
        repeatSpinnerArray.add(lang.REPEAT_SPINNER_5);

        //Creation of ArrayAdapter.
        ArrayAdapter<String> repeatSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, repeatSpinnerArray);

        //Adjusting it to have the correct type of dropdown selection.
        repeatSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Linking together the spinner with the adapter.
        repeatFrequencySpinner.setAdapter(repeatSpinnerAdapter);
    }

    //Initialize various fields based on repeatData.
    private void initializeFields()
    {
        CheckBox repeatCheckBox = (CheckBox) findViewById(R.id.repeatCheckBox);
        RadioButton periodicalRadioButton = (RadioButton) findViewById(R.id.periodicalRadioButton);
        RadioButton customRadioButton = (RadioButton) findViewById(R.id.customRadioButton);
        EditText repeatFrequencyEditText = (EditText) findViewById(R.id.repeatFrequencyEditText);
        EditText endDayEditText = (EditText) findViewById(R.id.endDayEditText);
        EditText endMonthEditText = (EditText) findViewById(R.id.endMonthEditText);
        EditText endYearEditText = (EditText) findViewById(R.id.endYearEditText);
        EditText endHourEditText = (EditText) findViewById(R.id.endHourEditText);
        EditText endMinuteEditText = (EditText) findViewById(R.id.endMinuteEditText);
        Spinner repeatFrequencySpinner = (Spinner) findViewById(R.id.repeatFrequencySpinner);

        //Set repeatCheckBox.
        repeatCheckBox.setChecked(repeatData.getActive());

        //Set one of the RadioButtons to checked.
        if(repeatData.getMode() == 0)
        {
            periodicalRadioButton.setChecked(true);
        }
        else if(repeatData.getMode() == 1)
        {
            customRadioButton.setChecked(true);
        }
        else
        {
            periodicalRadioButton.setChecked(true);

            //Display message in the log.
            Log.v("initializeFields() error", "repeatMode isn't 0 or 1");
        }

        //Set the selection of repeatSpinner
        repeatFrequencySpinner.setSelection(repeatData.getPeriodicalSpinnerPosition());

        endDayEditText.setText(""+repeatData.getPeriodicalActiveUntil().getActivationMoment().getDay());
        endMonthEditText.setText(""+repeatData.getPeriodicalActiveUntil().getActivationMoment().getMonth());
        endYearEditText.setText(""+repeatData.getPeriodicalActiveUntil().getActivationMoment().getYear());
        endHourEditText.setText(""+repeatData.getPeriodicalActiveUntil().getActivationMoment().getHour());
        endMinuteEditText.setText(""+repeatData.getPeriodicalActiveUntil().getActivationMoment().getMinute());

        //Set a number to repeatFrequencyEditText.
        repeatFrequencyEditText.setText(""+repeatData.getPeriodicalFrequency());

        //Makes customRepeatArrayList contain same data as repeatData.customDateAndTimeArrayList.
        customRepeatArrayList = new ArrayList<ActivationData>(repeatData.getCustomActivationDataList());

        //updates manualRepeatSpinner with the current customRepeatArrayList data.
        updateCustomRepeatSpinner();
    }

    //Updates manualRepeatSpinner with the current values.
    private void updateCustomRepeatSpinner()
    {
        Spinner customRepeatSpinner = (Spinner) findViewById(R.id.customRepeatSpinner);

        //Creation of ArrayAdapter.
        ArrayAdapter<ActivationData> customRepeatSpinnerAdapter = new ArrayAdapter<ActivationData>(this, android.R.layout.simple_spinner_item, customRepeatArrayList);

        //Adjusting it to have the correct type of dropdown selection.
        customRepeatSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Linking together the spinner with the adapter.
        customRepeatSpinner.setAdapter(customRepeatSpinnerAdapter);
    }

    //Give EditText to check, returns its int content or 0 by default if the content isn't a valid positive integer.
    private int getIntValueOfEditText(EditText ET)
    {
        int ETInt = 0;

        try
        {
            //Get parsed integer value from ET.
            ETInt = Integer.parseInt(ET.getText().toString());

            //Makes sure ETInt can't be returned as negative.
            if(ETInt < 0)
            {
                ETInt = 0;
            }
        }
        catch(Exception e)
        {
            Log.v("getIntValueOfEditText() parsing error", "Content of ET wasn't an integer");
        }

        return ETInt;
    }

    //On click methods below.
    //----------------------------------------------------------------------------------------------
    //For addButton.
    public void repeatCheckBox(View view)
    {
        //Determine which fields are usable and which aren't.
        setFieldAccess();
    }

    //For addButton.
    public void periodicalRadioButton(View view)
    {
        //Determine which fields are usable and which aren't.
        setFieldAccess();
    }

    //For addButton.
    public void customRadioButton(View view)
    {
        //Determine which fields are usable and which aren't.
        setFieldAccess();
    }

    //For addButton.
    public void addButton(View view)
    {
        if(checkFieldsForAdd())
        {
            EditText dayEditText = (EditText) findViewById(R.id.dayEditText);
            EditText monthEditText = (EditText) findViewById(R.id.monthEditText);
            EditText yearEditText = (EditText) findViewById(R.id.yearEditText);
            EditText hourEditText = (EditText) findViewById(R.id.hourEditText);
            EditText minuteEditText = (EditText) findViewById(R.id.minuteEditText);
            EditText locationEditText = (EditText) findViewById(R.id.locationEditText);

            int locationEditTextValue;

            if(isTypeMedicine)
            {
                locationEditTextValue = getIntValueOfEditText(locationEditText);
            }
            else
            {
                locationEditTextValue = 0;
            }

            //Create ActivationData object.
            ActivationData AD = new ActivationData(
                    new Moment(
                            Integer.parseInt(dayEditText.getText().toString()),
                            Integer.parseInt(monthEditText.getText().toString()),
                            Integer.parseInt(yearEditText.getText().toString()),
                            Integer.parseInt(hourEditText.getText().toString()),
                            Integer.parseInt(minuteEditText.getText().toString())
                    ),
                    locationEditTextValue
            );

            //Add ActivationData object to dateAndTimeArrayListList.
            customRepeatArrayList.add(AD);

            //Adds the newly created date to manualRepeatSpinner.
            updateCustomRepeatSpinner();
        }
    }

    //For removeButton.
    public void removeButton(View view)
    {
        Spinner customRepeatSpinner = (Spinner) findViewById(R.id.customRepeatSpinner);

        //Runs the deleting process only if customRepeatSpinner isn't empty.
        if(customRepeatSpinner.getSelectedItemPosition() != -1)
        {
            //Remove the corresponding item from customRepeatArrayList compared to what currently is selected in customRepeatSpinner.
            customRepeatArrayList.remove(customRepeatSpinner.getSelectedItemPosition());

            //Adds the newly created date to manualRepeatSpinner.
            updateCustomRepeatSpinner();
        }
    }

    //For useButton.
    public void useButton(View view)
    {
        CheckBox repeatCheckBox = (CheckBox) findViewById(R.id.repeatCheckBox);
        RadioButton periodicalRadioButton = (RadioButton) findViewById(R.id.periodicalRadioButton);
        RadioButton customRadioButton = (RadioButton) findViewById(R.id.customRadioButton);
        EditText repeatFrequencyEditText = (EditText) findViewById(R.id.repeatFrequencyEditText);
        Spinner repeatFrequencySpinner = (Spinner) findViewById(R.id.repeatFrequencySpinner);
        EditText endDayEditText = (EditText) findViewById(R.id.endDayEditText);
        EditText endMonthEditText = (EditText) findViewById(R.id.endMonthEditText);
        EditText endYearEditText = (EditText) findViewById(R.id.endYearEditText);
        EditText endHourEditText = (EditText) findViewById(R.id.endHourEditText);
        EditText endMinuteEditText = (EditText) findViewById(R.id.endMinuteEditText);

        //The variables that are used to create repeatData.
        boolean repeat = repeatCheckBox.isChecked();
        int repeatMode = 2;
        if(periodicalRadioButton.isChecked())
        {
            repeatMode = 0;
        }
        else if(customRadioButton.isChecked())
        {
            repeatMode = 1;
        }
        int repeatFrequencyInt = Integer.parseInt(repeatFrequencyEditText.getText().toString());
        int repeatFrequencySpinnerPosition = repeatFrequencySpinner.getSelectedItemPosition();
        int endDayInt = getIntValueOfEditText(endDayEditText);
        if(endDayInt == 0) endDayInt = 1;
        int endMonthInt = getIntValueOfEditText(endMonthEditText);
        if(endMonthInt == 0) endMonthInt = 1;
        int endYearInt = getIntValueOfEditText(endYearEditText);
        if(endYearInt == 0) endYearInt = 8999;
        int endHourInt = getIntValueOfEditText(endHourEditText);
        int endMinuteInt = getIntValueOfEditText(endMinuteEditText);
        ActivationData activeUntil = new ActivationData(
                new Moment(
                        endDayInt,
                        endMonthInt,
                        endYearInt,
                        endHourInt,
                        endMinuteInt
                )
        );

        //Create object of RepeatData.
        repeatData = new RepeatData(
                repeat,
                repeatMode,
                repeatFrequencyInt,
                repeatFrequencySpinnerPosition,
                activeUntil,
                customRepeatArrayList
        );

        //Initialize gson.
        Gson gson = new Gson();

        //Convert repeatData to String form.
        String repeatDataString = gson.toJson(repeatData);

        //Create new Intent.
        Intent returnIntent = new Intent();

        //Put the String version of repeatData to returnIntent under the name "result".
        returnIntent.putExtra("repeatDataResult", repeatDataString);

        //Prepare returnIntent to return something.
        setResult(RESULT_OK, returnIntent);

        //Finish activity.
        finish();
    }

    //For backButton.
    public void backButton(View view)
    {
        //Create new Intent.
        Intent returnIntent = new Intent();

        //Prepare returnIntent to return nothing.
        setResult(RESULT_CANCELED, returnIntent);

        //Finish activity.
        finish();
    }
    //----------------------------------------------------------------------------------------------
}
