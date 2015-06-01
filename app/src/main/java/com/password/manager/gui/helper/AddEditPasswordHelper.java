package com.password.manager.gui.helper;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.password.manager.R;
import com.password.manager.core.Logger;
import com.password.manager.core.Password;
import com.password.manager.core.PasswordListAdapter;
import com.password.manager.core.RandomPasswordGenerator;
import com.password.manager.core.Settings;
import com.password.manager.core.User;
import com.password.manager.core.handler.PasswordListHandler;


/**
 * Created by Clemens on 04.12.2014.
 */
public class AddEditPasswordHelper {
    public static void addPassword(final Context context, final PasswordListAdapter passwordListAdapter) {
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

        AlertDialogHelper.create(context, R.string.show_password_header, view, R.string.ok, R.string.helper_cancel)
                .setSingleButton(new AlertDialogHelper.OnClickListener() {
                    @Override
                    public void onClick(AlertDialogHelper dialog) {
                        String program = program_edit_text.getText().toString();
                        String username = username_edit_text.getText().toString();
                        String password = password_edit_text.getText().toString();

                        if (program.length() == 0) {
                            Logger.show(R.string.error_no_program, context);
                        } else if (password.length() == 0) {
                            Logger.show(R.string.error_no_password, context);
                        } else {
                            try {
                                passwordListHandler.addAndSave(new Password(program, username, password));
                            } catch (Exception e) {
                                Logger.show(e.getMessage(), context);
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

        program_text_view.setText(password.getProgram(), TextView.BufferType.NORMAL);
        username_text_view.setText(password.getUsername(), TextView.BufferType.NORMAL);
        password_text_view.setText(password.getPassword(), TextView.BufferType.NORMAL);

        final AlertDialogHelper alertDialogHelper =
                AlertDialogHelper.create(context, R.string.show_password_header, view, R.string.ok, R.string.helper_delete, R.string.helper_edit)
                    .setAllButtons(
                    new AlertDialogHelper.OnClickListener() {
                        @Override
                        public void onClick(AlertDialogHelper dialog) {
                            dialog.dismiss();
                        }
                    },
                    new AlertDialogHelper.OnClickListener() {
                        @Override
                        public void onClick(AlertDialogHelper dialog) {
                            dialog.dismiss();

                            removePassword(context, position, passwordListAdapter);
                        }
                    },
                    new AlertDialogHelper.OnClickListener() {
                        @Override
                        public void onClick(AlertDialogHelper dialog) {
                            dialog.dismiss();
                            editPassword(context, position, passwordListAdapter);
                        }
                    });

        if (Settings.getInstance().isSaveLogin()) {
            View view1 = View.inflate(context, R.layout.save_login_show_password_relogin, null);
            final TextView master_password_text_view = (TextView) view1.findViewById(R.id.save_login_show_password_relogin_edit_text);

            AlertDialogHelper.create(context, R.string.helper_enter_password, view1, R.string.ok, R.string.helper_cancel)
                    .setSingleButton(new AlertDialogHelper.OnClickListener() {
                        @Override
                        public void onClick(AlertDialogHelper dialog) {
                            String pw = master_password_text_view.getText().toString();
                            try {
                                if (pw.equals(User.getInstance("").getPassword())) {
                                    alertDialogHelper.show();
                                } else {
                                    Logger.show(R.string.error_wrong_password, context);
                                }
                            } catch (Exception e) {
                                Logger.show(e.getMessage(), context);
                            }

                            dialog.dismiss();
                        }
                    })
                    .show();
        } else if (!Settings.getInstance().isSaveLogin()) {
            alertDialogHelper.show();
        }
    }

    public static void editPassword(final Context context, final int index, final PasswordListAdapter passwordListAdapter) {
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

        program_edit_text.setText(password.getProgram());
        username_edit_text.setText(password.getUsername());
        password_edit_text.setText(password.getPassword());

        AlertDialogHelper.create(context, R.string.helper_edit, view, R.string.ok, R.string.helper_cancel)
                .setSingleButton(new AlertDialogHelper.OnClickListener() {
                    @Override
                    public void onClick(AlertDialogHelper dialog) {
                        String program = program_edit_text.getText().toString();
                        String username = username_edit_text.getText().toString();
                        String password = password_edit_text.getText().toString();

                        if (program.length() == 0) {
                            Logger.show(R.string.error_no_program, context);
                        } else if (password.length() == 0) {
                            Logger.show(R.string.error_no_password, context);
                        } else {
                            try {
                                Password ps = new Password(program, username, password);
                                PasswordListHandler plh = PasswordListHandler.getInstance();
                                plh.getObjects().set(index, ps);
                                plh.save();

                                passwordListAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                Logger.show(e.getMessage(), context);
                            }

                            dialog.dismiss();
                        }
                    }
                })
                .show();
    }

    public static void removePassword(final Context context, final int index, final PasswordListAdapter passwordListAdapter) {
        View view = View.inflate(context, R.layout.delete_message_layout, null);
        AlertDialogHelper.create(context, R.string.helper_delete, view, R.string.ok, R.string.helper_cancel)
                .setSingleButton(new AlertDialogHelper.OnClickListener() {
                    @Override
                    public void onClick(AlertDialogHelper dialog) {
                        try {
                            PasswordListHandler.getInstance().getObjects().remove(index);
                            PasswordListHandler.getInstance().save();
                            passwordListAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            Logger.show(e.getMessage(), context);
                        }
                        dialog.dismiss();
                    }
                }).show();
    }
}
