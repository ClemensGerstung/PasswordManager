package com.password.manager;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.password.manager.classes.PasswordListHandler;
import com.password.manager.classes.User;

// TODO: change main password
// TODO: add-button move into actionbar
// TODO: rotate/close save instance state handling
// TODO: if main_user_name is empty remember user has to be unchecked
// TODO: set animations on swaping fragments

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout_fragment_to_replace, new LoginFragment())
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        logout();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //TODO: logout();

    }

    public void logout() {
        User.logout();
        PasswordListHandler.logout();
        getFragmentManager()
                .beginTransaction()
                //.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.main_layout_fragment_to_replace, new LoginFragment())
                .commit();

    }
}
