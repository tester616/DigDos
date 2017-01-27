package com.example.x.digdos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by rusty on 5.6.2015.
 */
public class AlertDialogFragment extends DialogFragment
{
    private String text,
            positive,
            negative;

    //Part with code for implementing an interface and allowing the button clicking methods to be run in the launching Activity.
    //----------------------------------------------------------------------------------------------
    //The activity that creates an instance of this dialog fragment must
    // implement this interface in order to receive event callbacks.
    //Each method passes the DialogFragment in case the host needs to query it.
    public interface AlertDialogListener
    {
        public void onDialogPositiveClick();
        public void onDialogNegativeClick();
        //public void onDialogPositiveClick(DialogFragment dialog);
        //public void onDialogNegativeClick(DialogFragment dialog);
    }

    //Use this instance of the interface to deliver action events
    private AlertDialogListener ADListener;

    //Override the Fragment.onAttach() method to instantiate the UserDeleteAlertDialogListener.
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        //Verify that the host activity implements the callback interface
        try
        {
            //Instantiate the NoticeDialogListener so we can send events to the host
            ADListener = (AlertDialogListener) activity;
        }
        catch (ClassCastException e)
        {
            //The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
    //----------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Gives values based on what was sent in the bundle.
        text = getArguments().getString("text");
        positive = getArguments().getString("positive");
        negative = getArguments().getString("negative");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        //Use the Builder class for convenient dialog construction.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Set text, positive button, negative button and listeners for them.
        builder.setMessage(text)
                .setPositiveButton(
                    positive,
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            //User gave positive answer.
                            ADListener.onDialogPositiveClick();
                        }
                    }
                )
                .setNegativeButton(
                    negative,
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            //User gave negative answer.
                            ADListener.onDialogNegativeClick();
                        }
                    }
                );

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
