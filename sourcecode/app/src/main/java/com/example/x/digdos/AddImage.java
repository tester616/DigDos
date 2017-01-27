package com.example.x.digdos;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/*public class AddImage extends Activity
{
    private Language lang;
    private ImageData imageData;
    private Bitmap B;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Removes the title of the Activity.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Defines which xml file is shown in this Activity and shows it.
        setContentView(R.layout.activity_add_image);

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();

        //Create listener for imageSpinner.
        setSpinnerListener();

        //Update the spinner with all image names from the image folder.
        updateSpinner();

        //Create imageData based on what is received.
        updateImageDataWithReceivedData();

        //Initialize various fields.
        initializeFields();

        //Determine which fields are usable and which aren't.
        setFieldAccess();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_image, menu);
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
    public void onPause()
    {
        super.onPause();

        ImageView previewImageView = (ImageView) findViewById(R.id.previewImageView);

        //Recycle and null B when leaving if it isn't null to avoid out of memory error.
        if(B != null)
        {
            B.recycle();
            B = null;
        }
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
        CheckBox imageCheckBox = (CheckBox) findViewById(R.id.imageCheckBox);
        Button takeImageButton = (Button) findViewById(R.id.takeImageButton);
        EditText imageNameEditText = (EditText) findViewById(R.id.imageNameEditText);
        Button importImageButton = (Button) findViewById(R.id.importImageButton);
        Button useButton = (Button) findViewById(R.id.useButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        //Update text.
        imageCheckBox.setText(lang.IMAGE_CHECK_BOX);
        takeImageButton.setText(lang.TAKE_IMAGE_BUTTON);
        imageNameEditText.setHint(lang.IMAGE_NAME_EDIT_TEXT);
        importImageButton.setText(lang.IMPORT_IMAGE_BUTTON);
        useButton.setText(lang.USE_BUTTON);
        deleteButton.setText(lang.DELETE_BUTTON);
        backButton.setText(lang.BACK_BUTTON);
    }

    //Determine which fields are usable and which aren't.
    private void setFieldAccess()
    {
        //Variable initialization.
        CheckBox imageCheckBox = (CheckBox) findViewById(R.id.imageCheckBox);
        Button takeImageButton = (Button) findViewById(R.id.takeImageButton);
        EditText imageNameEditText = (EditText) findViewById(R.id.imageNameEditText);
        Button importImageButton = (Button) findViewById(R.id.importImageButton);
        Spinner imageSpinner = (Spinner) findViewById(R.id.imageSpinner);
        ImageView previewImageView = (ImageView) findViewById(R.id.previewImageView);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);

        //Set enabled status of various things on the screen depending on certain selections.
        if(!imageCheckBox.isChecked())
        {
            takeImageButton.setEnabled(false);
            imageNameEditText.setEnabled(false);
            importImageButton.setEnabled(false);
            imageSpinner.setEnabled(false);
            previewImageView.setEnabled(false);
            deleteButton.setEnabled(false);
        }
        else
        {
            takeImageButton.setEnabled(true);
            imageNameEditText.setEnabled(true);
            importImageButton.setEnabled(true);
            imageSpinner.setEnabled(true);
            previewImageView.setEnabled(true);
            deleteButton.setEnabled(true);
        }
    }

    //Create imageData based on what is received.
    private void updateImageDataWithReceivedData()
    {
        //Get the extra data sent to this Intent in a Bundle.
        Bundle extras = getIntent().getExtras();

        //Get the String version of repeatData from the Bundle.
        String imageDataString = extras.getString("imageDataInitial");

        //Create new object of Gson.
        Gson gson = new Gson();

        //Transform repeatDataString to an actual object of the class.
        imageData = gson.fromJson(imageDataString, ImageData.class);
    }

    //Initialize various fields based on repeatData.
    private void initializeFields()
    {
        CheckBox imageCheckBox = (CheckBox) findViewById(R.id.imageCheckBox);
        Spinner imageSpinner = (Spinner) findViewById(R.id.imageSpinner);
        ArrayAdapter<String> imageSpinnerAdapter = (ArrayAdapter<String>) imageSpinner.getAdapter();

        //Set imageCheckBox.
        imageCheckBox.setChecked(imageData.getActive());

        //Set position of imageSpinner if imageCheckBox.imageName isn't null or "".
        if(imageData.getName() != null && !imageData.getName().equals(""))
        {
            int spinnerPosition = imageSpinnerAdapter.getPosition(imageData.getName());
            imageSpinner.setSelection(spinnerPosition);
        }
    }

    //Create listener for imageSpinner.
    private void setSpinnerListener()
    {
        Spinner imageSpinner = (Spinner) findViewById(R.id.imageSpinner);

        imageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                Spinner imageSpinner = (Spinner) findViewById(R.id.imageSpinner);
                ImageView previewImageView = (ImageView) findViewById(R.id.previewImageView);
                String imagePath = Environment.getExternalStorageDirectory()+"/"+GV.PROGRAM_NAME+"/"+GV.IMAGE_FOLDER_NAME+"/"+imageSpinner.getSelectedItem().toString();
                Support S = new Support(getApplicationContext());

                try
                {
                    previewImageView.setImageBitmap(
                            S.getScaledBitmap(
                                    imagePath,
                                    previewImageView.getWidth(),
                                    previewImageView.getHeight(),
                                    S.getCorrectedBitmapRotationMatrix(imagePath)
                            )
                    );
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {

            }
        });
    }

    //Update the spinner with all images from the folder the program uses.
    private void updateSpinner()
    {
        Spinner imageSpinner = (Spinner) findViewById(R.id.imageSpinner);
        ArrayList<String> imageSpinnerArray;
        ArrayAdapter<String> imageSpinnerAdapter;

        //Get the files in the image folder.
        File[] files = getFiles(Environment.getExternalStorageDirectory()+"/"+GV.PROGRAM_NAME+"/"+GV.IMAGE_FOLDER_NAME);

        //Makes sure length of files isn't 0 and create imageSpinnerArray accordingly.
        if(files.length != 0)
        {
            //Create imageSpinnerArray with the names from files.
            imageSpinnerArray =  new ArrayList<String>(getFileNames(files));
        }
        else
        {
            imageSpinnerArray =  new ArrayList<String>();
        }

        //Adapter for imageSpinner that uses imageSpinnerArray.
        imageSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, imageSpinnerArray);

        //Adjusting it to have the correct type of dropdown selection.
        imageSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Set ArrayAdapter for Spinner.
        imageSpinner.setAdapter(imageSpinnerAdapter);
    }

    //Get all files from a directory.
    private File[] getFiles(String DirectoryPath)
    {
        File f = new File(DirectoryPath);

        //Creates the directory if it doesn't exist.
        if(!f.exists())
        {
            f.mkdirs();
        }

        return f.listFiles();
    }

    //Get names of files in an ArrayList<String>.
    private ArrayList<String> getFileNames(File[] files)
    {
        ArrayList<String> filenameList = new ArrayList<String>();

        //Returns null if files has no content, otherwise loops through each of them, adding the name to filenameList.
        if (files.length == 0)
        {
            return null;
        }
        else
        {
            for(File file : files)
            {
                filenameList.add(file.getName());
            }
        }

        return filenameList;
    }

    //What happens when another Activity brings back a result to this one.
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK)
        {
            //If the result is from using the camera to take an image.
            if(requestCode == GV.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
            {
                EditText imageNameEditText = (EditText) findViewById(R.id.imageNameEditText);

                //Path where images will be stored.
                File imageFolder = new File(Environment.getExternalStorageDirectory()+"/"+GV.PROGRAM_NAME+"/"+GV.IMAGE_FOLDER_NAME);

                //Creates the folder if it doesn't already exist.
                //noinspection ResultOfMethodCallIgnored
                imageFolder.mkdirs();

                //Values for the query below.
                final String[] imageColumns = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
                final String imageOrderBy = MediaStore.Images.Media._ID+" DESC";

                //Cursor with answer for the query.
                Cursor imageCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, imageOrderBy);

                //Get path from imageCursor and use it to save the image to another location, then delete it from it's original location.
                if(imageCursor.moveToFirst())
                {
                    int id = imageCursor.getInt(imageCursor.getColumnIndex(MediaStore.Images.Media._ID));
                    String fullPath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));

                    //Move the image from one place to another.
                    File from = new File(fullPath);
                    String destinationPath = Environment.getExternalStorageDirectory()+"/"+GV.PROGRAM_NAME+"/"+GV.IMAGE_FOLDER_NAME+"/"+imageNameEditText.getText().toString()+".jpg";
                    File to = new File(destinationPath);

                    //noinspection ResultOfMethodCallIgnored
                    from.renameTo(to);

                    //Close the Cursor.
                    imageCursor.close();

                    //Delete the data (image in this case).
                    getContentResolver().delete(data.getData(), null, null);

                    //Update the spinner, now including the new image.
                    updateSpinner();
                }
            }
            else if(requestCode == GV.PICK_FILE_REQUEST_CODE)    //If the result is from picking a file from the phone.
            {
                String FilePath = data.getData().getPath();
                //FilePath is your file as a string.
            }
            else
            {
                Log.v("AddImage onActivityResult()", "Unexpected requestCode ("+requestCode+").");
            }
        }
        else if(resultCode == RESULT_CANCELED)
        {
            Log.v("AddImage onActivityResult()", "Nothing was returned.");
        }
        else
        {
            Log.v("AddImage onActivityResult()", "Unexpected resultCode ("+resultCode+").");
        }
    }

    //Check if this device has a camera.
    private boolean checkCameraHardware(Context context)
    {
        //Return true if device has camera, false otherwise.
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    //On click methods below.
    //----------------------------------------------------------------------------------------------
    //For imageCheckBox.
    public void imageCheckBox(View view)
    {
        //Determine which fields are usable and which aren't.
        setFieldAccess();
    }

    //For takeImageButton.
    public void takeImageButton(View view)
    {
        if(checkCameraHardware(getApplicationContext()))
        {
            //Create Intent to take a picture and return control to the calling application.
            Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            //Start imageIntent expecting a result.
            startActivityForResult(imageIntent, GV.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
        else
        {
            //Image capture failed.
            Toast.makeText(this, lang.NO_CAMERA, Toast.LENGTH_LONG).show();
        }
    }

    //For importImageButton.
    public void importImageButton(View view)
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

    //For useButton.
    public void useButton(View view)
    {
        CheckBox imageCheckBox = (CheckBox) findViewById(R.id.imageCheckBox);
        Spinner imageSpinner = (Spinner) findViewById(R.id.imageSpinner);

        //The variables that are used to create imageData.
        boolean image = imageCheckBox.isChecked();
        String imageName = "";
        if(!(imageSpinner.getSelectedItemPosition() == -1))
        {
            imageName = imageSpinner.getSelectedItem().toString();
        }

        //Create object of ImageData.
        imageData = new ImageData(image,
                imageName);

        Gson gson = new Gson();

        //Convert repeatData to String form.
        String imageDataString = gson.toJson(imageData);

        //Create new Intent.
        Intent returnIntent = new Intent();

        //Provide what you want to return here.
        returnIntent.putExtra("imageDataResult", imageDataString);

        //Prepare returnIntent to return a result.
        setResult(RESULT_OK,returnIntent);

        //Finish activity.
        finish();
    }

    //For deleteButton.
    public void deleteButton(View view)
    {
        Spinner imageSpinner = (Spinner) findViewById(R.id.imageSpinner);

        //Runs the deleting process only if imageSpinner isn't empty.
        if(imageSpinner.getSelectedItemPosition() != -1)
        {
            String imagePathString = Environment.getExternalStorageDirectory()+"/"+GV.PROGRAM_NAME+"/"+GV.IMAGE_FOLDER_NAME+"/"+imageSpinner.getSelectedItem().toString();
            File imagePathFile = new File(imagePathString);

            //Runs if the file path exists.
            if(imagePathFile.exists())
            {
                //Runs if the deletion is successful (returns true).
                if (imagePathFile.delete())
                {
                    //Update the spinner with all image names from the image folder.
                    updateSpinner();

                    Log.v("File deleted", ""+imagePathFile);
                }
                else
                {
                    Log.v("File not deleted", ""+imagePathFile);
                }
            }
        }
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
}*/



//CODE WITH SWEDISH COMMENTS BELOW.



public class AddImage extends Activity
{
    private Language lang;
    private ImageData imageData;
    private Bitmap B;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Removes the title of the Activity.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Defines which xml file is shown in this Activity and shows it.
        setContentView(R.layout.activity_add_image);

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();

        //Create listener for imageSpinner.
        setSpinnerListener();

        //Update the spinner with all image names from the image folder.
        updateSpinner();

        //Create imageData based on what is received.
        updateImageDataWithReceivedData();

        //Initialize various fields.
        initializeFields();

        //Determine which fields are usable and which aren't.
        setFieldAccess();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_image, menu);
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
    public void onPause()
    {
        super.onPause();

        ImageView previewImageView = (ImageView) findViewById(R.id.previewImageView);

        //Recycle and null B when leaving if it isn't null to avoid out of memory error.
        if(B != null)
        {
            B.recycle();
            B = null;
        }
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
        CheckBox imageCheckBox = (CheckBox) findViewById(R.id.imageCheckBox);
        Button takeImageButton = (Button) findViewById(R.id.takeImageButton);
        EditText imageNameEditText = (EditText) findViewById(R.id.imageNameEditText);
        Button importImageButton = (Button) findViewById(R.id.importImageButton);
        Button useButton = (Button) findViewById(R.id.useButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        //Update text.
        imageCheckBox.setText(lang.IMAGE_CHECK_BOX);
        takeImageButton.setText(lang.TAKE_IMAGE_BUTTON);
        imageNameEditText.setHint(lang.IMAGE_NAME_EDIT_TEXT);
        importImageButton.setText(lang.IMPORT_IMAGE_BUTTON);
        useButton.setText(lang.USE_BUTTON);
        deleteButton.setText(lang.DELETE_BUTTON);
        backButton.setText(lang.BACK_BUTTON);
    }

    //Determine which fields are usable and which aren't.
    private void setFieldAccess()
    {
        //Variable initialization.
        CheckBox imageCheckBox = (CheckBox) findViewById(R.id.imageCheckBox);
        Button takeImageButton = (Button) findViewById(R.id.takeImageButton);
        EditText imageNameEditText = (EditText) findViewById(R.id.imageNameEditText);
        Button importImageButton = (Button) findViewById(R.id.importImageButton);
        Spinner imageSpinner = (Spinner) findViewById(R.id.imageSpinner);
        ImageView previewImageView = (ImageView) findViewById(R.id.previewImageView);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);

        //Set enabled status of various things on the screen depending on certain selections.
        if(!imageCheckBox.isChecked())
        {
            takeImageButton.setEnabled(false);
            imageNameEditText.setEnabled(false);
            importImageButton.setEnabled(false);
            imageSpinner.setEnabled(false);
            previewImageView.setEnabled(false);
            deleteButton.setEnabled(false);
        }
        else
        {
            takeImageButton.setEnabled(true);
            imageNameEditText.setEnabled(true);
            importImageButton.setEnabled(true);
            imageSpinner.setEnabled(true);
            previewImageView.setEnabled(true);
            deleteButton.setEnabled(true);
        }
    }

    //Create imageData based on what is received.
    private void updateImageDataWithReceivedData()
    {
        //Get the extra data sent to this Intent in a Bundle.
        Bundle extras = getIntent().getExtras();

        //Get the String version of repeatData from the Bundle.
        String imageDataString = extras.getString("imageDataInitial");

        //Create new object of Gson.
        Gson gson = new Gson();

        //Transform repeatDataString to an actual object of the class.
        imageData = gson.fromJson(imageDataString, ImageData.class);
    }

    //Initialize various fields based on repeatData.
    private void initializeFields()
    {
        CheckBox imageCheckBox = (CheckBox) findViewById(R.id.imageCheckBox);
        Spinner imageSpinner = (Spinner) findViewById(R.id.imageSpinner);
        ArrayAdapter<String> imageSpinnerAdapter = (ArrayAdapter<String>) imageSpinner.getAdapter();

        //Set imageCheckBox.
        imageCheckBox.setChecked(imageData.getActive());

        //Set position of imageSpinner if imageCheckBox.imageName isn't null or "".
        if(imageData.getName() != null && !imageData.getName().equals(""))
        {
            int spinnerPosition = imageSpinnerAdapter.getPosition(imageData.getName());
            imageSpinner.setSelection(spinnerPosition);
        }
    }

    //Create listener for imageSpinner.
    private void setSpinnerListener()
    {
        Spinner imageSpinner = (Spinner) findViewById(R.id.imageSpinner);

        imageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                Spinner imageSpinner = (Spinner) findViewById(R.id.imageSpinner);
                ImageView previewImageView = (ImageView) findViewById(R.id.previewImageView);
                String imagePath = Environment.getExternalStorageDirectory()+"/"+GV.PROGRAM_NAME+"/"+GV.IMAGE_FOLDER_NAME+"/"+imageSpinner.getSelectedItem().toString();
                Support S = new Support(getApplicationContext());

                try
                {
                    previewImageView.setImageBitmap(
                            S.getScaledBitmap(
                                    imagePath,
                                    previewImageView.getWidth(),
                                    previewImageView.getHeight(),
                                    S.getCorrectedBitmapRotationMatrix(imagePath)
                            )
                    );
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {

            }
        });
    }

    //Update the spinner with all images from the folder the program uses.
    private void updateSpinner()
    {
        Spinner imageSpinner = (Spinner) findViewById(R.id.imageSpinner);
        ArrayList<String> imageSpinnerArray;
        ArrayAdapter<String> imageSpinnerAdapter;

        //Get the files in the image folder.
        File[] files = getFiles(Environment.getExternalStorageDirectory()+"/"+GV.PROGRAM_NAME+"/"+GV.IMAGE_FOLDER_NAME);

        //Makes sure length of files isn't 0 and create imageSpinnerArray accordingly.
        if(files == null || files.length == 0)
        {
            imageSpinnerArray =  new ArrayList<String>();
        }
        else
        {
            //Create imageSpinnerArray with the names from files.
            imageSpinnerArray =  new ArrayList<String>(getFileNames(files));
        }

        //Adapter for imageSpinner that uses imageSpinnerArray.
        imageSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, imageSpinnerArray);

        //Adjusting it to have the correct type of dropdown selection.
        imageSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Set ArrayAdapter for Spinner.
        imageSpinner.setAdapter(imageSpinnerAdapter);
    }

    //Get all files from a directory.
    private File[] getFiles(String DirectoryPath)
    {
        File f = new File(DirectoryPath);

        //Creates the directory if it doesn't exist.
        if(!f.exists())
        {
            f.mkdirs();
        }

        return f.listFiles();
    }

    //Get names of files in an ArrayList<String>.
    private ArrayList<String> getFileNames(File[] files)
    {
        ArrayList<String> filenameList = new ArrayList<String>();

        //Returns null if files has no content, otherwise loops through each of them, adding the name to filenameList.
        if (files.length == 0)
        {
            return null;
        }
        else
        {
            for(File file : files)
            {
                filenameList.add(file.getName());
            }
        }

        return filenameList;
    }

    //Denna metod kallas då en Intent som startades med startActivityForResult() är klar.
    //Här kan man med andra ord fritt göra något efteråt.
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //Först kontrolleras att något konkret data har returnerats, sedan om det har att göra med
        // rätt aktivitet (i detta fall med att ta en bild med kameran).
        if(resultCode == RESULT_OK)
        {
            if(requestCode == GV.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
            {
                //Innehåller namnet för bilden användaren gav före den togs.
                EditText imageNameEditText = (EditText) findViewById(R.id.imageNameEditText);

                //Teckensträng med namnet av stället dit programmet sparar tagna bilder.
                String imageFolderPath = Environment.getExternalStorageDirectory()+"/"+
                        GV.PROGRAM_NAME+"/"+
                        GV.IMAGE_FOLDER_NAME;

                File imageFolderFile = new File(imageFolderPath);

                //Skapar mappen/mapparna i enheten om den/de inte redan existerar från förut.
                imageFolderFile.mkdirs();

                //Eftersom bilden automatiskt sparas till fel ställe på enheten måste den flyttas.
                //För flyttandet behövs information om vart den sparats, vilket man får med en
                // query som liknar mycket de som används för att komma åt databasens data.
                final String[] imageColumns = {MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DATA};
                final String imageOrderBy = MediaStore.Images.Media._ID+" DESC";
                Cursor imageCursor = getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        imageColumns,
                        null,
                        null,
                        imageOrderBy
                );

                //Om Cursor har data från query används den här för att flytta bilden.
                if(imageCursor.moveToFirst())
                {
                    File from = new File(
                            imageCursor.getString(
                                    imageCursor.getColumnIndex(MediaStore.Images.Media.DATA)
                            )
                    );
                    File to = new File(imageFolderPath+"/"+
                                    imageNameEditText.getText().toString()+".jpg"
                    );

                    //Här sker själva "flyttandet" i form av att skapa bilden från "from" till "to".
                    from.renameTo(to);

                    imageCursor.close();

                    //Radera bilden från den ursprungliga mappen.
                    getContentResolver().delete(data.getData(), null, null);

                    //Resten av metoden är irrelevant för hur programmet sparar bilder.

                    //Update the spinner, now including the new image.
                    updateSpinner();
                }
            }
            else if(requestCode == GV.PICK_FILE_REQUEST_CODE)    //If the result is from picking a file from the phone.
            {
                String FilePath = data.getData().getPath();
                //FilePath is your file as a string.
            }
            else
            {
                Log.v("AddImage onActivityResult()", "Unexpected requestCode ("+requestCode+").");
            }
        }
        else if(resultCode == RESULT_CANCELED)
        {
            Log.v("AddImage onActivityResult()", "Nothing was returned.");
        }
        else
        {
            Log.v("AddImage onActivityResult()", "Unexpected resultCode ("+resultCode+").");
        }
    }

    //Check if this device has a camera.
    private boolean checkCameraHardware(Context context)
    {
        //Return true if device has camera, false otherwise.
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    //On click methods below.
    //----------------------------------------------------------------------------------------------
    //For imageCheckBox.
    public void imageCheckBox(View view)
    {
        //Determine which fields are usable and which aren't.
        setFieldAccess();
    }

    //Metod som aktiveras då användaren trycker på takeImageButton.
    public void takeImageButton(View view)
    {
        //Här kontrolleras att enheten har en kamera före öppnandet sker.
        if(checkCameraHardware(getApplicationContext()))
        {
            //Intent används för att öppna en ny Activity, i detta fall en med vilken man
            // kan ta bilder.
            Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            //Startar imageIntent i ett läge som förväntas returnera data då den stängs.
            //Ett heltal kopplas också ihop med den för identifieringsskäl.
            startActivityForResult(imageIntent, GV.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
        else
        {
            //Visar ett meddelande för användaren om enheten inte har en kamera.
            Toast.makeText(this, lang.NO_CAMERA, Toast.LENGTH_LONG).show();
        }
    }

    //For importImageButton.
    public void importImageButton(View view)
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

    //For useButton.
    public void useButton(View view)
    {
        CheckBox imageCheckBox = (CheckBox) findViewById(R.id.imageCheckBox);
        Spinner imageSpinner = (Spinner) findViewById(R.id.imageSpinner);

        //The variables that are used to create imageData.
        boolean image = imageCheckBox.isChecked();
        String imageName = "";
        if(!(imageSpinner.getSelectedItemPosition() == -1))
        {
            imageName = imageSpinner.getSelectedItem().toString();
        }

        //Create object of ImageData.
        imageData = new ImageData(image,
                imageName);

        Gson gson = new Gson();

        //Convert repeatData to String form.
        String imageDataString = gson.toJson(imageData);

        //Create new Intent.
        Intent returnIntent = new Intent();

        //Provide what you want to return here.
        returnIntent.putExtra("imageDataResult", imageDataString);

        //Prepare returnIntent to return a result.
        setResult(RESULT_OK,returnIntent);

        //Finish activity.
        finish();
    }

    //For deleteButton.
    public void deleteButton(View view)
    {
        Spinner imageSpinner = (Spinner) findViewById(R.id.imageSpinner);

        //Runs the deleting process only if imageSpinner isn't empty.
        if(imageSpinner.getSelectedItemPosition() != -1)
        {
            String imagePathString = Environment.getExternalStorageDirectory()+"/"+GV.PROGRAM_NAME+"/"+GV.IMAGE_FOLDER_NAME+"/"+imageSpinner.getSelectedItem().toString();
            File imagePathFile = new File(imagePathString);

            //Runs if the file path exists.
            if(imagePathFile.exists())
            {
                //Runs if the deletion is successful (returns true).
                if (imagePathFile.delete())
                {
                    //Update the spinner with all image names from the image folder.
                    updateSpinner();

                    Log.v("File deleted", ""+imagePathFile);
                }
                else
                {
                    Log.v("File not deleted", ""+imagePathFile);
                }
            }
        }
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