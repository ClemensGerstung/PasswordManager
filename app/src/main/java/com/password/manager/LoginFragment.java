package com.password.manager;


import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.password.manager.core.Logger;
import com.password.manager.core.Settings;
import com.password.manager.core.User;
import com.password.manager.core.handler.AESHandler;
import com.password.manager.core.handler.PasswordListHandler;
import com.password.manager.core.handler.PathHandler;
import com.password.manager.gui.helper.CreateUserHelper;

import java.io.File;


public class LoginFragment extends Fragment {

    private CheckBox remember_user;
    private CheckBox saveLogin;
    private Button login;
    private Button create_user;
    private EditText name;
    private EditText password;

    private CharSequence nameCharSequence;
    private CharSequence passwordCharSequence;

    public LoginFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_layout, container, false);
        remember_user = (CheckBox) view.findViewById(R.id.login_check_box_remember_user);
        login = (Button) view.findViewById(R.id.login_button_login);
        create_user = (Button) view.findViewById(R.id.login_button_create_user);
        name = (EditText) view.findViewById(R.id.login_name_input);
        password = (EditText) view.findViewById(R.id.login_password_input);
        saveLogin = (CheckBox) view.findViewById(R.id.login_layout_checkbox_save_login);

        try {
            Settings settings = Settings.getInstance();

            remember_user.setChecked(settings.getRememberedUserName().length() > 0);
            saveLogin.setChecked(settings.isSaveLogin());
            name.setText(settings.getRememberedUserName());

        } catch (Exception e) {
            Toast.makeText(getActivity(), getResourceString(R.string.error) + " " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (remember_user.isChecked()) remember_user.setChecked(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        /// TODO: prevent brut-force-attacks!
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameCharSequence = name.getText();
                passwordCharSequence = password.getText();

                try {
                    if (nameCharSequence.length() == 0 && passwordCharSequence.length() == 0)
                        throw new Exception(getResourceString(R.string.error_no_user_input_and_password));
                    if (nameCharSequence.length() == 0)
                        throw new Exception(getResourceString(R.string.error_no_user_input));
                    if (passwordCharSequence.length() == 0)
                        throw new Exception(getResourceString(R.string.error_no_password));


                    String path = PathHandler.PathToUsers + File.separator + nameCharSequence + ".xml";
                    if (!new File(path).exists()) {
                        throw new Exception(getResourceString(R.string.error_user_doesnt_exist));
                    }

                    String user_file = PathHandler.readFile(path);
                    User user = User.getInstance(user_file);

                    String en_pas = AESHandler.encrypt(passwordCharSequence.toString(), passwordCharSequence.toString()).replace("\n", "");
                    if (!user.getPassword().equals(en_pas)) {
                        throw new Exception(Logger.getResourceString(R.string.error_wrong_password, getActivity()));
                    } else if (en_pas.length() == 0) {
                        throw new Exception(Logger.getResourceString(R.string.error_no_password, getActivity()));
                    } else {
                        user.setPassword(passwordCharSequence.toString());

                        String key_file = PathHandler.readFile(PathHandler.PathToKeys + File.separator + nameCharSequence + ".xml");
                        if (key_file.isEmpty()) {
                            PasswordListHandler passwordListHandler = PasswordListHandler.getInstance();
                        } else {
                            String de_key_file = AESHandler.decrypt(key_file, user.getPassword());
                            PasswordListHandler passwordListHandler = PasswordListHandler.createPasswordListHandlerFromString(de_key_file);
                        }

                        Settings settings = Settings.getInstance();
                        settings.setSaveLogin(saveLogin.isChecked());
                        settings.setRememberedUserName(remember_user.isChecked() && name.getText().length() > 0 ? name.getText().toString() : "");

                        settings.save();



                        getActivity()
                                .getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_layout_fragment_to_replace, new PasswordListFragment())
                                .commit();
                    }
                } catch (Exception e) {
                    Logger.show(e.getMessage(), getActivity());
                    User.logout();
                    PasswordListHandler.logout();
                }
            }
        });

        create_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateUserHelper.createUser(getActivity());
            }
        });


        return view;
    }

    public String getResourceString(int id) {
        return getActivity().getResources().getString(id);
    }


}
