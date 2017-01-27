package com.example.x.digdos;

import android.util.Log;


public class Language
{
    //Language.
    public int LANGUAGE;

    //Buttons.
    public String ADD_BUTTON = "",
            ADD_IMAGE_BUTTON = "",
            ADD_REPEAT_BUTTON = "",
            ADD_SOUND_BUTTON = "",
            BACK_BUTTON = "",
            CLEAR_ALERT_BUTTON = "",
            CREATE_BUTTON = "",
            DELETE_ALL_USERS_BUTTON = "",
            DELETE_BUTTON = "",
            DELETE_USER_BUTTON = "",
            EDIT_MEDICINE_BUTTON = "",
            EDIT_NOTIFICATION_BUTTON = "",
            EXIT_BUTTON = "",
            IMPORT_IMAGE_BUTTON = "",
            IMPORT_SOUND_BUTTON= "",
            LISTEN_BUTTON = "",
            LOG_IN_BUTTON = "",
            LOG_OUT_BUTTON = "",
            LOGIN_BUTTON = "",
            NEW_MEDICINE_BUTTON = "",
            NEW_NOTIFICATION_BUTTON = "",
            NEW_USER_BUTTON = "",
            RECORD_OFF_BUTTON = "",
            RECORD_ON_BUTTON = "",
            REMINDERS_BUTTON = "",
            REMOVE_BUTTON = "",
            SAFETY_QUESTION_BUTTON = "",
            SAVE_BUTTON = "",
            SETTINGS_BUTTON = "",
            TAKE_IMAGE_BUTTON = "",
            USE_BUTTON = "",
            USER_SETTINGS_BUTTON = "",

            //CheckBoxes.
            ALLOW_ALERT_CHANGES_IN_SETTINGS_CHECK_BOX = "",
            ACTIVATE_REMINDERS_CHECK_BOX = "",
            IMAGE_CHECK_BOX = "",
            LOCK_ACCOUNT_CREATION_CHECK_BOX = "",
            REPEAT_CHECK_BOX = "",
            SHOW_MEDICINES_CHECK_BOX = "",
            SHOW_NOTIFICATIONS_CHECK_BOX = "",
            SOUND_CHECK_BOX = "",
            USE_SAFETY_QUESTION_CHECK_BOX = "",

            //EditTexts
            ANSWER_EDIT_TEXT = "",
            CURRENT_ANSWER_EDIT_TEXT = "",
            HINT_EDIT_TEXT = "",
            IMAGE_NAME_EDIT_TEXT = "",
            LOCATION_EDIT_TEXT = "",
            NAME_EDIT_TEXT = "",
            NOTE_EDIT_TEXT = "",
            PASSWORD_EDIT_TEXT = "",
            QUESTION_EDIT_TEXT = "",
            REPEAT_ANSWER_EDIT_TEXT = "",
            SOUND_NAME_EDIT_TEXT = "",
            USERNAME_EDIT_TEXT = "",

            //TextViews.
            ACCESS_TEXT_VIEW = "",
            ALERT_DATE_AND_TIME_TEXT_VIEW = "",
            ALERT_MEDICINE_TEXT_VIEW = "",
            ALERT_NOTIFICATION_TEXT_VIEW = "",
            DATE_TEXT_VIEW = "",
            DAY_TEXT_VIEW = "",
            HINT_TEXT_VIEW = "",
            LANGUAGE_TEXT_VIEW = "",
            LOCATION_TEXT_VIEW = "",
            MODE_TEXT_VIEW = "",
            MONTH_TEXT_VIEW = "",
            NAME_TEXT_VIEW = "",
            NOTE_TEXT_VIEW = "",
            QUESTION_TEXT_VIEW = "",
            RECORD_OFF_TEXT_VIEW = "",
            RECORD_ON_TEXT_VIEW = "",
            REMINDER_VISIBILITY_TEXT_VIEW = "",
            REPEAT_END_TEXT_VIEW = "",
            REPEAT_MODE_TEXT_VIEW = "",
            REPEAT_PASSWORD_TEXT_VIEW = "",
            REPEAT_TEXT_VIEW = "",
            SNOOZE_MINUTES_TEXT_VIEW = "",
            SNOOZE_TIME_TEXT_VIEW = "",
            TEXT_TEXT_VIEW = "",
            TIME_TEXT_VIEW = "",
            TITLE_TEXT_VIEW = "",
            TYPE_TEXT_VIEW = "",
            VOLUME_TEXT_VIEW = "",
            YEAR_TEXT_VIEW = "",

            //Spinners.
            REPEAT_SPINNER_0 = "",
            REPEAT_SPINNER_1 = "",
            REPEAT_SPINNER_2 = "",
            REPEAT_SPINNER_3 = "",
            REPEAT_SPINNER_4 = "",
            REPEAT_SPINNER_5 = "",
            SOUND_SPINNER_DEFAULT = "",

            //Messages.
            ACCOUNT_CREATION_LOCKED = "",
            ALL_USERS_DELETED = "",
            DELETE_FAILED_WRONG_PASSWORD = "",
            LOGIN_FAILED_NO_SUCH_USER = "",
            LOGIN_FAILED_WRONG_PASSWORD = "",
            LOGIN_SUCCESSFUL = "",
            MEDICINE_CREATED = "",
            MEDICINE_DELETED = "",
            MEDICINE_INFORMATION_EDITED = "",
            NO_CAMERA = "",
            NOTIFICATION_CREATED = "",
            NOTIFICATION_DELETED = "",
            NOTIFICATION_INFORMATION_EDITED = "",
            SAFETY_QUESTION_ANSWERS_DO_NOT_MATCH = "",
            SAFETY_QUESTION_WRONG_ANSWER = "",
            SAFETY_QUESTION_WRONG_PREVIOUS_ANSWER = "",
            SETTINGS_SAVED = "",
            USER_CREATED = "",
            USER_CREATION_PASSWORDS_DO_NOT_MATCH = "",
            USER_CREATION_USERNAME_ALREADY_EXISTS = "",
            USER_DELETED = "",

            //Dialogs.
            DELETE_ALL_USERS_DIALOG_ALERT = "",
            DELETE_USER_DIALOG_ALERT = "",
            DELETE_USER_DIALOG_NEGATIVE = "",
            DELETE_USER_DIALOG_POSITIVE = "",

            //RadioButtons
            CUSTOM_RADIO_BUTTON = "",
            EDIT_RADIO_BUTTON = "",
            MEDICINE_RADIO_BUTTON = "",
            NEW_RADIO_BUTTON = "",
            NOTIFICATION_RADIO_BUTTON = "",
            PERIODICAL_RADIO_BUTTON = "";

    public Language()
    {

    }

    public Language(int language)
    {
        LANGUAGE = language;

        if(LANGUAGE == GV.LANGUAGE_ENGLISH)
        {
            //Buttons.
            ADD_BUTTON = "Add";
            ADD_IMAGE_BUTTON = "Add image";
            ADD_REPEAT_BUTTON = "Add repeat";
            ADD_SOUND_BUTTON = "Add sound";
            BACK_BUTTON = "Back";
            CLEAR_ALERT_BUTTON = "Clear alert";
            CREATE_BUTTON = "Create";
            DELETE_ALL_USERS_BUTTON = "Delete all users";
            DELETE_BUTTON = "Delete";
            DELETE_USER_BUTTON = "Delete user";
            EDIT_MEDICINE_BUTTON = "Edit medicine";
            EDIT_NOTIFICATION_BUTTON = "Edit notification";
            EXIT_BUTTON = "Exit";
            IMPORT_IMAGE_BUTTON = "Import image";
            IMPORT_SOUND_BUTTON= "Import sound";
            LISTEN_BUTTON = "Listen";
            LOG_IN_BUTTON = "Log in";
            LOG_OUT_BUTTON = "Log out";
            LOGIN_BUTTON = "Login";
            NEW_MEDICINE_BUTTON = "New medicine";
            NEW_NOTIFICATION_BUTTON = "New notification";
            NEW_USER_BUTTON = "New user";
            RECORD_OFF_BUTTON = "Record";
            RECORD_ON_BUTTON = "Stop recording";
            REMINDERS_BUTTON = "Reminders";
            REMOVE_BUTTON = "Remove";
            SAFETY_QUESTION_BUTTON = "Safety question";
            SAVE_BUTTON = "Save";
            SETTINGS_BUTTON = "Settings";
            TAKE_IMAGE_BUTTON = "Take image";
            USE_BUTTON = "Use";
            USER_SETTINGS_BUTTON = "User settings";

            //CheckBoxes.
            ALLOW_ALERT_CHANGES_IN_SETTINGS_CHECK_BOX = "Allow anyone to change reminder visibility";
            ACTIVATE_REMINDERS_CHECK_BOX = "Activate reminders";
            IMAGE_CHECK_BOX = "Image on";
            LOCK_ACCOUNT_CREATION_CHECK_BOX = "Lock account creation";
            REPEAT_CHECK_BOX = "Repeat on";
            SHOW_MEDICINES_CHECK_BOX = "Show medicines";
            SHOW_NOTIFICATIONS_CHECK_BOX = "Show notifications";
            SOUND_CHECK_BOX = "Sound on";
            USE_SAFETY_QUESTION_CHECK_BOX = "Use safety question";

            //EditTexts
            ANSWER_EDIT_TEXT = "Answer";
            CURRENT_ANSWER_EDIT_TEXT = "Current answer";
            HINT_EDIT_TEXT = "Hint";
            IMAGE_NAME_EDIT_TEXT = "Taken image filename";
            LOCATION_EDIT_TEXT = "Location";
            NAME_EDIT_TEXT = "Name";
            NOTE_EDIT_TEXT = "Note";
            PASSWORD_EDIT_TEXT = "Password";
            QUESTION_EDIT_TEXT = "Question";
            REPEAT_ANSWER_EDIT_TEXT = "Repeat answer";
            SOUND_NAME_EDIT_TEXT = "Recorded sound filename";
            USERNAME_EDIT_TEXT = "Username";

            //TextViews.
            ACCESS_TEXT_VIEW = "Access";
            ALERT_DATE_AND_TIME_TEXT_VIEW = "Alert date and time";
            ALERT_MEDICINE_TEXT_VIEW = "New medicine";
            ALERT_NOTIFICATION_TEXT_VIEW = "New notification";
            DATE_TEXT_VIEW = "Date:";
            DAY_TEXT_VIEW = "Day:";
            HINT_TEXT_VIEW = "Hint";
            LANGUAGE_TEXT_VIEW = "Language";
            LOCATION_TEXT_VIEW = "Location:";
            MODE_TEXT_VIEW = "Mode";
            MONTH_TEXT_VIEW = "Month:";
            NAME_TEXT_VIEW = "Name:";
            NOTE_TEXT_VIEW = "Note:";
            QUESTION_TEXT_VIEW = "Question";
            RECORD_OFF_TEXT_VIEW = "";
            RECORD_ON_TEXT_VIEW = "Recording...";
            REMINDER_VISIBILITY_TEXT_VIEW = "Reminder visibility";
            REPEAT_END_TEXT_VIEW = "Until";
            REPEAT_MODE_TEXT_VIEW = "Repeat mode";
            REPEAT_PASSWORD_TEXT_VIEW = "Repeat password";
            REPEAT_TEXT_VIEW = "Repeat every";
            SNOOZE_MINUTES_TEXT_VIEW = "(in minutes)";
            SNOOZE_TIME_TEXT_VIEW = "Snooze time";
            TEXT_TEXT_VIEW = "Text:";
            TIME_TEXT_VIEW = "Time:";
            TITLE_TEXT_VIEW = "Title:";
            TYPE_TEXT_VIEW = "Type";
            VOLUME_TEXT_VIEW = "Volume";
            YEAR_TEXT_VIEW = "Year:";

            //Spinners.
            REPEAT_SPINNER_0 = "Minute(s)";
            REPEAT_SPINNER_1 = "Hour(s)";
            REPEAT_SPINNER_2 = "Day(s)";
            REPEAT_SPINNER_3 = "Week(s)";
            REPEAT_SPINNER_4 = "Month(s)";
            REPEAT_SPINNER_5 = "Year(s)";
            SOUND_SPINNER_DEFAULT = "Default";

            //Messages.
            ACCOUNT_CREATION_LOCKED = "Account creation is currently locked.";
            ALL_USERS_DELETED = "All users deleted.";
            DELETE_FAILED_WRONG_PASSWORD = "Wrong password.";
            LOGIN_FAILED_NO_SUCH_USER = "No such user exists.";
            LOGIN_FAILED_WRONG_PASSWORD = "Wrong password.";
            LOGIN_SUCCESSFUL = "Login successful.";
            MEDICINE_CREATED = "Medicine created.";
            MEDICINE_DELETED = "Medicine deleted.";
            MEDICINE_INFORMATION_EDITED = "Medicine edited.";
            NO_CAMERA = "This device has no camera.";
            NOTIFICATION_CREATED = "Notification created.";
            NOTIFICATION_DELETED = "Notification deleted.";
            NOTIFICATION_INFORMATION_EDITED = "Notification edited.";
            SAFETY_QUESTION_ANSWERS_DO_NOT_MATCH = "The answers don't match.";
            SAFETY_QUESTION_WRONG_ANSWER = "Wrong answer.";
            SAFETY_QUESTION_WRONG_PREVIOUS_ANSWER = "Wrong previous answer.";
            SETTINGS_SAVED = "Settings saved.";
            USER_CREATED = "User created.";
            USER_CREATION_PASSWORDS_DO_NOT_MATCH = "The passwords don't match.";
            USER_CREATION_USERNAME_ALREADY_EXISTS = "A user by that name already exists.";
            USER_DELETED = "User deleted.";

            //Dialogs
            DELETE_ALL_USERS_DIALOG_ALERT = "Really delete all users?";
            DELETE_USER_DIALOG_ALERT = "Really delete user?";
            DELETE_USER_DIALOG_NEGATIVE = "No";
            DELETE_USER_DIALOG_POSITIVE = "Yes";

            //RadioButtons
            CUSTOM_RADIO_BUTTON = "Custom";
            EDIT_RADIO_BUTTON = "Edit";
            MEDICINE_RADIO_BUTTON = "Medicine";
            NEW_RADIO_BUTTON = "New";
            NOTIFICATION_RADIO_BUTTON = "Notification";
            PERIODICAL_RADIO_BUTTON = "Periodical";
        }
        else if(LANGUAGE == GV.LANGUAGE_FINNISH)
        {
            //Buttons.
            ADD_BUTTON = "Lisää";
            ADD_IMAGE_BUTTON = "Lisää kuva";
            ADD_REPEAT_BUTTON = "Lisää toisto";
            ADD_SOUND_BUTTON = "Lisää ääni";
            BACK_BUTTON = "Takaisin";
            CLEAR_ALERT_BUTTON = "Poista hälytys";
            DELETE_ALL_USERS_BUTTON = "Poista kaikki käyttäjät";
            CREATE_BUTTON = "Luo";
            DELETE_BUTTON = "Poista";
            DELETE_USER_BUTTON = "Poista käyttäjä";
            EDIT_MEDICINE_BUTTON = "Muokkaa lääkettä";
            EDIT_NOTIFICATION_BUTTON = "Muokkaa huomautusta";
            EXIT_BUTTON = "Sulje";
            IMPORT_IMAGE_BUTTON = "Tuo kuva";
            IMPORT_SOUND_BUTTON= "Tuo ääni";
            LISTEN_BUTTON = "Kuuntele";
            LOG_IN_BUTTON = "Kirjaudu sisään";
            LOG_OUT_BUTTON = "Kirjaudu ulos";
            LOGIN_BUTTON = "Sisäänkirjautuminen";
            NEW_MEDICINE_BUTTON = "Uusi lääke";
            NEW_NOTIFICATION_BUTTON = "Uusi huomautus";
            NEW_USER_BUTTON = "Uusi käyttäjä";
            RECORD_OFF_BUTTON = "Nauhoita";
            RECORD_ON_BUTTON = "Lopeta nauhoitus";
            REMINDERS_BUTTON = "Muistutukset";
            REMOVE_BUTTON = "Poista";
            SAFETY_QUESTION_BUTTON = "Varmennuskysymys";
            SAVE_BUTTON = "Tallenna";
            SETTINGS_BUTTON = "Asetukset";
            TAKE_IMAGE_BUTTON = "Ota kuva";
            USE_BUTTON = "Käytä";
            USER_SETTINGS_BUTTON = "Käyttäjän asetukset";

            //CheckBoxes.
            ALLOW_ALERT_CHANGES_IN_SETTINGS_CHECK_BOX = "Salli yleinen muistutuksien näyttämisen muutto";
            ACTIVATE_REMINDERS_CHECK_BOX = "Aktivoi muistutukset";
            IMAGE_CHECK_BOX = "Kuva käytössä";
            LOCK_ACCOUNT_CREATION_CHECK_BOX = "Lukitse käyttäjien luominen";
            REPEAT_CHECK_BOX = "Toisto käytössä";
            SHOW_MEDICINES_CHECK_BOX = "Näytä lääkkeet";
            SHOW_NOTIFICATIONS_CHECK_BOX = "Näytä huomautukset";
            SOUND_CHECK_BOX = "Ääni käytössä";
            USE_SAFETY_QUESTION_CHECK_BOX = "Käytä varmennuskysymystä";

            //EditTexts
            ANSWER_EDIT_TEXT = "Vastaus";
            CURRENT_ANSWER_EDIT_TEXT = "Nykyinen vastaus";
            HINT_EDIT_TEXT = "Vihje";
            IMAGE_NAME_EDIT_TEXT = "Otetun kuvan tiedostonimi";
            LOCATION_EDIT_TEXT = "Sijainti";
            NAME_EDIT_TEXT = "Nimi";
            NOTE_EDIT_TEXT = "Huom";
            PASSWORD_EDIT_TEXT = "Salasana";
            QUESTION_EDIT_TEXT = "Kysymys";
            REPEAT_ANSWER_EDIT_TEXT = "Toista vastaus";
            SOUND_NAME_EDIT_TEXT = "Nauhoitetun äänen tiedostonimi";
            USERNAME_EDIT_TEXT = "Käyttäjänimi";

            //TextViews.
            ACCESS_TEXT_VIEW = "Pääsy";
            ALERT_DATE_AND_TIME_TEXT_VIEW = "Hälytyksen päivämäärä ja aika";
            ALERT_MEDICINE_TEXT_VIEW = "Uusi lääke";
            ALERT_NOTIFICATION_TEXT_VIEW = "Uusi huomautus";
            DAY_TEXT_VIEW = "Päivä:";
            HINT_TEXT_VIEW = "Vihje";
            LANGUAGE_TEXT_VIEW = "Kieli";
            LOCATION_TEXT_VIEW = "Sijainti:";
            MODE_TEXT_VIEW = "Tila";
            MONTH_TEXT_VIEW = "Kuukausi:";
            NAME_TEXT_VIEW = "Nimi:";
            NOTE_TEXT_VIEW = "Huom:";
            QUESTION_TEXT_VIEW = "Kysymys";
            RECORD_OFF_TEXT_VIEW = "";
            RECORD_ON_TEXT_VIEW = "Nauhoitetaan...";
            REMINDER_VISIBILITY_TEXT_VIEW = "Muistutuksien näkyvyys";
            REPEAT_END_TEXT_VIEW = "Kunnes";
            REPEAT_MODE_TEXT_VIEW = "Toiston tila";
            REPEAT_PASSWORD_TEXT_VIEW = "Toista salasana";
            REPEAT_TEXT_VIEW = "Toista joka";
            SNOOZE_MINUTES_TEXT_VIEW = "(minuuteissa)";
            SNOOZE_TIME_TEXT_VIEW = "Torkkuaika";
            TEXT_TEXT_VIEW = "Teksti:";
            TIME_TEXT_VIEW = "Aika:";
            TITLE_TEXT_VIEW = "Otsikko:";
            TYPE_TEXT_VIEW = "Tyyppi";
            VOLUME_TEXT_VIEW = "Äänenvoimakkuus";
            YEAR_TEXT_VIEW = "Vuosi:";

            //Spinners.
            REPEAT_SPINNER_0 = "Minuutti";
            REPEAT_SPINNER_1 = "Tunti";
            REPEAT_SPINNER_2 = "Päivä";
            REPEAT_SPINNER_3 = "Viikko";
            REPEAT_SPINNER_4 = "Kuukausi";
            REPEAT_SPINNER_5 = "Vuosi";
            SOUND_SPINNER_DEFAULT = "Oletus";

            //Messages.
            ACCOUNT_CREATION_LOCKED = "Uuden käyttäjän luominen on tällä hetkellä lukittu.";
            ALL_USERS_DELETED = "Kaikki käyttäjät poistettu.";
            DELETE_FAILED_WRONG_PASSWORD = "Väärä salasana.";
            LOGIN_FAILED_NO_SUCH_USER = "Käyttäjää ei löydy.";
            LOGIN_FAILED_WRONG_PASSWORD = "Väärä salasana.";
            LOGIN_SUCCESSFUL = "Sisäänkirjautuminen onnistui.";
            MEDICINE_CREATED = "Lääke luotu.";
            MEDICINE_DELETED = "Lääke poistettu.";
            MEDICINE_INFORMATION_EDITED = "Lääkke muokattu.";
            NO_CAMERA = "Laitteessa ei ole kameraa.";
            NOTIFICATION_CREATED = "Huomautus luotu.";
            NOTIFICATION_DELETED = "Huomautus poistettu.";
            NOTIFICATION_INFORMATION_EDITED = "Huomautus muokattu.";
            SAFETY_QUESTION_ANSWERS_DO_NOT_MATCH = "Vastaukset eivät täsmää.";
            SAFETY_QUESTION_WRONG_ANSWER = "Väärä vastaus.";
            SAFETY_QUESTION_WRONG_PREVIOUS_ANSWER = "Väärä aiempi vastaus.";
            SETTINGS_SAVED = "Asetukset tallennettu.";
            USER_CREATED = "Käyttäjä luotu.";
            USER_CREATION_PASSWORDS_DO_NOT_MATCH = "Salasanat eivät täsmää.";
            USER_CREATION_USERNAME_ALREADY_EXISTS = "Käyttäjä kyseisellä nimellä on jo olemassa.";
            USER_DELETED = "Käyttäjä poistettu.";

            //Dialogs
            DELETE_ALL_USERS_DIALOG_ALERT = "Poistetaanko kaikki käyttäjät varmasti?";
            DELETE_USER_DIALOG_ALERT = "Poistetaanko käyttäjä varmasti?";
            DELETE_USER_DIALOG_NEGATIVE = "Ei";
            DELETE_USER_DIALOG_POSITIVE = "Kyllä";

            //RadioButtons
            CUSTOM_RADIO_BUTTON = "Mukautettu";
            EDIT_RADIO_BUTTON = "Muokkaa";
            MEDICINE_RADIO_BUTTON = "Lääke";
            NEW_RADIO_BUTTON = "Uusi";
            NOTIFICATION_RADIO_BUTTON = "Huomautus";
            PERIODICAL_RADIO_BUTTON = "Periodinen";
        }
        else if(LANGUAGE == GV.LANGUAGE_SWEDISH)
        {
            //Buttons.
            ADD_BUTTON = "Lägg till";
            ADD_IMAGE_BUTTON = "Lägg till bild";
            ADD_REPEAT_BUTTON = "Lägg till upprepning";
            ADD_SOUND_BUTTON = "Lägg till ljud";
            BACK_BUTTON = "Tillbaka";
            CLEAR_ALERT_BUTTON = "Ta bort alarm";
            CREATE_BUTTON = "Skapa";
            DELETE_ALL_USERS_BUTTON = "Ta bort alla användare";
            DELETE_BUTTON = "Ta bort";
            DELETE_USER_BUTTON = "Ta bort användare";
            EDIT_MEDICINE_BUTTON = "Redigera medicin";
            EDIT_NOTIFICATION_BUTTON = "Redigera notis";
            EXIT_BUTTON = "Stäng av";
            IMPORT_IMAGE_BUTTON = "Hämta bild";
            IMPORT_SOUND_BUTTON= "Hämta ljud";
            LISTEN_BUTTON = "Lyssna";
            LOG_IN_BUTTON = "Logga in";
            LOG_OUT_BUTTON = "Logga ut";
            LOGIN_BUTTON = "Inloggning";
            NEW_MEDICINE_BUTTON = "Ny medicin";
            NEW_NOTIFICATION_BUTTON = "Ny notis";
            NEW_USER_BUTTON = "Ny användare";
            RECORD_OFF_BUTTON = "Banda";
            RECORD_ON_BUTTON = "Sluta banda";
            REMINDERS_BUTTON = "Påminnelser";
            REMOVE_BUTTON = "Ta bort";
            SAFETY_QUESTION_BUTTON = "Säkerhetsfråga";
            SAVE_BUTTON = "Spara";
            SETTINGS_BUTTON = "Inställningar";
            TAKE_IMAGE_BUTTON = "Ta bild";
            USE_BUTTON = "Använd";
            USER_SETTINGS_BUTTON = "Användarens inställningar";

            //CheckBoxes.
            ALLOW_ALERT_CHANGES_IN_SETTINGS_CHECK_BOX = "Tillåt alla ändra på visandet av påminnelser";
            ACTIVATE_REMINDERS_CHECK_BOX = "Aktivera påminnelserna";
            IMAGE_CHECK_BOX = "Bild på";
            LOCK_ACCOUNT_CREATION_CHECK_BOX = "Lås användarskapande";
            REPEAT_CHECK_BOX = "Upprepning på";
            SHOW_MEDICINES_CHECK_BOX = "Visa mediciner";
            SHOW_NOTIFICATIONS_CHECK_BOX = "Visa notiser";
            SOUND_CHECK_BOX = "Ljud på";
            USE_SAFETY_QUESTION_CHECK_BOX = "Använd säkerhetsfråga";

            //EditTexts
            ANSWER_EDIT_TEXT = "Svar";
            CURRENT_ANSWER_EDIT_TEXT = "Nuvarande svar";
            HINT_EDIT_TEXT = "Ledtråd";
            IMAGE_NAME_EDIT_TEXT = "Tagna bildens filnamn";
            LOCATION_EDIT_TEXT = "Plats";
            NAME_EDIT_TEXT = "Namn";
            NOTE_EDIT_TEXT = "Notera";
            PASSWORD_EDIT_TEXT = "Lösenord";
            QUESTION_EDIT_TEXT = "Fråga";
            REPEAT_ANSWER_EDIT_TEXT = "Upprepa svar";
            SOUND_NAME_EDIT_TEXT = "Bandade ljudets filnamn";
            USERNAME_EDIT_TEXT = "Användarnamn";

            //TextViews.
            ACCESS_TEXT_VIEW = "Tillgång";
            ALERT_DATE_AND_TIME_TEXT_VIEW = "Alarmens datum och tid";
            ALERT_MEDICINE_TEXT_VIEW = "Ny medicin";
            ALERT_NOTIFICATION_TEXT_VIEW = "Ny notis";
            DAY_TEXT_VIEW = "Dag:";
            HINT_TEXT_VIEW = "Ledtråd";
            LANGUAGE_TEXT_VIEW = "Språk";
            LOCATION_TEXT_VIEW = "Plats:";
            MODE_TEXT_VIEW = "Läge";
            MONTH_TEXT_VIEW = "Månad:";
            NAME_TEXT_VIEW = "Namn:";
            NOTE_TEXT_VIEW = "Notera:";
            QUESTION_TEXT_VIEW = "Fråga";
            RECORD_OFF_TEXT_VIEW = "";
            RECORD_ON_TEXT_VIEW = "Bandar...";
            REMINDER_VISIBILITY_TEXT_VIEW = "Påminnelsernas synlighet";
            REPEAT_END_TEXT_VIEW = "Tills";
            REPEAT_MODE_TEXT_VIEW = "Upprepningsläge";
            REPEAT_PASSWORD_TEXT_VIEW = "Upprepa lösenord";
            REPEAT_TEXT_VIEW = "Upprepa varje";
            SNOOZE_MINUTES_TEXT_VIEW = "(i minuter)";
            SNOOZE_TIME_TEXT_VIEW = "Vilolägets längd";
            TEXT_TEXT_VIEW = "Text:";
            TIME_TEXT_VIEW = "Tid:";
            TITLE_TEXT_VIEW = "Titel:";
            TYPE_TEXT_VIEW = "Typ";
            VOLUME_TEXT_VIEW = "Ljudstyrka";
            YEAR_TEXT_VIEW = "År:";

            //Spinners.
            REPEAT_SPINNER_0 = "Minut(er)";
            REPEAT_SPINNER_1 = "Timme/ar";
            REPEAT_SPINNER_2 = "Dag(ar)";
            REPEAT_SPINNER_3 = "Vecka/or";
            REPEAT_SPINNER_4 = "Månad(er)";
            REPEAT_SPINNER_5 = "År";
            SOUND_SPINNER_DEFAULT = "Standard";

            //Messages.
            ACCOUNT_CREATION_LOCKED = "Skapandet av en ny användare är låst för tillfället.";
            ALL_USERS_DELETED = "Alla användare borttagna.";
            DELETE_FAILED_WRONG_PASSWORD = "Fel lösenord.";
            LOGIN_SUCCESSFUL = "Inloggningen lyckades.";
            LOGIN_FAILED_WRONG_PASSWORD = "Fel lösenord.";
            LOGIN_FAILED_NO_SUCH_USER = "Användaren existerar inte.";
            MEDICINE_CREATED = "Medicin skapad.";
            MEDICINE_DELETED = "Medicin borttagen.";
            MEDICINE_INFORMATION_EDITED = "Medicin redigerad.";
            NO_CAMERA = "Apparaten har inte en kamera.";
            NOTIFICATION_CREATED = "Notis skapad.";
            NOTIFICATION_DELETED = "Notis borttagen.";
            NOTIFICATION_INFORMATION_EDITED = "Notis redigerad.";
            SAFETY_QUESTION_ANSWERS_DO_NOT_MATCH = "Svaren är olika.";
            SAFETY_QUESTION_WRONG_ANSWER = "Fel svar.";
            SAFETY_QUESTION_WRONG_PREVIOUS_ANSWER = "Fel tidigare svar.";
            SETTINGS_SAVED = "Inställningar sparade.";
            USER_CREATED = "Användare skapad.";
            USER_CREATION_PASSWORDS_DO_NOT_MATCH = "Lösenorden är olika.";
            USER_CREATION_USERNAME_ALREADY_EXISTS = "Det finns redan en användare med det givna namnet.";
            USER_DELETED = "Användare borttagen.";

            //Dialogs
            DELETE_ALL_USERS_DIALOG_ALERT = "Vill du säkert ta bort alla användare?";
            DELETE_USER_DIALOG_ALERT = "Vill du säkert ta bort användaren?";
            DELETE_USER_DIALOG_NEGATIVE = "Nej";
            DELETE_USER_DIALOG_POSITIVE = "Ja";

            //RadioButtons
            CUSTOM_RADIO_BUTTON = "Anpassad";
            EDIT_RADIO_BUTTON = "Redigera";
            MEDICINE_RADIO_BUTTON = "Medicin";
            NEW_RADIO_BUTTON = "Ny";
            NOTIFICATION_RADIO_BUTTON = "Notis";
            PERIODICAL_RADIO_BUTTON = "Periodisk";
        }
        else
        {
            Log.v("Language error", "No such language supported.");
        }
    }
}
