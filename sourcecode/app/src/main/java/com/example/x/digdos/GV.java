package com.example.x.digdos;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rusty on 2.4.2015.
 */

//Class for global variables.
public class GV
{
    public static final String IMAGE_FOLDER_NAME = "Images",
            PREFS_NAME = "DigDosPrefs",
            PROGRAM_NAME = "DigDos",
            SOUND_FOLDER_NAME = "Sounds";
    public static final int ADD_IMAGE_REQUEST_CODE = 200,
            ADD_REPEAT_REQUEST_CODE = 202,
            ADD_SOUND_REQUEST_CODE = 201,
            CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100,
            LANGUAGE_ENGLISH = 0,
            LANGUAGE_FINNISH = 1,
            LANGUAGE_SWEDISH = 2,
            PICK_FILE_REQUEST_CODE = 1,
            TYPE_ALL = 0,
            TYPE_MEDICINE = 1,
            TYPE_NOTIFICATION = 2;
}