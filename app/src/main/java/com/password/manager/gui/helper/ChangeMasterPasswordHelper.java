package com.password.manager.gui.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.password.manager.R;
import com.password.manager.core.Logger;
import com.password.manager.core.User;
import com.password.manager.core.handler.PasswordListHandler;

/**
 * Created by Clemens on 12.12.2014.
 */
public class ChangeMasterPasswordHelper {

    public static void ChangeMasterPassword(final Context context) throws Exception {
        final User user = User.getInstance("");
        View view = View.inflate(context, R.layout.change_master_password_layout, null);

        final EditText oldPasswordEditText = (EditText) view.findViewById(R.id.change_master_password_old_password_edit_text);
        final EditText newPasswordEditText = (EditText) view.findViewById(R.id.change_master_password_new_password_edit_text);
        final EditText repeatPasswordEditText = (EditText) view.findViewById(R.id.change_master_password_repeat_password_edit_text);

        final TextView oldPasswordTextView = (TextView) view.findViewById(R.id.change_master_password_old_password_text_view);
        final TextView newPasswordTextView = (TextView) view.findViewById(R.id.change_master_password_new_password_text_view);
        final TextView repeatPasswordTextView = (TextView) view.findViewById(R.id.change_master_password_repeat_password_text_view);

        final int[] di = new int[1];
        oldPasswordTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        di[0] = oldPasswordEditText.getInputType();
                        oldPasswordEditText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        break;
                    case MotionEvent.ACTION_UP:
                        oldPasswordEditText.setInputType(di[0]);
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

                        // TODO: REGEX
                        if (oldPW.isEmpty()) {
                            Logger.show(R.string.error_old_password_empty, context);
                        } else if (newPW.isEmpty()) {
                            Logger.show(R.string.error_new_password_cant_be_empty, context);
                        } else if (repeatPW.isEmpty()) {
                            Logger.show(R.string.error_repeated_password_cant_be_empty, context);
                        } else if (!newPW.equals(repeatPW)) {
                            Logger.show(R.string.error_password_dont_match, context);
                        } else if (newPW.equals(oldPW)) {
                            Logger.show(R.string.error_new_and_old_must_be_different, context);
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

        d.show();

    }
}
