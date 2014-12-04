package com.password.manager;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.password.manager.classes.PasswordListHandler;
import com.password.manager.classes.User;

// TODO: change main password
// TODO: edit password entry
// TODO: delete password entry
// TODO: add-button move into actionbar
// TODO:

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        getFragmentManager().beginTransaction().replace(R.id.main_layout_fragment_to_replace, new LoginFragment()).commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //TODO: logout()

    }

    public void logout() {
        User.logout();
        PasswordListHandler.logout();
        getFragmentManager().beginTransaction().replace(R.id.main_layout_fragment_to_replace, new LoginFragment()).commit();
    }
}
