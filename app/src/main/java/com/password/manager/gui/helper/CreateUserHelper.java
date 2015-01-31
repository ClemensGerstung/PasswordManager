package com.password.manager.gui.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.password.manager.R;
import com.password.manager.core.Logger;
import com.password.manager.core.User;
import com.password.manager.core.handler.PasswordSafetyHandler;
import com.password.manager.core.handler.PathHandler;

import java.io.File;

/**
 * Created by Clemens on 12.12.2014.
 */
public class CreateUserHelper {
    public static void createUser(final Context context) {
        View view = View.inflate(context, R.layout.create_user_layout, null);

        final EditText userEditText = (EditText) view.findViewById(R.id.create_user_user_name_edit_text);
        final EditText newPasswordEditText = (EditText) view.findViewById(R.id.create_user_new_password_edit_text);
        final EditText repeatPasswordEditText = (EditText) view.findViewById(R.id.create_user_repeat_password_edit_text);

        final TextView newPasswordTextView = (TextView) view.findViewById(R.id.create_user_new_password_text_view);
        final TextView repeatPasswordTextView = (TextView) view.findViewById(R.id.create_user_repeat_password_text_view);

        final int[] di = new int[1];
        newPasswordTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        di[0] = newPasswordEditText.getInputType();
                        newPasswordEditText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        break;
                    case MotionEvent.ACTION_UP:
                        newPasswordEditText.setInputType(di[0]);
                        break;
                }

                return true;
            }
        });

        repeatPasswordTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        di[0] = repeatPasswordEditText.getInputType();
                        repeatPasswordEditText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        break;
                    case MotionEvent.ACTION_UP:
                        repeatPasswordEditText.setInputType(di[0]);
                        break;
                }

                return true;
            }
        });

        final AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(R.string.helper_create_user)
                .setView(view)
                .setCancelable(true)
                .setNegativeButton(R.string.helper_cancel, null)
                .setPositiveButton("OK", null)
                .create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String pathToUser = PathHandler.PathToUsers + File.separator + userEditText.getText().toString() + ".xml";
                        String pathToKey = PathHandler.PathToKeys + File.separator + userEditText.getText().toString() + ".xml";
                        String newPassword = newPasswordEditText.getText().toString();
                        String repeatPassword = repeatPasswordEditText.getText().toString();

                        if (PathHandler.fileExists(pathToUser)) {
                            Logger.show(R.string.error_user_exists, context);
                        } else if (!newPassword.equals(repeatPassword)) {
                            Logger.show(R.string.error_password_dont_match, context);
                        } else if (PasswordSafetyHandler.isSafe(newPassword, 2, 2, 2, 2, 8)) {
                            Logger.show("Password isn't save enough!", context);
                        } else {
                            try {
                                User user = new User(userEditText.getText().toString(), newPassword, pathToKey);

                                PathHandler.createPMDirectory();


                                user.save();
                                PathHandler.writeFile(pathToKey, "");

                                alertDialog.dismiss();
                            } catch (Exception e) {
                                Logger.show(e.getMessage(), context);
                            }
                        }
                    }
                });
            }
        });
        alertDialog.show();
    }
}
