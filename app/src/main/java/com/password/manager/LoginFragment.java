package com.password.manager;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

public class LoginFragment extends Fragment {

    private CheckBox checkBox;

    public LoginFragment() {
        
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_layout, container, false);
        checkBox = (CheckBox)view.findViewById(R.id.login_check_box_remember_user);

        return view;
    }


}
