package com.example.x.digdos;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by rusty on 8.7.2015.
 */
public class Support
{
    private Context ctx;

    //Empty constructor.
    public Support()
    {

    }

    //Constructor taking Context.
    public Support(Context ctx)
    {
        this.ctx = ctx;
    }

    //Get The currently chosen language.
    public Language getLanguage()
    {
        //Gets SharedPreferences.
        SharedPreferences prefs = ctx.getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Return language.
        return new Language(prefs.getInt("language", GV.LANGUAGE_ENGLISH));
    }

    //Get all files from a directory.
    public File[] getFiles(String DirectoryPath)
    {
        File f = new File(DirectoryPath);

        //Creates the directory if it doesn't exist.
        f.mkdirs();

        return f.listFiles();
    }

    //Get names of files in an ArrayList<String>.
    public ArrayList<String> getFileNames(File[] files)
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

    //Fixes rotation problems for an image you want to use.
    public Matrix getCorrectedBitmapRotationMatrix(String imagePath)
    {
        ExifInterface exif = null;
        int orientation = 0;

        try
        {
            exif = new ExifInterface(imagePath);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if(exif != null)
        {
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        }

        Matrix matrix = new Matrix();

        if(orientation == 1)
        {
            Log.v("fixBitmapRotationError() orientation", orientation + ", no correction was done.");
        }
        else if(orientation == 3)
        {
            matrix.postRotate(180);
            Log.v("fixBitmapRotationError() orientation", orientation+", 180 degree correction was done.");
        }
        else if(orientation == 6)
        {
            matrix.postRotate(90);
            Log.v("fixBitmapRotationError() orientation", orientation+", 90 degree correction was done.");
        }
        else if(orientation == 8)
        {
            matrix.postRotate(270);
            Log.v("fixBitmapRotationError() orientation", orientation+", 270 degree correction was done.");
        }
        else
        {
            Log.v("fixBitmapRotationError() orientation", orientation+", not one of the default 4, no correction was done.");
        }

        return matrix;
    }

    //Scales a bitmap to fit a smaller container.
    public Bitmap getScaledBitmap(String imagePath, int scaledWidth, int scaledHeight, Matrix matrix)
    {
        Bitmap B = null;

        //BitmapFactory.Options is used to only load a small version of the full image and save memory in hopes of stopping out of memory exception.
        BitmapFactory.Options options = new BitmapFactory.Options();

        //Makes decoding only bring bounds (like width, height) without the actual pixel data.
        options.inJustDecodeBounds = true;

        //Decode imagePath that was sent to this method.
        BitmapFactory.decodeFile(imagePath, options);

        //After decodeFile() is called you can get the width and height of the image you're working with.
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;

        Log.v("numbers", ""+imageWidth+" "+imageHeight+" "+scaledWidth+" "+scaledHeight);

        //Set the sample size for options.
        //Math.min returns the smaller value of the two sent in
        //A value over 1 here indicates the image is bigger than the ImageView for it, thus using this value to load a smaller image.
        //For example a value of 2 will load only half of the pixels in both width and height to the Bitmap for a quarter of the total pixels.
        options.inSampleSize = Math.min(imageWidth/scaledWidth, imageHeight/scaledHeight);

        //Make decoding not only decode bounds (like width, height).
        options.inJustDecodeBounds = false;

        //Tries to execute decodeFile() since the saved image name might not exist anymore and could cause an error.
        try
        {
            //B is the smaller version of the original image here.
            B = BitmapFactory.decodeFile(imagePath, options);
        }
        catch(Exception e)
        {
            Log.v("Error in fixBitmapRotationErrorAndGetScaledBitmap()", "Couldn't create bitmap with the following path: "+imagePath);
        }

        //Fixes rotation problem of the initial B, creating a new rotated one.
        if (B != null)
        {
            B = Bitmap.createBitmap(B, 0, 0, B.getWidth(), B.getHeight(), matrix, true);
        }

        return B;
    }
}
