package com.event.search.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogManager {
    private static AlertDialogManager mInstance;

    private  AlertDialogManager(){
        mInstance = new AlertDialogManager();
    }

    public static AlertDialogManager getInstance(){
        if(null == mInstance)
            mInstance = new AlertDialogManager();
        return mInstance;
    }
	/**
	 * Function to display simple Alert Dialog
	 * @param context - application context
	 * @param title - alert dialog title
	 * @param message - alert message
	 * */
	public void showAlertDialog(Context context, String title, String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
}
