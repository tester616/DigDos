package com.example.x.digdos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dig_dos_database",
            TABLE_USER = "user",
            TABLE_REMINDER = "reminder",
            COLUMN_USER_ID = "id",
            COLUMN_USER_USERNAME = "username",
            COLUMN_USER_PASSWORD = "password",
            COLUMN_REMINDER_ID = "id",
            COLUMN_REMINDER_TYPE = "type",
            COLUMN_REMINDER_NAME = "name",
            COLUMN_REMINDER_NOTE = "note",
            COLUMN_REMINDER_INITIAL_ACTIVATION = "initial_activation",
            COLUMN_REMINDER_IMAGE_DATA = "image_data",
            COLUMN_REMINDER_SOUND_DATA = "sound_data",
            COLUMN_REMINDER_REPEAT_DATA = "repeat_data",
            COLUMN_REMINDER_LATEST_ACTIVATION = "latest_activation",
            COLUMN_REMINDER_RUN_AMOUNT = "run_amount",
            CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_USERNAME + " VARCHAR NOT NULL, "
            + COLUMN_USER_PASSWORD + " VARCHAR NOT NULL"
            +");",
            CREATE_TABLE_REMINDER = "CREATE TABLE " + TABLE_REMINDER + "("
            + COLUMN_REMINDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_REMINDER_TYPE + " INTEGER NOT NULL, "
            + COLUMN_REMINDER_NAME + " VARCHAR NOT NULL, "
            + COLUMN_REMINDER_NOTE + " VARCHAR NOT NULL, "
            + COLUMN_REMINDER_INITIAL_ACTIVATION + " VARCHAR NOT NULL, "
            + COLUMN_REMINDER_IMAGE_DATA + " VARCHAR NOT NULL, "
            + COLUMN_REMINDER_SOUND_DATA + " VARCHAR NOT NULL, "
            + COLUMN_REMINDER_REPEAT_DATA + " VARCHAR NOT NULL, "
            + COLUMN_REMINDER_LATEST_ACTIVATION + " VARCHAR NOT NULL, "
            + COLUMN_REMINDER_RUN_AMOUNT + " INTEGER NOT NULL"
            +");";

    public DatabaseHelper(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_REMINDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);

        onCreate(db);

        Log.v("Database upgrade", "Version " + oldVersion + " to " + newVersion);
    }
}