package com.password.manager;


import android.app.Fragment;
import android.content.Intent;
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
import com.password.manager.core.brut.force.prevention.LockService;
import com.password.manager.core.handler.PasswordListHandler;
import com.password.manager.gui.helper.CreateUserHelper;


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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameCharSequence = name.getText();
                passwordCharSequence = password.getText();


                try {

                    LockService.LOCKSERVICE.loginUser(nameCharSequence.toString(), passwordCharSequence.toString());

                    Settings settings = Settings.getInstance();
                    settings.setSaveLogin(saveLogin.isChecked());
                    settings.setRememberedUserName(remember_user.isChecked() && name.getText().length() > 0 ? name.getText().toString() : "");

                    settings.save();

                    getActivity()
                            .getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_layout_fragment_to_replace, new PasswordListFragment())
                            .commit();

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
