package com.password.manager;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;

public class LoginFragment extends Fragment {

    private ExpandableListView expandableListView;


    public LoginFragment() {
        
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_layout, container, false);
        expandableListView = (ExpandableListView)view.findViewById(R.id.login_name_expandable_list_view);

        String[] strings = {"Hallo", "Eins", "Zwei", "Drei"};

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.login_name_dropdown_menu_layout, strings);
        expandableListView.setAdapter(listAdapter);

        return view;
    }


}
