package com.password.manager;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.password.manager.classes.Password;
import com.password.manager.classes.PasswordListHandler;
import com.password.manager.classes.RandomPasswordGenerator;


/**
 * Created by Clemens on 04.12.2014.
 */
public class AddEditPasswordHelper {
    public static void addPassword(final Context context) {
        final PasswordListHandler passwordListHandler = PasswordListHandler.getInstance();
        View view = View.inflate(context, R.layout.add_edit_password_layout, null);

        final EditText program_edit_text = (EditText) view.findViewById(R.id.add_edit_password_edit_text_program);
        final EditText username_edit_text = (EditText) view.findViewById(R.id.add_edit_password_edit_text_username);
        final EditText password_edit_text = (EditText) view.findViewById(R.id.add_edit_password_edit_text_password);

        password_edit_text.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                password_edit_text.setText(RandomPasswordGenerator.generatePassword(8, 15, 2, 2, 2), TextView.BufferType.NORMAL);

                return true;
            }
        });

        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.add_edit_password_helper_add_title))
                .setView(view)
                .setNegativeButton(R.string.add_edit_password_helper_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String program = program_edit_text.getText().toString();
                        String username = username_edit_text.getText().toString();
                        String password = password_edit_text.getText().toString();

                        try {
                            if(program.length() == 0) throw new Exception(getString(context, R.string.error_no_program));
                            if(password.length() == 0) throw new Exception(getString(context, R.string.error_no_password));

                            passwordListHandler.addAndSave(new Password(program, username, password));
                        } catch (Exception e) {
                            Toast.makeText(context, getString(context, R.string.error) + " " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        dialog.dismiss();
                    }
                })
                .show();

    }

    private static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }
}
