package com.password.manager.clickhandler;

import android.view.View;

/**
 * Created by Clemens on 01.12.2014.
 */
public class LoginClickHandler implements View.OnClickListener {

    private String user_file;
    private String password;

    public LoginClickHandler(String user_file, String password) {
        this.user_file = user_file;
        this.password = password;
    }

    @Override
    public void onClick(View v) {

    }
}
