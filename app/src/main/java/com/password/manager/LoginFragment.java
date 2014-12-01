package com.password.manager;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.password.manager.classes.AESHelper;
import com.password.manager.classes.FileAndDirectoryHandler;
import com.password.manager.classes.PMSerializer;
import com.password.manager.classes.User;

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
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String user_file = FileAndDirectoryHandler.readFile(FileAndDirectoryHandler.PathToUsers + File.separator + nameCharSequence + ".xml");
                        User user = PMSerializer.deserialize(user_file, User.class);

                        String en_pas = AESHelper.encrypt(passwordCharSequence.toString(), passwordCharSequence.toString());
                        if(!user.password.equals(en_pas)){
                            throw new Exception("Password is wrong!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) { e.printStackTrace(); }


        return view;
    }


}
