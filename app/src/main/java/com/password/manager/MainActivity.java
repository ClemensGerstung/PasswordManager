package com.password.manager;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.password.manager.core.Logger;
import com.password.manager.handler.PasswordListHandler;
import com.password.manager.core.User;

// TODO: add-button move into actionbar
// TODO: rotate save instance state handling -> DO NOT LOGOUT!!!!
// TODO: set animations on swapping fragments

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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.clear();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        logout();
    }

    @Override
    protected void onPause() {
        super.onPause();
        logout();

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
