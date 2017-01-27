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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;

import java.util.ArrayList;


public class ReminderActivity extends Activity
{
    private Language lang;
    private ImageData imageData;
    private SoundData soundData;
    private RepeatData repeatData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Makes so the keyboard doesn't automatically pop up due to ScrollView.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Removes the title of the Activity.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Defines which xml file is shown in this Activity and shows it.
        setContentView(R.layout.activity_reminder);

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();

        //Get values for type and mode from SharedPreferences.
        setTypeAndMode();

        //Updates the content of reminderSpinner.
        updateReminderSpinnerContent();

        //Set listener for reminderSpinner.
        setReminderSpinnerListener();

        //Update field data.
        updateFields();

        //Set field access.
        setFieldAccess();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reminder, menu);
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
        TextView typeTextView = (TextView) findViewById(R.id.typeTextView);
        RadioButton medicineRadioButton = (RadioButton) findViewById(R.id.medicineRadioButton);
        RadioButton notificationRadioButton = (RadioButton) findViewById(R.id.notificationRadioButton);
        TextView modeTextView = (TextView) findViewById(R.id.modeTextView);
        RadioButton newRadioButton = (RadioButton) findViewById(R.id.newRadioButton);
        RadioButton editRadioButton = (RadioButton) findViewById(R.id.editRadioButton);
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText locationEditText = (EditText) findViewById(R.id.locationEditText);
        EditText noteEditText = (EditText) findViewById(R.id.noteEditText);
        TextView alertDateAndTimeTextView = (TextView) findViewById(R.id.alertDateAndTimeTextView);
        Button addImageButton = (Button) findViewById(R.id.addImageButton);
        Button addSoundButton = (Button) findViewById(R.id.addSoundButton);
        Button addRepeatButton = (Button) findViewById(R.id.addRepeatButton);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        //Update text.
        typeTextView.setText(lang.TYPE_TEXT_VIEW);
        medicineRadioButton.setText(lang.MEDICINE_RADIO_BUTTON);
        notificationRadioButton.setText(lang.NOTIFICATION_RADIO_BUTTON);
        modeTextView.setText(lang.MODE_TEXT_VIEW);
        newRadioButton.setText(lang.NEW_RADIO_BUTTON);
        editRadioButton.setText(lang.EDIT_RADIO_BUTTON);
        nameEditText.setHint(lang.NAME_EDIT_TEXT);
        locationEditText.setHint(lang.LOCATION_EDIT_TEXT);
        noteEditText.setHint(lang.NOTE_EDIT_TEXT);
        alertDateAndTimeTextView.setText(lang.ALERT_DATE_AND_TIME_TEXT_VIEW);
        addImageButton.setText(lang.ADD_IMAGE_BUTTON);
        addSoundButton.setText(lang.ADD_SOUND_BUTTON);
        addRepeatButton.setText(lang.ADD_REPEAT_BUTTON);
        saveButton.setText(lang.SAVE_BUTTON);
        deleteButton.setText(lang.DELETE_BUTTON);
        backButton.setText(lang.BACK_BUTTON);
    }

    //Give values to type and mode depending on what the user had last time (in other words what's been saved to SharedPreferences).
    private void setTypeAndMode()
    {
        //Variable initialization.
        RadioButton medicineRadioButton = (RadioButton) findViewById(R.id.medicineRadioButton);
        RadioButton notificationRadioButton = (RadioButton) findViewById(R.id.notificationRadioButton);
        RadioButton newRadioButton = (RadioButton) findViewById(R.id.newRadioButton);
        RadioButton editRadioButton = (RadioButton) findViewById(R.id.editRadioButton);

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        medicineRadioButton.setChecked(prefs.getBoolean("typeMedicine", false));
        notificationRadioButton.setChecked(prefs.getBoolean("typeNotification", false));
        newRadioButton.setChecked(prefs.getBoolean("modeNew", false));
        editRadioButton.setChecked(prefs.getBoolean("modeEdit", false));
    }

    //Update SharedPreferences with type and mode info.
    private void updateSP()
    {
        //Variable initialization.
        RadioButton medicineRadioButton = (RadioButton) findViewById(R.id.medicineRadioButton);
        RadioButton notificationRadioButton = (RadioButton) findViewById(R.id.notificationRadioButton);
        RadioButton newRadioButton = (RadioButton) findViewById(R.id.newRadioButton);
        RadioButton editRadioButton = (RadioButton) findViewById(R.id.editRadioButton);

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Opens editor for prefs.
        SharedPreferences.Editor editor = prefs.edit();

        //Update information with the editor.
        editor.putBoolean("typeMedicine", medicineRadioButton.isChecked());
        editor.putBoolean("typeNotification", notificationRadioButton.isChecked());
        editor.putBoolean("modeNew", newRadioButton.isChecked());
        editor.putBoolean("modeEdit", editRadioButton.isChecked());

        //Save the changes.
        editor.commit();
    }

    //Return true if type selected is medicine.
    private boolean typeMedicine()
    {
        //Variable initialization/declaration.
        RadioButton medicineRadioButton = (RadioButton) findViewById(R.id.medicineRadioButton);

        return medicineRadioButton.isChecked();
    }

    //Return true if type selected is notification.
    private boolean typeNotification()
    {
        //Variable initialization/declaration.
        RadioButton notificationRadioButton = (RadioButton) findViewById(R.id.notificationRadioButton);

        return notificationRadioButton.isChecked();
    }

    //Return true if mode selected is new.
    private boolean modeNew()
    {
        //Variable initialization/declaration.
        RadioButton newRadioButton = (RadioButton) findViewById(R.id.newRadioButton);

        return newRadioButton.isChecked();
    }

    //Return true if mode selected is edit.
    private boolean modeEdit()
    {
        //Variable initialization/declaration.
        RadioButton editRadioButton = (RadioButton) findViewById(R.id.editRadioButton);

        return editRadioButton.isChecked();
    }

    //Initialize various fields.
    private void updateFields()
    {
        //Variable declaration/initialization.
        Spinner reminderSpinner = (Spinner) findViewById(R.id.reminderSpinner);
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText locationEditText = (EditText) findViewById(R.id.locationEditText);
        EditText noteEditText = (EditText) findViewById(R.id.noteEditText);
        EditText dayEditText = (EditText) findViewById(R.id.dayEditText);
        EditText monthEditText = (EditText) findViewById(R.id.monthEditText);
        EditText yearEditText = (EditText) findViewById(R.id.yearEditText);
        EditText hourEditText = (EditText) findViewById(R.id.hourEditText);
        EditText minuteEditText = (EditText) findViewById(R.id.minuteEditText);

        if(reminderSpinner.getSelectedItemPosition() != -1)
        {
            //Create an object based on the data from the current selection of reminderSpinner.
            Reminder R = (Reminder) reminderSpinner.getSelectedItem();

            //Fill fields in the activity with data from R.
            nameEditText.setText(R.getName());
            locationEditText.setText(""+R.getInitialActivation().getLocation());
            noteEditText.setText(R.getNote());
            dayEditText.setText("" + R.getInitialActivation().getActivationMoment().getDay());
            monthEditText.setText(""+R.getInitialActivation().getActivationMoment().getMonth());
            yearEditText.setText(""+R.getInitialActivation().getActivationMoment().getYear());
            hourEditText.setText(""+R.getInitialActivation().getActivationMoment().getHour());
            minuteEditText.setText(""+R.getInitialActivation().getActivationMoment().getMinute());
            imageData = R.getImageData();
            soundData = R.getSoundData();
            repeatData = R.getRepeatData();
        }
        else
        {
            //Set empty/default values.
            nameEditText.setText("");
            locationEditText.setText("");
            noteEditText.setText("");
            dayEditText.setText("");
            monthEditText.setText("");
            yearEditText.setText("");
            hourEditText.setText("");
            minuteEditText.setText("");
            imageData = new ImageData(
                    false,
                    ""
            );
            soundData = new SoundData(
                    false,
                    "",
                    7
            );
            repeatData = new RepeatData(
                    false,
                    0,
                    1,
                    0,
                    new ActivationData(
                            new Moment(
                                    1,
                                    1,
                                    8999,
                                    0,
                                    0
                            )
                    ),
                    new ArrayList<ActivationData>()
            );
        }
    }

    //Fill reminderSpinner with data of every created medicine and create a listener.
    private void updateReminderSpinnerContent()
    {
        //Variable initialization/declaration.
        Spinner reminderSpinner = (Spinner) findViewById(R.id.reminderSpinner);

        if(modeNew())
        {
            //Initialize an empty list.
            ArrayList<String> reminderSpinnerArray =  new ArrayList<String>();

            //Creation of ArrayAdapter.
            ArrayAdapter<String> reminderSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, reminderSpinnerArray);

            //Adjusting it to have the correct type of dropdown selection.
            reminderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            //Linking together the spinner with the adapter.
            reminderSpinner.setAdapter(reminderSpinnerAdapter);
        }
        else if(modeEdit())
        {
            DatabaseQuery DQ = new DatabaseQuery(getApplicationContext());

            //Initialize a Cursor.
            Cursor CR;

            //Fills CR with either all medicines or all notifications depending on which type is selected.
            if(typeMedicine())
            {
                //fill CR with information about every Medicine.
                CR = DQ.getReminderInformation(GV.TYPE_MEDICINE);
            }
            else if(typeNotification())
            {
                //fill CR with information about every Notification.
                CR = DQ.getReminderInformation(GV.TYPE_NOTIFICATION);
            }
            else
            {
                //fill CR with information about every Reminder.
                CR = DQ.getReminderInformation(GV.TYPE_ALL);

                //Display message in log.
                Log.v("updateReminderSpinner() error", "No valid type selected. Defaulting to all.");
            }

            //Initialize a list to be filled with objects of SpinnerListItem.
            ArrayList<Reminder> reminderSpinnerArray =  new ArrayList<Reminder>();

            Gson gson = new Gson();

            while(CR.moveToNext())
            {
                ActivationData initialActivation = gson.fromJson(CR.getString(4),
                        ActivationData.class);
                imageData = gson.fromJson(CR.getString(5), ImageData.class);
                soundData = gson.fromJson(CR.getString(6), SoundData.class);
                repeatData = gson.fromJson(CR.getString(7), RepeatData.class);
                ActivationData latestActivation = gson.fromJson(CR.getString(8),
                        ActivationData.class);

                //Adds a new object of Reminder based on the current CR position data to reminderSpinnerArray.
                reminderSpinnerArray.add(
                        new Reminder(
                                CR.getInt(0),
                                CR.getInt(1),
                                CR.getString(2),
                                CR.getString(3),
                                initialActivation,
                                imageData,
                                soundData,
                                repeatData,
                                latestActivation,
                                CR.getInt(9)
                        )
                );
            }

            //Creation of ArrayAdapter.
            ArrayAdapter<Reminder> reminderSpinnerAdapter = new ArrayAdapter<Reminder>(this, android.R.layout.simple_spinner_item, reminderSpinnerArray);

            //Adjusting it to have the correct type of dropdown selection.
            reminderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            //Linking together the spinner with the adapter.
            reminderSpinner.setAdapter(reminderSpinnerAdapter);

            //Closes the database connection.
            DQ.closeDatabaseHelper();
        }
        else
        {
            //Display message in log.
            Log.v("updateReminderSpinner() error", "No valid mode selected.");
        }
    }

    //Set listener for reminderSpinner
    private void setReminderSpinnerListener()
    {
        Spinner reminderSpinner = (Spinner) findViewById(R.id.reminderSpinner);

        reminderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                updateFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {

            }
        });
    }

    //Makes certain fields usable or unusable.
    private void setFieldAccess()
    {
        Spinner reminderSpinner = (Spinner) findViewById(R.id.reminderSpinner);
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText noteEditText = (EditText) findViewById(R.id.noteEditText);
        TextView alertDateAndTimeTextView = (TextView) findViewById(R.id.alertDateAndTimeTextView);
        EditText dayEditText = (EditText) findViewById(R.id.dayEditText);
        TextView dayMonthDotTextView = (TextView) findViewById(R.id.dayMonthDotTextView);
        EditText monthEditText = (EditText) findViewById(R.id.monthEditText);
        TextView monthYearDotTextView = (TextView) findViewById(R.id.monthYearDotTextView);
        EditText yearEditText = (EditText) findViewById(R.id.yearEditText);
        EditText hourEditText = (EditText) findViewById(R.id.hourEditText);
        TextView hourMinuteColonTextView = (TextView) findViewById(R.id.hourMinuteColonTextView);
        EditText minuteEditText = (EditText) findViewById(R.id.minuteEditText);
        EditText locationEditText = (EditText) findViewById(R.id.locationEditText);
        Button addImageButton = (Button) findViewById(R.id.addImageButton);
        Button addSoundButton = (Button) findViewById(R.id.addSoundButton);
        Button addRepeatButton = (Button) findViewById(R.id.addRepeatButton);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);

        //Sets the CheckBoxes to either be active or not depending on which type and mode are currently active.
        if(modeEdit())
        {
            if(reminderSpinner.getSelectedItemPosition() != -1)
            {
                reminderSpinner.setEnabled(true);
                nameEditText.setEnabled(true);
                noteEditText.setEnabled(true);
                alertDateAndTimeTextView.setEnabled(true);
                dayEditText.setEnabled(true);
                dayMonthDotTextView.setEnabled(true);
                monthEditText.setEnabled(true);
                monthYearDotTextView.setEnabled(true);
                yearEditText.setEnabled(true);
                hourEditText.setEnabled(true);
                hourMinuteColonTextView.setEnabled(true);
                minuteEditText.setEnabled(true);
                locationEditText.setEnabled(typeMedicine());
                addImageButton.setEnabled(true);
                addSoundButton.setEnabled(true);
                addSoundButton.setEnabled(true);
                addRepeatButton.setEnabled(true);
                saveButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }
            else
            {
                reminderSpinner.setEnabled(false);
                nameEditText.setEnabled(false);
                noteEditText.setEnabled(false);
                alertDateAndTimeTextView.setEnabled(false);
                dayEditText.setEnabled(false);
                dayMonthDotTextView.setEnabled(false);
                monthEditText.setEnabled(false);
                monthYearDotTextView.setEnabled(false);
                yearEditText.setEnabled(false);
                hourEditText.setEnabled(false);
                hourMinuteColonTextView.setEnabled(false);
                minuteEditText.setEnabled(false);
                locationEditText.setEnabled(false);
                addImageButton.setEnabled(false);
                addSoundButton.setEnabled(false);
                addSoundButton.setEnabled(false);
                addRepeatButton.setEnabled(false);
                saveButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }

        }
        else if(modeNew())
        {
            reminderSpinner.setEnabled(false);
            nameEditText.setEnabled(true);
            noteEditText.setEnabled(true);
            alertDateAndTimeTextView.setEnabled(true);
            dayEditText.setEnabled(true);
            dayMonthDotTextView.setEnabled(true);
            monthEditText.setEnabled(true);
            monthYearDotTextView.setEnabled(true);
            yearEditText.setEnabled(true);
            hourEditText.setEnabled(true);
            hourMinuteColonTextView.setEnabled(true);
            minuteEditText.setEnabled(true);
            locationEditText.setEnabled(typeMedicine());
            addImageButton.setEnabled(true);
            addSoundButton.setEnabled(true);
            addSoundButton.setEnabled(true);
            addRepeatButton.setEnabled(true);
            saveButton.setEnabled(true);
            deleteButton.setEnabled(false);
        }
        else
        {
            //Display message in log.
            Log.v("setFieldAccess() error", "No valid mode selected.");
        }
    }

    //Checks if any fields filled by the user contain bad values.
    //Returns true if not, false if yes.
    private boolean checkFields()
    {
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText noteEditText = (EditText) findViewById(R.id.noteEditText);
        EditText dayEditText = (EditText) findViewById(R.id.dayEditText);
        EditText monthEditText = (EditText) findViewById(R.id.monthEditText);
        EditText yearEditText = (EditText) findViewById(R.id.yearEditText);
        EditText hourEditText = (EditText) findViewById(R.id.hourEditText);
        EditText minuteEditText = (EditText) findViewById(R.id.minuteEditText);

        //Variable initialization.
        boolean fieldCheck = true;

        //Object creation of FieldLimitation.
        FieldLimitation FL = new FieldLimitation();

        //Checks on all fields by running the corresponding method in FL.
        if(!FL.checkName(nameEditText.getText().toString()))
        {
            fieldCheck = false;
        }

        if(!FL.checkNote(noteEditText.getText().toString()))
        {
            fieldCheck = false;
        }

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

        return fieldCheck;
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

    //This method is called once an Activity finishes that was launched with startActivityForResult().
    //Used for taking care of possible data returned from said Activity.
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK)
        {
            if(requestCode == GV.ADD_IMAGE_REQUEST_CODE)
            {
                //Initialize gson.
                Gson gson = new Gson();

                //Creates imageDataString as the result data.
                String imageDataString = data.getStringExtra("imageDataResult");

                //Convert imageData to String form.
                imageData = gson.fromJson(imageDataString, ImageData.class);
            }
            else if(requestCode == GV.ADD_SOUND_REQUEST_CODE)
            {
                //Initialize gson.
                Gson gson = new Gson();

                //Creates soundDataString as the result data.
                String soundDataString = data.getStringExtra("soundDataResult");

                //Convert soundData to String form.
                soundData = gson.fromJson(soundDataString, SoundData.class);
            }
            else if(requestCode == GV.ADD_REPEAT_REQUEST_CODE)
            {
                //Initialize gson.
                Gson gson = new Gson();

                //Creates repeatDataString as the result data.
                String repeatDataString = data.getStringExtra("repeatDataResult");

                //Convert repeatData to String form.
                repeatData = gson.fromJson(repeatDataString, RepeatData.class);
            }
            else
            {
                Log.v("ReminderActivity onActivityResult()", "Unexpected requestCode ("+requestCode+").");
            }
        }
        else if(resultCode == RESULT_CANCELED)
        {
            Log.v("ReminderActivity onActivityResult()", "Nothing was returned.");
        }
        else
        {
            Log.v("ReminderActivity onActivityResult()", "Unexpected resultCode ("+resultCode+").");
        }
    }

    //On click methods below.
    //----------------------------------------------------------------------------------------------
    //For RadioButton of either type or mode.
    public void typeOrModeRadioButton(View view)
    {
        //Update content of reminderSpinner.
        updateReminderSpinnerContent();

        //Update field values..
        updateFields();

        //Set field access.
        setFieldAccess();

        //Update SharedPreferences with type and mode info.
        updateSP();
    }

    public void addImageButton(View view)
    {
        Intent addImageIntent = new Intent(this, AddImage.class);

        Gson gson = new Gson();

        String imageDataString = gson.toJson(imageData);

        addImageIntent.putExtra("imageDataInitial", imageDataString);

        startActivityForResult(addImageIntent, GV.ADD_IMAGE_REQUEST_CODE);
    }

    //For addSoundButton.
    public void addSoundButton(View view)
    {
        //Create new Intent.
        Intent addSoundIntent = new Intent(this, AddSound.class);

        //Create new object of Gson.
        Gson gson = new Gson();

        //Make String version of imageData.
        String soundDataString = gson.toJson(soundData);

        //Attach data to the intent.
        addSoundIntent.putExtra("soundDataInitial", soundDataString);

        //Start another activity expecting a possible result at the end.
        startActivityForResult(addSoundIntent, GV.ADD_SOUND_REQUEST_CODE);
    }

    //For addRepeatButton.
    public void addRepeatButton(View view)
    {
        //Create new Intent.
        Intent addRepeatIntent = new Intent(this, AddRepeat.class);

        //Create new object of Gson.
        Gson gson = new Gson();

        //Attach data to the intent.
        addRepeatIntent.putExtra("repeatDataInitial", gson.toJson(repeatData));
        addRepeatIntent.putExtra("booleanMedicineType", typeMedicine());

        //Start another activity expecting a possible result at the end.
        startActivityForResult(addRepeatIntent, GV.ADD_REPEAT_REQUEST_CODE);
    }

    //For saveButton.
    public void saveButton(View view)
    {
        Spinner reminderSpinner = (Spinner) findViewById(R.id.reminderSpinner);
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText locationEditText = (EditText) findViewById(R.id.locationEditText);
        EditText noteEditText = (EditText) findViewById(R.id.noteEditText);
        EditText dayEditText = (EditText) findViewById(R.id.dayEditText);
        EditText monthEditText = (EditText) findViewById(R.id.monthEditText);
        EditText yearEditText = (EditText) findViewById(R.id.yearEditText);
        EditText hourEditText = (EditText) findViewById(R.id.hourEditText);
        EditText minuteEditText = (EditText) findViewById(R.id.minuteEditText);

        //Runs only if checkFields() returns true, meaning no bad values have been given.
        if(checkFields())
        {
            DatabaseQuery DQ = new DatabaseQuery(getApplicationContext());

            if(modeNew())
            {
                //Variables sent with createReminder().
                int type = 0;
                if(typeMedicine())
                {
                    type = 1;
                }
                else if(typeNotification())
                {
                    type = 2;
                }
                String name = nameEditText.getText().toString();
                String note = noteEditText.getText().toString();
                ActivationData initialActivation = new ActivationData(
                        new Moment(
                                Integer.parseInt(dayEditText.getText().toString()),
                                Integer.parseInt(monthEditText.getText().toString()),
                                Integer.parseInt(yearEditText.getText().toString()),
                                Integer.parseInt(hourEditText.getText().toString()),
                                Integer.parseInt(minuteEditText.getText().toString())
                        ),
                        getIntValueOfEditText(locationEditText)
                );

                Reminder R = new Reminder(0, type, name, note, initialActivation, imageData, soundData, repeatData);

                //Tries to execute createReminder().
                try
                {
                    //Launches createReminder().
                    DQ.createReminder(R);

                    //Closes the database connection.
                    DQ.closeDatabaseHelper();

                    if(typeMedicine())
                    {
                        //Display message.
                        Toast.makeText(getBaseContext(), lang.MEDICINE_CREATED, Toast.LENGTH_LONG).show();
                    }
                    else if(typeNotification())
                    {
                        //Display message.
                        Toast.makeText(getBaseContext(), lang.NOTIFICATION_CREATED, Toast.LENGTH_LONG).show();
                    }
                }
                catch(Exception e)    //Catch possible errors.
                {
                    //Display message.
                    Log.v("Medicine creation error", e.getMessage());
                }
            }
            else if(modeEdit())
            {
                //Create an object based on the data from the current selection of reminderSpinner.
                Reminder R = (Reminder) reminderSpinner.getSelectedItem();

                //Variables sent with updateReminder().
                int id = R.getId();
                int type = 0;
                if(typeMedicine())
                {
                    type = 1;
                }
                else if(typeNotification())
                {
                    type = 2;
                }
                String name = nameEditText.getText().toString();
                String note = noteEditText.getText().toString();
                ActivationData initialActivation = new ActivationData(
                        new Moment(
                                Integer.parseInt(dayEditText.getText().toString()),
                                Integer.parseInt(monthEditText.getText().toString()),
                                Integer.parseInt(yearEditText.getText().toString()),
                                Integer.parseInt(hourEditText.getText().toString()),
                                Integer.parseInt(minuteEditText.getText().toString())
                        ),
                        getIntValueOfEditText(locationEditText)
                );
                initialActivation.setAmountOfTimesDisplayed(R.getInitialActivation().getAmountOfTimesDisplayed());
                ActivationData latestActivation = R.getLatestActivation();
                int amountOfTimesDisplayed = R.getAmountOfTimesDisplayed();

                //Creates a Reminder with the updated data.
                Reminder updatedR = new Reminder(
                        id,
                        type,
                        name,
                        note,
                        initialActivation,
                        imageData,
                        soundData,
                        repeatData,
                        latestActivation,
                        amountOfTimesDisplayed
                );

                //Tries to execute updateMedicine().
                try
                {
                    //Launches updateMedicine().
                    DQ.updateReminder(updatedR);

                    if(typeMedicine())
                    {
                        //Display message.
                        Toast.makeText(getBaseContext(), lang.MEDICINE_INFORMATION_EDITED, Toast.LENGTH_LONG).show();
                    }
                    else if(typeNotification())
                    {
                        //Display message.
                        Toast.makeText(getBaseContext(), lang.NOTIFICATION_INFORMATION_EDITED, Toast.LENGTH_LONG).show();
                    }
                }
                catch(Exception e)    //Catch possible errors.
                {
                    //Display message.
                    Log.v("Medicine editing error", e.getMessage());
                }

                //Closes the database connection.
                DQ.closeDatabaseHelper();
            }
            else
            {
                //Display message.
                Log.v("Error in save()", "No mode was selected.");
            }

            //Update content of reminderSpinner.
            updateReminderSpinnerContent();

            //Update field values..
            updateFields();

            //Set field access.
            setFieldAccess();
        }
    }

    //For deleteButton.
    public void deleteButton(View view)
    {
        //Variable initialization/declaration.
        Spinner reminderSpinner = (Spinner) findViewById(R.id.reminderSpinner);

        DatabaseQuery DQ = new DatabaseQuery(getApplicationContext());

        //Create an object based on the data from the current selection of reminderSpinner.
        Reminder R = (Reminder) reminderSpinner.getSelectedItem();

        //Variable sent with deleteReminder().
        int id = R.getId();

        //Tries to execute deleteReminder().
        try
        {
            //Launches deleteReminder().
            DQ.deleteReminder(id);

            //Closes the database connection.
            DQ.closeDatabaseHelper();

            if(typeMedicine())
            {
                //Display message.
                Toast.makeText(getBaseContext(), lang.MEDICINE_DELETED, Toast.LENGTH_LONG).show();
            }
            else if(typeNotification())
            {
                //Display message.
                Toast.makeText(getBaseContext(), lang.NOTIFICATION_DELETED, Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e)    //Catch possible errors.
        {
            //Display message.
            Log.v("Medicine deletion error", e.getMessage());
        }

        //Update content of reminderSpinner.
        updateReminderSpinnerContent();

        //Update field values..
        updateFields();

        //Set field access.
        setFieldAccess();
    }

    //For backButton.
    public void backButton(View view)
    {
        //End activity, return to previous.
        finish();
    }
    //----------------------------------------------------------------------------------------------
}