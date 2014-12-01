package com.password.manager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        getFragmentManager().beginTransaction().replace(R.id.main_layout_fragment_to_replace, new LoginFragment()).commit();
    }
}
