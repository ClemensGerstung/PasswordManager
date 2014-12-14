package com.password.manager.gui.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.password.manager.R;
import com.password.manager.core.Logger;
import com.password.manager.core.Password;
import com.password.manager.core.User;
import com.password.manager.handler.PasswordListHandler;

/**
 * Created by Clemens on 12.12.2014.
 */
public class ChangeMasterPasswordHelper {

    public static void ChangeMasterPassword(final Context context) throws Exception {
        final User user = User.getInstance("");
        View view = View.inflate(context, R.layout.change_master_password_layout, null);

        // TODO: on touch password is visible

        final TextView oldPasswordTextView = (TextView) view.findViewById(R.id.change_master_password_old_password_text_view);
        final TextView newPasswordTextView = (TextView) view.findViewById(R.id.change_master_password_new_password_text_view);
        final TextView repeatPasswordTextView = (TextView) view.findViewById(R.id.change_master_password_repeat_password_text_view);

        final EditText oldPasswordEditText = (EditText) view.findViewById(R.id.change_master_password_old_password_edit_text);
        final EditText newPasswordEditText = (EditText) view.findViewById(R.id.change_master_password_new_password_edit_text);
        final EditText repeatPasswordEditText = (EditText) view.findViewById(R.id.change_master_password_repeat_password_edit_text);

        final AlertDialog d = new AlertDialog.Builder(context)
                .setTitle(R.string.helper_change_master_password)
                .setView(view)
                .setCancelable(true)
                .setNegativeButton(R.string.helper_cancel, null)
                .setPositiveButton("OK", null)
                .create();

        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = d.getButton(DialogInterface.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String oldPW = oldPasswordEditText.getText().toString();
                        String newPW = newPasswordEditText.getText().toString();
                        String repeatPW = repeatPasswordEditText.getText().toString();

                        // TODO: extract strings to strings.xml
                        // TODO: REGEX
                        if (oldPW.isEmpty()) {
                            Logger.show("Old password can't be empty", context);
                        } else if (newPW.isEmpty()) {
                            Logger.show("New password can't be empty", context);
                        } else if (repeatPW.isEmpty()) {
                            Logger.show("The repeated password can't be empty", context);
                        } else if (!newPW.equals(repeatPW)) {
                            Logger.show("The new passwords doesn't match!", context);
                        } else if (newPW.equals(oldPW)) {
                            Logger.show("New and old password must be different", context);
                        }/* else if (!newPW.matches(Password.PasswordREGEX)) {
                            Logger.show("Password isn't save enough!", context);
                        }*/ else {
                            try {
                                user.setPassword(newPW);
                                user.save();

                                PasswordListHandler.getInstance().save();

                                d.dismiss();
                            } catch (Exception e) {
                                Logger.show(e.getMessage(), context);
                            }
                        }
                    }
                });

            }
        });

    }
}
