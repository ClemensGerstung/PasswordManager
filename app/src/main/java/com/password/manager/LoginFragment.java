package com.password.manager;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.password.manager.classes.FileAndDirectoryHandler;
import com.password.manager.classes.PMSerializer;
import com.password.manager.classes.User;
import com.password.manager.clickhandler.LoginClickHandler;

import java.io.File;

public class LoginFragment extends Fragment {

    private CheckBox remember_user;
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

        nameCharSequence = name.getText();
        passwordCharSequence = password.getText();

        try {
            String userFile = FileAndDirectoryHandler.readFile(FileAndDirectoryHandler.PathToUsers + File.separator + nameCharSequence.toString() + ".xml");

            login.setOnClickListener(new LoginClickHandler(userFile, passwordCharSequence.toString()));

        } catch (Exception e) { e.printStackTrace(); }


        return view;
    }


}
