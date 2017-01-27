package com.example.x.digdos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rusty on 1.2.2015.
 */

//Helper class for database creation, mainly containing various finalized strings in form of names and table creation statements ready to be used in SQL statements.
/*public class DatabaseHelper extends SQLiteOpenHelper
{
    //Version of the database.
    public static final int DATABASE_VERSION = 1;

    //Name for the whole database.
    public static final String DATABASE_NAME = "dig_dos_database";

    //Names for tables.
    public static final String TABLE_USER = "user",
            TABLE_REMINDER = "reminder";

    //Columns for "user".
    public static final String COLUMN_USER_ID = "id",
            COLUMN_USER_USERNAME = "username",
            COLUMN_USER_PASSWORD = "password";

    //Columns for "reminder".
    public static final String COLUMN_REMINDER_ID = "id",
            COLUMN_REMINDER_TYPE = "type",
            COLUMN_REMINDER_NAME = "name",
            COLUMN_REMINDER_NOTE = "note",
            COLUMN_REMINDER_INITIAL_ACTIVATION = "initial_activation",
            COLUMN_REMINDER_IMAGE_DATA = "image_data",
            COLUMN_REMINDER_SOUND_DATA = "sound_data",
            COLUMN_REMINDER_REPEAT_DATA = "repeat_data",
            COLUMN_REMINDER_LATEST_ACTIVATION = "latest_activation",
            COLUMN_REMINDER_RUN_AMOUNT = "run_amount";

    //Strings used for creating all the tables.
    public static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_USERNAME + " VARCHAR NOT NULL, "
            + COLUMN_USER_PASSWORD + " VARCHAR NOT NULL"
            +");";
    public static final String CREATE_TABLE_REMINDER = "CREATE TABLE " + TABLE_REMINDER + "("
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

    //Constructor
    public DatabaseHelper(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creates the tables if they don't already exist.
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_REMINDER);
    }

    //Call this to delete the current tables and to create new ones. Useful if the structure of a table has changed for example.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Clear old tables.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);

        //Create new ones.
        onCreate(db);

        //Display message
        Log.v("Database upgrade", "Version " + oldVersion + " to " + newVersion);
    }
}*/



//CODE WITH SWEDISH COMMENTS BELOW.



//Klass som hjälper med skapandet av en databas. Det viktiga är att ärva SQLiteOpenHelper.
public class DatabaseHelper extends SQLiteOpenHelper
{
    //En massa variabler som för det mesta representerar olika namn som används då databasen skapas.
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

    //Konstruktor. Skapar databsen.
    public DatabaseHelper(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Skapar tabellerna om de inte redan existerar.
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_REMINDER);
    }

    //Raderar databasens tabeller och skapar nya. Användbar om förändringar sker till exempelvis
    // namnet av en tabell.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Bort med gamla.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);

        //In med nya.
        onCreate(db);

        //Visa meddelande.
        Log.v("Database upgrade", "Version " + oldVersion + " to " + newVersion);
    }
}