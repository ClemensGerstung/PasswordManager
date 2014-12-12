package com.password.manager.gui.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.password.manager.R;
import com.password.manager.core.Logger;
import com.password.manager.core.User;

/**
 * Created by Clemens on 12.12.2014.
 */
public class ChangeMasterPasswordHelper {
    public static void ChangeMasterPassword(Context context) throws Exception {
        User user = User.getInstance("");

        new AlertDialog.Builder(context)
                .setTitle("HI")
                .setMessage("asdfsd")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
