package com.password.manager;



import android.app.ActionBar;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.password.manager.classes.PasswordListHandler;
import com.password.manager.classes.Settings;


public class PasswordListFragment extends Fragment {

    private ListView passwordListView;

    public PasswordListFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.password_list_layout, container, false);
        passwordListView = (ListView) view.findViewById(R.id.password_list_list_view);
        PasswordListAdapter passwordListAdapter = null;
        try {
            passwordListAdapter = new PasswordListAdapter(getActivity(), PasswordListHandler.getInstance().objects, Settings.getInstance().isSaveLogin());
        } catch (Exception e) {
            Toast.makeText(getActivity(), getResourceString(R.string.error) + " " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        passwordListView.setAdapter(passwordListAdapter);

        return view;
    }

    public String getResourceString(int id) {
        return getActivity().getResources().getString(id);
    }
}
