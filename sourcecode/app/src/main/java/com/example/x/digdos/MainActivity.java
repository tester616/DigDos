package com.example.x.digdos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

//Class for the activity that always runs first when the program is started.
//From here you can either go create a new user or log in to an existing user.
/*public class MainActivity extends Activity
{
    private Language lang;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Removes the title of the Activity.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Defines which xml file is shown in this Activity.
        setContentView(R.layout.activity_main);

        //Only used to delete current tables from the database and recreating them empty.
        //recreateDatabase();

        //Only used to delete current SharedPreferences and starting empty again.
        //recreateSharedPreferences();

        //Performs a check to see if all SharedPreferences values the program uses are created.
        //If not, creates them with default values.
        checkSharedPreferences();

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();

        //Determine which fields are usable and which aren't.
        setFieldAccess();

        //Updates the repeating alarm, either activating it or deactivating it depending on settings.
        updateRepeatingAlarmState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.action_settings) {
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

        //Determine which fields are usable and which aren't.
        setFieldAccess();
    }

    //Creates object of Language with values for the currently selected language.
    @SuppressLint("CommitPrefEdits")
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
        Button newUserButton = (Button) findViewById(R.id.newUserButton);
        Button deleteUserButton = (Button) findViewById(R.id.deleteUserButton);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button settingsButton = (Button) findViewById(R.id.settingsButton);
        Button exitButton = (Button) findViewById(R.id.exitButton);

        //Update text.
        newUserButton.setText(lang.NEW_USER_BUTTON);
        deleteUserButton.setText(lang.DELETE_USER_BUTTON);
        loginButton.setText(lang.LOGIN_BUTTON);
        settingsButton.setText(lang.SETTINGS_BUTTON);
        exitButton.setText(lang.EXIT_BUTTON);
    }

    //Determine which fields are usable and which aren't.
    private void setFieldAccess()
    {
        Button newUserButton = (Button) findViewById(R.id.newUserButton);

        DatabaseQuery DQ = new DatabaseQuery(getApplicationContext());

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Locks newUserButton only if lockAccountCreation is true AND user table has content.
        if(prefs.getBoolean("lockAccountCreation", false) && DQ.getTableAmountOfRows(DatabaseHelper.TABLE_USER) != 0)
        {
            newUserButton.setEnabled(false);
        }
        else
        {
            newUserButton.setEnabled(true);
        }
    }

    //Run this if there isn't a database or if it's out of date.
    private void recreateDatabase()
    {
        DatabaseQuery DQ = new DatabaseQuery(getApplicationContext());
        DQ.upgradeDatabase();
    }

    //Only used to delete current SharedPreferences and starting empty again.
    private void recreateSharedPreferences()
    {
        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Opens editor for prefs.
        SharedPreferences.Editor editor = prefs.edit();

        //Clear all data from SharedPreferences.
        editor.clear();

        //Commit the changes.
        editor.commit();
    }

    //Check if any keys are missing in SharedPreferences and creates them if that's the case.
    private void checkSharedPreferences()
    {
        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Opens editor for prefs.
        SharedPreferences.Editor editor = prefs.edit();

        //Perform the checks here.
        //Runs only if the key checkForReminders isn't found in SharedPreferences.
        if(!prefs.contains("checkForReminders"))
        {
            //Create key checkForReminders with value 1.
            editor.putBoolean("checkForReminders", false);
        }

        //Run this version as long as testing and checking for alerts isn't reasonable to keep on at start.
        //editor.putBoolean("checkForReminders", false);

        //See above.
        if(!prefs.contains("showMedicines"))
        {
            editor.putBoolean("showMedicines", true);
        }

        //See above.
        if(!prefs.contains("showNotifications"))
        {
            editor.putBoolean("showNotifications", true);
        }

        //See above.
        if(!prefs.contains("allowAlertChangesInSettings"))
        {
            editor.putBoolean("allowAlertChangesInSettings", false);
        }

        //See above.
        if(!prefs.contains("lockAccountCreation"))
        {
            editor.putBoolean("lockAccountCreation", false);
        }

        //See above.
        if(!prefs.contains("question"))
        {
            editor.putString("question", "");
        }

        //See above.
        if(!prefs.contains("hint"))
        {
            editor.putString("hint", "");
        }

        //See above.
        if(!prefs.contains("answer"))
        {
            editor.putString("answer", "");
        }

        //See above.
        if(!prefs.contains("repeatAnswer"))
        {
            editor.putString("repeatAnswer", "");
        }

        //See above.
        if(!prefs.contains("useSafetyQuestion"))
        {
            editor.putBoolean("useSafetyQuestion", false);
        }

        //See above.
        if(!prefs.contains("language"))
        {
            editor.putInt("language", GV.LANGUAGE_ENGLISH);
        }

        //See above.
        if(!prefs.contains("typeMedicine"))
        {
            editor.putBoolean("typeMedicine", true);
        }

        //See above.
        if(!prefs.contains("typeNotification"))
        {
            editor.putBoolean("typeNotification", false);
        }

        //See above.
        if(!prefs.contains("modeNew"))
        {
            editor.putBoolean("modeNew", true);
        }

        //See above.
        if(!prefs.contains("modeEdit"))
        {
            editor.putBoolean("modeEdit", false);
        }

        //See above.
        if(!prefs.contains("snoozeTime"))
        {
            editor.putInt("snoozeTime", 10);
        }

        //Save the changes.
        editor.commit();
    }

    //Activate/deactivate the repeating alarm.
    private void updateRepeatingAlarmState()
    {
        //Creates object of AlarmManagerControls.
        AlarmManagerControls AMC = new AlarmManagerControls(getApplicationContext());

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //If checkForReminders is found and true, activates the repeating alarm, deactivates
        // otherwise.
        if(prefs.getBoolean("checkForReminders", false))
        {
            //Activate repeating alarm.
            AMC.setRepeatingAlarmState(true);
        }
        else
        {
            //Deactivate repeating alarm.
            AMC.setRepeatingAlarmState(false);
        }
    }

    //On click methods below.
    //----------------------------------------------------------------------------------------------
    //For newUserButton.
    public void newUserButton(View view)
    {
        //New activities are started by creating an Intent of the class and running startActivity on it.
        Intent newUserIntent = new Intent(this, NewUser.class);
        startActivity(newUserIntent);
    }

    //For deleteUserButton.
    public void deleteUserButton(View view)
    {
        //Start another activity.
        Intent deleteUserIntent = new Intent(this, DeleteUser.class);
        startActivity(deleteUserIntent);
    }

    //For loginButton.
    public void loginButton(View view)
    {
        //Start another activity.
        Intent loginIntent = new Intent(this, Login.class);
        startActivity(loginIntent);
    }

    //For settingsButton.
    public void settingsButton(View view)
    {
        //Start another activity.
        Intent settingsIntent = new Intent(this, PublicSettings.class);
        startActivity(settingsIntent);
    }

    //For exitButton.
    public void exitButton(View view)
    {
        //Exit program.
        finish();
    }
    //----------------------------------------------------------------------------------------------
}*/







public class MainActivity extends Activity
{
    private Language lang;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Removes the title of the Activity.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Defines which xml file is shown in this Activity.
        setContentView(R.layout.activity_main);

        //Only used to delete current tables from the database and recreating them empty.
        //recreateDatabase();

        //Only used to delete current SharedPreferences and starting empty again.
        //recreateSharedPreferences();

        //Performs a check to see if all SharedPreferences values the program uses are created.
        //If not, creates them with default values.
        checkSharedPreferences();

        //Set language.
        setLanguage();

        //Update various fields with current language.
        createFieldsWithCurrentLanguage();

        //Determine which fields are usable and which aren't.
        setFieldAccess();

        //Updates the repeating alarm, either activating it or deactivating it depending on settings.
        updateRepeatingAlarmState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.action_settings) {
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

        //Determine which fields are usable and which aren't.
        setFieldAccess();
    }

    //Creates object of Language with values for the currently selected language.
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
        Button newUserButton = (Button) findViewById(R.id.newUserButton);
        Button deleteUserButton = (Button) findViewById(R.id.deleteUserButton);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button settingsButton = (Button) findViewById(R.id.settingsButton);
        Button exitButton = (Button) findViewById(R.id.exitButton);

        //Update text.
        newUserButton.setText(lang.NEW_USER_BUTTON);
        deleteUserButton.setText(lang.DELETE_USER_BUTTON);
        loginButton.setText(lang.LOGIN_BUTTON);
        settingsButton.setText(lang.SETTINGS_BUTTON);
        exitButton.setText(lang.EXIT_BUTTON);
    }

    //Determine which fields are usable and which aren't.
    private void setFieldAccess()
    {
        Button newUserButton = (Button) findViewById(R.id.newUserButton);

        DatabaseQuery DQ = new DatabaseQuery(getApplicationContext());

        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Locks newUserButton only if lockAccountCreation is true AND user table has content.
        if(prefs.getBoolean("lockAccountCreation", false) && DQ.getTableAmountOfRows(DatabaseHelper.TABLE_USER) != 0)
        {
            newUserButton.setEnabled(false);
        }
        else
        {
            newUserButton.setEnabled(true);
        }
    }

    //Run this if there isn't a database or if it's out of date.
    private void recreateDatabase()
    {
        DatabaseQuery DQ = new DatabaseQuery(getApplicationContext());
        DQ.upgradeDatabase();
    }

    //Only used to delete current SharedPreferences and starting empty again.
    private void recreateSharedPreferences()
    {
        //Gets SharedPreferences.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //Opens editor for prefs.
        SharedPreferences.Editor editor = prefs.edit();

        //Clear all data from SharedPreferences.
        editor.clear();

        //Commit the changes.
        editor.commit();
    }

    //Kontrollerar att alla SharedPreferences variabler programmet använder existerar.
    private void checkSharedPreferences()
    {
        //Skapar SharedPreferences med privat åtkomst.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //För att göra ändringar till SharedPreferences används en Editor.
        SharedPreferences.Editor editor = prefs.edit();

        //Skapar en boolean som heter showMedicines och är true om inte prefs redan har en variabel
        // med namnet showMedicines.
        if(!prefs.contains("showMedicines"))
        {
            editor.putBoolean("showMedicines", true);
        }

        //Resten av kontrollerna sker nedan med samma logik som för showMedicines.

        //Perform the checks here.
        //Runs only if the key checkForReminders isn't found in SharedPreferences.
        if(!prefs.contains("checkForReminders"))
        {
            //Create key checkForReminders with value 1.
            editor.putBoolean("checkForReminders", false);
        }

        //Run this version as long as testing and checking for alerts isn't reasonable to keep on at start.
        //editor.putBoolean("checkForReminders", false);

        if(!prefs.contains("showNotifications"))
        {
            editor.putBoolean("showNotifications", true);
        }

        if(!prefs.contains("allowAlertChangesInSettings"))
        {
            editor.putBoolean("allowAlertChangesInSettings", false);
        }

        if(!prefs.contains("lockAccountCreation"))
        {
            editor.putBoolean("lockAccountCreation", false);
        }

        if(!prefs.contains("question"))
        {
            editor.putString("question", "");
        }

        if(!prefs.contains("hint"))
        {
            editor.putString("hint", "");
        }

        if(!prefs.contains("answer"))
        {
            editor.putString("answer", "");
        }

        if(!prefs.contains("repeatAnswer"))
        {
            editor.putString("repeatAnswer", "");
        }

        if(!prefs.contains("useSafetyQuestion"))
        {
            editor.putBoolean("useSafetyQuestion", false);
        }

        if(!prefs.contains("language"))
        {
            editor.putInt("language", GV.LANGUAGE_ENGLISH);
        }

        if(!prefs.contains("typeMedicine"))
        {
            editor.putBoolean("typeMedicine", true);
        }

        if(!prefs.contains("typeNotification"))
        {
            editor.putBoolean("typeNotification", false);
        }

        if(!prefs.contains("modeNew"))
        {
            editor.putBoolean("modeNew", true);
        }

        if(!prefs.contains("modeEdit"))
        {
            editor.putBoolean("modeEdit", false);
        }

        if(!prefs.contains("snoozeTime"))
        {
            editor.putInt("snoozeTime", 10);
        }

        //Efter alla kontroller sparas möjliga ändringar nedan.

        //Sparar ändringarna till prefs med hjälp av commit() från editor.
        editor.commit();
    }

    //Metod som kan ändra på om programmet gör regelbundna kontroller till databasen.
    private void updateRepeatingAlarmState()
    {
        //Klassen AlarmManagerControls har inget att göra med att hämta data från SharedPreferences,
        // men behöver data från den i form av en Boolean för metoden setRepeatingAlarmState().
        AlarmManagerControls AMC = new AlarmManagerControls(getApplicationContext());

        //Samma logik för skapandet av SharedPreferences, men här sparas inte ny data, vilket
        // betyder att en editor inte behövs.
        SharedPreferences prefs = getSharedPreferences(GV.PREFS_NAME, Context.MODE_PRIVATE);

        //För att få ut en boolean från prefs används getBoolean(), som här kontrollerar om en
        // boolean med namnet checkForReminders existerar i prefs och returnerar värdet om svaret
        // är ja. Om den inte existerar returneras värdet efter namnet, alltså false.
        AMC.setRepeatingAlarmState(prefs.getBoolean("checkForReminders", false));
    }

    //On click methods below.
    //----------------------------------------------------------------------------------------------
    //For newUserButton.
    public void newUserButton(View view)
    {
        //New activities are started by creating an Intent of the class and running startActivity on it.
        Intent newUserIntent = new Intent(this, NewUser.class);
        startActivity(newUserIntent);
    }

    //For deleteUserButton.
    public void deleteUserButton(View view)
    {
        //Start another activity.
        Intent deleteUserIntent = new Intent(this, DeleteUser.class);
        startActivity(deleteUserIntent);
    }

    //For loginButton.
    public void loginButton(View view)
    {
        //Start another activity.
        Intent loginIntent = new Intent(this, Login.class);
        startActivity(loginIntent);
    }

    //For settingsButton.
    public void settingsButton(View view)
    {
        //Start another activity.
        Intent settingsIntent = new Intent(this, PublicSettings.class);
        startActivity(settingsIntent);
    }

    //For exitButton.
    public void exitButton(View view)
    {
        //Exit program.
        finish();
    }
    //----------------------------------------------------------------------------------------------
}









/*
Some general information about what different variables mean when used in the SQL methods.
different queries returning cursors
public Cursor query (String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
table 	The table name to compile the query against.
columns 	A list of which columns to return. Passing null will return all columns, which is discouraged to prevent reading data from storage that isn't going to be used.
selection 	A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
selectionArgs 	You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
groupBy 	A filter declaring how to group rows, formatted as an SQL GROUP BY clause (excluding the GROUP BY itself). Passing null will cause the rows to not be grouped.
having 	A filter declare which row groups to include in the cursor, if row grouping is being used, formatted as an SQL HAVING clause (excluding the HAVING itself). Passing null will cause all row groups to be included, and is required when row grouping is not being used.
orderBy 	How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort order, which may be unordered.
limit 	Limits the number of rows returned by the query, formatted as LIMIT clause. Passing null denotes no LIMIT clause.

public Cursor query (boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit, CancellationSignal cancellationSignal)
distinct 	true if you want each row to be unique, false otherwise.
cancellationSignal 	A signal to cancel the operation in progress, or null if none. If the operation is canceled, then OperationCanceledException will be thrown when the query is executed.

public Cursor query (String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
*/

/*
//Här skapas ett objekt av DatabasHjälpKlass med namnet DHK genom att kalla på konstruktorn och
// sända in ett objekt av Context med metoden getApplicationContext().
//För att kunna kalla på getApplicationContext() måste klassen ha tillgång till Activity (extends
// Activity), annars måste Context tas in från en klass som har det med exempelvis en
// konstruktor likt klassen jag här skapar ett objekt av.
DatabasHjälpKlass DHK = new DatabasHjälpKlass(getApplicationContext());

//Genom att kalla på metoden getWritableDatabase() från SQLiteOpenHelper (som objektet av klassen
// DatabasHjälpKlass med namnet DHK förlänger genom extends SQLiteOpenHelper)
// kan man skapa ett objekt av SQLiteDatabase som duger, som namnet redan föreslår, till att ändra
// på databasen antingen genom att skapa, radera eller ändra på existerande data.
//Man kan använda denna typs databas för läsande och tvärtom med tanke på getReadableDatabase(),
// men för klarhetens skull håller jag mig till de operationer namnen tyder på.
SQLiteDatabase SQLWDB = DHK.getWritableDatabase();

//Skapandet av en teckensträng med namnet tabell som ges värdet personregister, samma som tabellens
// namn jag använder i DatabasHjälpKlass.
String tabell = "personregister";

//ContentValues är en klass där man kan mata in namn på kolumner och para dem med värden som sedan
// kan användas för operationer inom databasen.
//Här skapas ett nytt objekt av klassen med namnet CV.
ContentValues CV = new ContentValues();

//inmatningen i ContentValues sker med metoden put().
//Först ges kolumnens namn och därefter det önskade värdet.
//Märk att namnen på kolumnerna motsvarar de som används i DatabasHjälpKlass för tabellen
// personregister och att id aldrig ges eftersom den hanteras automatiskt.
CV.put("förnamn", "Jakob");
CV.put("ålder", 26);

//Metoden för skapandet av en ny rad data till en tabell heter insert().
//Den tar in namnet på tabellen, en teckensträng för avancerad användning som här kan ignoreras med
// null och ContentValues.
//Som man kan se, behövs inte noggrann inmatning av exakta SQL satser. Allt det tas automatiskt
// hand om av SQLiteDatabase bara man ger metoderna korrekta parametrar.
SQLWDB.insert(tabell, null, CV);

//Skapandet av en teckensträng där jag väljer kolumnen (id) från vilken jag söker efter rader som
// skall uppdateras. Ett frågetecken tyder på ett värde jag söker efter.
String kolumnval = "id LIKE ?";

//Skapandet av en teckensträngräcka (med andra ord kan vara fler än en i samma) där jag ger det
// värde jag söker efter. Kan vara flera, bara det stämmer överens med antalet frågetecken.
//Genom att se på kombinationen av kolumnval och kolumnvalvärden ser man att jag söker efter en rad
// där kolumnen id har värdet 1.
String[] kolumnvalvärden = {"1"};

//Samma princip som med insert(), men nu handlar det om att uppdatera redan existerande rader.
//Man kan ge in samma tabellnamn och ContentValues för exemplets skull, men i riktig kod finns det
// knappast någon orsak att först skapa en rad med data och sedan uppdatera den med samma data.
//Metoden tar också in radval och radvalvärden som förklarades ovan. Om man ger null blir varje rad
// påverkad.
SQLWDB.update(tabell, CV, kolumnval, kolumnvalvärden);

//För att radera data från databasen finns metoden delete(). Den liknar update() gällande inmatning,
// men saknar ContentValues eftersom all data från en eller flera rader raderas.
SQLWDB.delete(tabell, kolumnval, kolumnvalvärden);

//Samma sak som för SQLWDB, men nu med getReadableDatabase() för att läsa data.
SQLiteDatabase SQLRDB = DHK.getReadableDatabase();

//För att i sin tur hämta data från databasen används query(). I exemplet är flera av värdena null
// och behövs endast om man vill nå specifik data noggrannare inom databasen.
//Det första null värdet representerar t.ex. de kolumner man vill returnera per rad av data och ges
// i form av String[] (null returnerar alla kolumner).
//Märk att query() returnerar data i form av en Cursor som här namnges C. Därifrån kan man sedan nå
// den data man vill.
Cursor C = SQLRDB.query(tabell, null, kolumnval, kolumnvalvärden, null, null, null);

//Tänk dig Cursor som en lista på rader query() returnerade från databasen. Denna lista pekar på en
// rad åt gången som data sedan kan tas ut från och börjar alltid från 0 som representerar
// en tom "rad" strax före den första som returnerades. Genom att kalla moveToNext() får man C att
// peka på nästa rad data som i detta fall är den första raden data (1) som returnerades.
C.moveToNext();

//När en Cursor pekar på rätt rad av data gäller det att kalla t.ex. getString() eller getInt() och
// skicka positionen med som ett heltal.
//Det viktiga är att positionen du skickar innehåller den typ av data som metoden försöker
// returnera (första kolumnen är position 0 osv.).
//I detta exempel skulle query() inte returnera en enda rad data på grund av att raden data med id
// 1 (som query() sökte efter) redan raderades under föregående rad av kod som kallade delete().
//Om en rad av data däremot hade returnerats, kunde man använda denna kod för att spara den i två
// heltal och en teckensträng för senare användning.
int returneradIdFrånPersonregister = C.getInt(0);
String returneradFörnamnFrånPersonregister = C.getString(1);
int returneradÅlderFrånPersonregister = C.getInt(2);

//Stäng C då den inte längre behövs.
C.close();




//Först gäller det att skapa ett objekt av klassen.
//SP är namnet på objektet och kan variera beroende på vad man vill ha som namn.
//mittSharedPreferencesFilnamn är namnet på filen dit datat sparas.
//Context.MODE_PRIVATE bestämmer att utomstående applikationer inte har tillgång till filens data.
//Raden är delad på grund av längden.
SharedPreferences SP = getSharedPreferences("mittSharedPreferencesFilnamn",
        Context.MODE_PRIVATE);

//Den första raden kod skapar ett heltal inom programmet och ger den ett värde som tas från SP med
// getInt()
//mittSparadeHeltalsnamn är namnet som heltalet använder i filen och kan variera
// likt mittSharedPreferencesFilnamn.
//0 är värdet som returneras om filen inte har något heltal som identifieras med namnet
// mittSparadeHeltalsnamn.
//Andra raden kod gör samma sak, men nu för en teckensträng.
//heltalFrånSP och teckensträngFrånSP kan efter dessa rader användas normalt av programmet.
int heltalFrånSP = SP.getInt("mittSparadeHeltalsnamn", 0);
String teckensträngFrånSP = SP.getString("minSparadeTeckensträng", "Tomt");

//För att spara data till filen behövs en Editor som skapas enligt koden nedan.
//E är endast ett namn likt SP.
SharedPreferences.Editor E = SP.edit();

//Exempel på hur man redogör data för att sparas till filen (första raden igen ett heltal, andra en
// teckensträng).
//mittSparadeHeltalsnamn är namnet som getInt() måste ge för att komma åt värdet.
//5 är värdet på heltalet som sparas.
E.putInt("mittSparadeHeltalsnamn", 5);
E.putString("minSparadeTeckensträng", "Test");

//commit() sparar alla förberedda förändringar till filen (i detta fall från putInt() och
// putString() i raderna ovan).
E.commit();
*/

/*
//Skapa objekt av Gson med namnet gson.
Gson gson = new Gson();

//Skapa objekt av ImageData och ge värden via en konstruktor.
ImageData bildData = new ImageData(true, "testbild.jpg");

//Här kallas toJson() på bildData, vilket förändrar det till en teckensträng med format som JSON
// förstår. Resultatet sparas med namnet bildDataSomTeckensträng.
String bildDataSomTeckensträng = gson.toJson(bildData);

//Denna teckensträng kan nu fritt användas och sparas till databasen. Eftersom databasoperationerna
// redan behandlades tidigare visar jag här endast hur man kan konvertera teckensträngen tillbaka
// till ett objekt av klassen ImageData genom att använda fromJson().
ImageData bildDataFrånTeckensträng = gson.fromJson(bildDataSomTeckensträng, ImageData.class);
*/