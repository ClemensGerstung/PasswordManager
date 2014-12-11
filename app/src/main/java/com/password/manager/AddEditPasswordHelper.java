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
import com.password.manager.classes.Settings;
import com.password.manager.classes.User;


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

                password_edit_text.setText(RandomPasswordGenerator.generatePassword(10, 15, 2, 4, 4), TextView.BufferType.NORMAL);

                return true;
            }
        });

        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.show_password_header))
                .setView(view)
                .setNegativeButton(R.string.add_edit_password_helper_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String program = program_edit_text.getText().toString();
                        String username = username_edit_text.getText().toString();
                        String password = password_edit_text.getText().toString();

                        if (program.length() == 0) {
                            Toast.makeText(context, getString(context, R.string.error) + " " + getString(context, R.string.error_no_program), Toast.LENGTH_LONG).show();
                        } else if (password.length() == 0) {
                            Toast.makeText(context, getString(context, R.string.error) + " " + getString(context, R.string.error_no_password), Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                passwordListHandler.addAndSave(new Password(program, username, password));
                            } catch (Exception e) {
                                Toast.makeText(context, getString(context, R.string.error) + " " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                            dialog.dismiss();
                        }
                    }
                })
                .show();

    }

    public static void showPassword(final Context context, Password password, final int position, final PasswordListAdapter passwordListAdapter) throws Exception {


        final PasswordListHandler passwordListHandler = PasswordListHandler.getInstance();
        View view = View.inflate(context, R.layout.show_passowrd_layout, null);

        final TextView program_text_view = (TextView) view.findViewById(R.id.show_password_edit_text_program);
        final TextView username_text_view = (TextView) view.findViewById(R.id.show_password_edit_text_username);
        final TextView password_text_view = (TextView) view.findViewById(R.id.show_password_edit_text_password);

        program_text_view.setText(password.getHeader(), TextView.BufferType.NORMAL);
        username_text_view.setText(password.getUsername(), TextView.BufferType.NORMAL);
        password_text_view.setText(password.getPassword(), TextView.BufferType.NORMAL);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.add_edit_password_helper_add_title))
                .setView(view)
                .setNegativeButton(R.string.add_edit_password_helper_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        removePassword(context, position, passwordListAdapter);
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNeutralButton(R.string.add_edit_password_helper_edit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editPassword(context, position, passwordListAdapter);
                        dialog.dismiss();
                    }
                });

        if (Settings.getInstance().isSaveLogin()) {
            View view1 = View.inflate(context, R.layout.save_login_show_password_relogin, null);
            final TextView master_password_text_view = (TextView) view1.findViewById(R.id.save_login_show_password_relogin_edit_text);

            new AlertDialog.Builder(context)
                    .setTitle(context.getResources().getString(R.string.add_edit_password_helper_add_title))
                    .setView(view1)
                    .setNegativeButton(R.string.add_edit_password_helper_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String pw = master_password_text_view.getText().toString();
                            try {
                                if (pw.equals(User.getInstance("").getPassword())) {
                                    builder.show();
                                } else {
                                    Toast.makeText(context, getString(context, R.string.error_wrong_password), Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            dialog.dismiss();
                        }
                    }).show();
        } else if (!Settings.getInstance().isSaveLogin()){
            builder.show();
        }
    }

    public static void editPassword(final Context context, final int index, final PasswordListAdapter passwordListAdapter){
        final PasswordListHandler passwordListHandler = PasswordListHandler.getInstance();
        View view = View.inflate(context, R.layout.add_edit_password_layout, null);

        final EditText program_edit_text = (EditText) view.findViewById(R.id.add_edit_password_edit_text_program);
        final EditText username_edit_text = (EditText) view.findViewById(R.id.add_edit_password_edit_text_username);
        final EditText password_edit_text = (EditText) view.findViewById(R.id.add_edit_password_edit_text_password);

        password_edit_text.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                password_edit_text.setText(RandomPasswordGenerator.generatePassword(10, 15, 2, 4, 4), TextView.BufferType.NORMAL);

                return true;
            }
        });

        Password password = passwordListHandler.getObjects().get(index);

        program_edit_text.setText(password.getHeader());
        username_edit_text.setText(password.getUsername());
        password_edit_text.setText(password.getPassword());

        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.add_edit_password_helper_edit))
                .setView(view)
                .setNegativeButton(R.string.add_edit_password_helper_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String program = program_edit_text.getText().toString();
                        String username = username_edit_text.getText().toString();
                        String password = password_edit_text.getText().toString();

                        if (program.length() == 0) {
                            Toast.makeText(context, getString(context, R.string.error) + " " + getString(context, R.string.error_no_program), Toast.LENGTH_LONG).show();
                        } else if (password.length() == 0) {
                            Toast.makeText(context, getString(context, R.string.error) + " " + getString(context, R.string.error_no_password), Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                Password ps = new Password(program, username, password);
                                PasswordListHandler plh = PasswordListHandler.getInstance();
                                plh.getObjects().set(index, ps);
                                plh.save();

                                passwordListAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                Toast.makeText(context, getString(context, R.string.error) + " " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                            dialog.dismiss();
                        }
                    }
                })
                .show();
    }

    public static void removePassword(final Context context, final int index, final PasswordListAdapter passwordListAdapter){
        new AlertDialog.Builder(context)
                .setTitle(getString(context, R.string.add_edit_password_helper_delete))
                .setMessage(R.string.add_edit_password_helper_delete_message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            PasswordListHandler.getInstance().getObjects().remove(index);
                            PasswordListHandler.getInstance().save();
                            passwordListAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            Toast.makeText(context, getString(context, R.string.error_wrong_password), Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.add_edit_password_helper_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }
}
