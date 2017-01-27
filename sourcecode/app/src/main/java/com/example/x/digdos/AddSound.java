package com.example.x.digdos;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class AddSound extends Activity
{
    private Language lang;
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private SoundData soundData;
    private boolean recordingState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Removes the title of the Activity.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Defines which xml file is shown in this Activity and shows it.
        setContentView(R.layout.activity_add_sound);

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();

        //Update the spinner with all sounds from the folder the program uses.
        updateSpinner();

        //Set max volume (amount of steps) for volumeSeekBar.
        setMaxVolume(15);

        //Create repeatData based on what is received.
        updateSoundDataWithReceivedData();

        //Initialize various fields.
        initializeFields();

        //Determine which fields are usable and which aren't.
        setFieldAccess();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_sound, menu);
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

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void setLanguage()
    {
        Support S = new Support(getApplicationContext());

        lang = S.getLanguage();
    }

    //Replaces text in various fields (like TextViews and Buttons) with the current language counterparts.
    private void createFieldsWithCurrentLanguage()
    {
        CheckBox soundCheckBox = (CheckBox) findViewById(R.id.soundCheckBox);
        EditText soundNameEditText = (EditText) findViewById(R.id.soundNameEditText);
        Button recordButton = (Button) findViewById(R.id.recordButton);
        Button importSoundButton = (Button) findViewById(R.id.importSoundButton);
        TextView volumeTextView = (TextView) findViewById(R.id.volumeTextView);
        Button listenButton = (Button) findViewById(R.id.listenButton);
        Button useButton = (Button) findViewById(R.id.useButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        //Update text.
        soundCheckBox.setText(lang.SOUND_CHECK_BOX);
        soundNameEditText.setHint(lang.SOUND_NAME_EDIT_TEXT);
        recordButton.setText(lang.RECORD_OFF_BUTTON);
        importSoundButton.setText(lang.IMPORT_SOUND_BUTTON);
        volumeTextView.setText(lang.VOLUME_TEXT_VIEW);
        listenButton.setText(lang.LISTEN_BUTTON);
        useButton.setText(lang.USE_BUTTON);
        deleteButton.setText(lang.DELETE_BUTTON);
        backButton.setText(lang.BACK_BUTTON);
    }

    //Determine which fields are usable and which aren't.
    private void setFieldAccess()
    {
        CheckBox soundCheckBox = (CheckBox) findViewById(R.id.soundCheckBox);
        EditText soundNameEditText = (EditText) findViewById(R.id.soundNameEditText);
        Button recordButton = (Button) findViewById(R.id.recordButton);
        ImageView recordImageView = (ImageView) findViewById(R.id.recordImageView);
        Button importSoundButton = (Button) findViewById(R.id.importSoundButton);
        Spinner soundSpinner = (Spinner) findViewById(R.id.soundSpinner);
        TextView volumeTextView = (TextView) findViewById(R.id.volumeTextView);
        SeekBar volumeSeekBar = (SeekBar) findViewById(R.id.volumeSeekBar);
        Button listenButton = (Button) findViewById(R.id.listenButton);
        Button useButton = (Button) findViewById(R.id.useButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        //Set enabled status of various things on the screen depending on certain selections.
        if(!soundCheckBox.isChecked())
        {
            soundNameEditText.setEnabled(false);
            recordButton.setEnabled(false);
            recordImageView.setEnabled(false);
            importSoundButton.setEnabled(false);
            soundSpinner.setEnabled(false);
            volumeTextView.setEnabled(false);
            volumeSeekBar.setEnabled(false);
            listenButton.setEnabled(false);
            useButton.setEnabled(true);
            deleteButton.setEnabled(false);
            backButton.setEnabled(true);
        }
        else
        {
            recordButton.setEnabled(true);

            if(recordingState)
            {
                soundCheckBox.setEnabled(false);
                soundNameEditText.setEnabled(false);
                recordImageView.setEnabled(false);
                importSoundButton.setEnabled(false);
                soundSpinner.setEnabled(false);
                volumeTextView.setEnabled(false);
                volumeSeekBar.setEnabled(false);
                listenButton.setEnabled(false);
                useButton.setEnabled(false);
                deleteButton.setEnabled(false);
                backButton.setEnabled(false);
            }
            else
            {
                soundCheckBox.setEnabled(true);
                soundNameEditText.setEnabled(true);
                recordImageView.setEnabled(true);
                importSoundButton.setEnabled(true);
                soundSpinner.setEnabled(true);
                volumeTextView.setEnabled(true);
                volumeSeekBar.setEnabled(true);
                listenButton.setEnabled(true);
                useButton.setEnabled(true);
                deleteButton.setEnabled(true);
                backButton.setEnabled(true);
            }
        }
    }

    //Create repeatData based on what is received.
    private void updateSoundDataWithReceivedData()
    {
        //Get the extra data sent to this Intent in a Bundle.
        Bundle extras = getIntent().getExtras();

        //Get the String version of repeatData from the Bundle.
        String soundDataString = extras.getString("soundDataInitial");

        //Create new object of Gson.
        Gson gson = new Gson();

        //Transform repeatDataString to an actual object of the class.
        soundData = gson.fromJson(soundDataString, SoundData.class);
    }

    //Initialize various fields based on soundData.
    private void initializeFields()
    {
        CheckBox soundCheckBox = (CheckBox) findViewById(R.id.soundCheckBox);
        Spinner soundSpinner = (Spinner) findViewById(R.id.soundSpinner);
        SeekBar volumeSeekBar = (SeekBar) findViewById(R.id.volumeSeekBar);
        ArrayAdapter<String> soundSpinnerAdapter = (ArrayAdapter<String>) soundSpinner.getAdapter();

        //Set soundCheckBox.
        soundCheckBox.setChecked(soundData.getActive());

        //Set position of soundSpinner if soundData.soundName isn't null or "".
        if(soundData.getName() != null && !soundData.getName().equals(""))
        {
            int spinnerPosition = soundSpinnerAdapter.getPosition(soundData.getName());
            soundSpinner.setSelection(spinnerPosition);
        }

        //Set volumeSeekBar.
        volumeSeekBar.setProgress(soundData.getVolume());
    }

    //Update the spinner with all sounds from the folder the program uses.
    private void updateSpinner()
    {
        Spinner soundSpinner = (Spinner) findViewById(R.id.soundSpinner);
        ArrayList<String> soundSpinnerArray;
        ArrayAdapter<String> soundSpinnerAdapter;
        Support S = new Support(getApplicationContext());

        //Get the files in the sound folder.
        File[] files = S.getFiles(Environment.getExternalStorageDirectory()+"/"+GV.PROGRAM_NAME+"/"+GV.SOUND_FOLDER_NAME);

        //Makes sure length of files isn't 0 and create soundSpinnerArray accordingly.
        if(files == null || files.length == 0)
        {
            soundSpinnerArray =  new ArrayList<String>();
        }
        else
        {
            //Create soundSpinnerArray with the names from files.
            soundSpinnerArray =  new ArrayList<String>(S.getFileNames(files));
        }

        //Adds Default as first string in soundSpinnerArray, which stands for the programs default alarm sound.
        soundSpinnerArray.add(0, lang.SOUND_SPINNER_DEFAULT);

        //Adapter for soundSpinner that uses soundSpinnerArray.
        soundSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, soundSpinnerArray);

        //Adjusting it to have the correct type of dropdown selection.
        soundSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Set ArrayAdapter for Spinner.
        soundSpinner.setAdapter(soundSpinnerAdapter);
    }

    //Set max volume (amount of steps) for volumeSeekBar.
    private void setMaxVolume(int volume)
    {
        SeekBar volumeSeekBar = (SeekBar) findViewById(R.id.volumeSeekBar);

        //Set max value for volumeSeekBar.
        volumeSeekBar.setMax(volume);
    }

    //What happens when another Activity brings back a result to this one.
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            if(requestCode == GV.PICK_FILE_REQUEST_CODE)
            {
                String FilePath = data.getData().getPath();
                //FilePath is your file as a string
            }
        }
    }

    private void startRecording()
    {
        EditText soundNameEditText = (EditText) findViewById(R.id.soundNameEditText);
        mRecorder = new MediaRecorder();

        String soundFolderString = Environment.getExternalStorageDirectory()+"/"+
                GV.PROGRAM_NAME+"/"+
                GV.SOUND_FOLDER_NAME;
        File soundFolderFile = new File(soundFolderString);
        soundFolderFile.mkdirs();

        String soundFileName = soundFolderString+"/"+soundNameEditText.getText().toString()+".mp3";

        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(soundFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try
        {
            mRecorder.prepare();
        }
        catch (IOException e)
        {
            Log.v("AddSound startRecording()",
                    "prepare() failed for mRecorder. "+e.getMessage());
        }

        mRecorder.start();
    }

    private void stopRecording()
    {
        mRecorder.stop();
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;

        updateSpinner();
    }

    //Start listening
    private void startListening()
    {
        Spinner soundSpinner = (Spinner) findViewById(R.id.soundSpinner);
        SeekBar volumeSeekBar = (SeekBar) findViewById(R.id.volumeSeekBar);

        //Create AudioManager.
        AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        //Set volume.
        manager.setStreamVolume(AudioManager.STREAM_MUSIC, volumeSeekBar.getProgress(), 0);

        //Play either the default sound or one of the user given ones.
        if(soundSpinner.getSelectedItem().toString().equals("Default") ||
                soundSpinner.getSelectedItem().toString().equals("Oletus") ||
                soundSpinner.getSelectedItem().toString().equals("Standard"))
        {
            //Creates MediaPlayer with the default alert sound.
            mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alert);
        }
        else
        {
            //Uri for the selected sound.
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+"/"+GV.PROGRAM_NAME+"/"+GV.SOUND_FOLDER_NAME+"/"+soundSpinner.getSelectedItem().toString());

            //Create MediaPlayer with the correct parameters.
            mPlayer = MediaPlayer.create(getApplicationContext(), uri);
        }

        //Play sound.
        mPlayer.start();
    }

    //Stop listening.
    private void stopListening()
    {
        //Reset mPlayer if it isn't null.
        if(!(mPlayer == null))
        {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    //On click methods below.
    //----------------------------------------------------------------------------------------------
    //For soundCheckBox.
    public void soundCheckBox(View view)
    {
        //Determine which fields are usable and which aren't.
        setFieldAccess();
    }

    //For recordCheckBox.
    public void recordButton(View view)
    {
        Button recordButton = (Button) findViewById(R.id.recordButton);
        ImageView recordImageView = (ImageView) findViewById(R.id.recordImageView);

        //Starts recording if recordingState is false. Stops recording otherwise.
        if(!recordingState)
        {
            startRecording();
            recordingState = true;
            recordButton.setText(lang.RECORD_ON_BUTTON);
            recordImageView.setBackgroundResource(R.drawable.record_light);
            setFieldAccess();
        }
        else
        {
            stopRecording();
            recordingState = false;
            recordButton.setText(lang.RECORD_OFF_BUTTON);
            recordImageView.setBackgroundResource(android.R.color.transparent);
            setFieldAccess();
        }
    }

    //For importSoundButton.
    public void importSoundButton(View view)
    {
        Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        fileIntent.setType("gagt/sdf");

        try
        {
            startActivityForResult(fileIntent, GV.PICK_FILE_REQUEST_CODE);
        }
        catch (ActivityNotFoundException e)
        {
            Log.e("tag", "No activity can handle picking a file. Showing alternatives.");
        }
    }

    //For listenButton.
    public void listenButton(View view)
    {
        startListening();
    }

    //For useButton.
    public void useButton(View view)
    {
        CheckBox soundCheckBox = (CheckBox) findViewById(R.id.soundCheckBox);
        Spinner soundSpinner = (Spinner) findViewById(R.id.soundSpinner);
        SeekBar volumeSeekBar = (SeekBar) findViewById(R.id.volumeSeekBar);

        //The variables that are used to create soundData.
        boolean sound = soundCheckBox.isChecked();
        String soundName = "";
        if(!(soundSpinner.getSelectedItemPosition() == -1))
        {
            if(soundSpinner.getSelectedItem().toString().equals("Default") ||
                    soundSpinner.getSelectedItem().toString().equals("Oletus") ||
                    soundSpinner.getSelectedItem().toString().equals("Standard"))
            {
                soundName = "Default";
            }
            else
            {
                soundName = soundSpinner.getSelectedItem().toString();
            }
        }
        int volume = volumeSeekBar.getProgress();

        //Create object of SoundData.
        soundData = new SoundData(sound,
                soundName,
                volume);

        //Initialize gson.
        Gson gson = new Gson();

        //Convert repeatData to String form.
        String soundDataString = gson.toJson(soundData);

        //Create new Intent.
        Intent returnIntent = new Intent();

        //Put the String version of repeatData to returnIntent under the name "result".
        returnIntent.putExtra("soundDataResult", soundDataString);

        //Prepare returnIntent to return something.
        setResult(RESULT_OK, returnIntent);

        //Reset mPlayer.
        stopListening();

        //Finish activity.
        finish();
    }

    //For deleteButton.
    public void deleteButton(View view)
    {
        Spinner soundSpinner = (Spinner) findViewById(R.id.soundSpinner);

        //Runs the deleting process only if soundSpinner isn't empty.
        if(soundSpinner.getSelectedItemPosition() != -1)
        {
            String soundPathString = Environment.getExternalStorageDirectory()+"/"+GV.PROGRAM_NAME+"/"+GV.SOUND_FOLDER_NAME+"/"+soundSpinner.getSelectedItem().toString();
            File soundPathFile = new File(soundPathString);

            //Runs if the file path exists.
            if(soundPathFile.exists())
            {
                //Runs if the deletion is successful (returns true).
                if (soundPathFile.delete())
                {
                    //Update the spinner with all sound names from the sound folder.
                    updateSpinner();

                    Log.v("File deleted", ""+soundPathFile);
                }
                else
                {
                    Log.v("File not deleted", ""+soundPathFile);
                }
            }
        }
    }

    //For backButton.
    public void backButton(View view)
    {
        //Reset mPlayer.
        stopListening();

        //Finish activity.
        finish();
    }
    //----------------------------------------------------------------------------------------------
}