package com.password.manager;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.password.manager.core.Logger;
import com.password.manager.core.Password;
import com.password.manager.core.PasswordListAdapter;
import com.password.manager.core.Settings;
import com.password.manager.core.User;
import com.password.manager.core.handler.PasswordListHandler;
import com.password.manager.gui.helper.AddEditPasswordHelper;
import com.password.manager.gui.helper.ChangeMasterPasswordHelper;

public class PasswordListFragment extends Fragment {

    private ListView passwordListView;
    private BottomButtonFragment bottomButtonFragment;
    private PasswordListAdapter passwordListAdapter;

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
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_logout:
                User.logout();
                PasswordListHandler.logout();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_layout_fragment_to_replace, new LoginFragment())
                        .commit();
                break;
            case R.id.menu_change_password:
                try {
                    ChangeMasterPasswordHelper.ChangeMasterPassword(getActivity());
                } catch (Exception e) {
                    Logger.show(e.getMessage(), getActivity());
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = null;
        view = inflater.inflate(R.layout.password_list_layout, container, false);

        passwordListView = (ListView) view.findViewById(R.id.password_list_list_view);

        try {
            passwordListAdapter = new PasswordListAdapter(getActivity(), PasswordListHandler.getInstance().objects, Settings.getInstance().isSaveLogin());
        } catch (Exception e) {
            Logger.show(e.getMessage(), getActivity());
        }
        passwordListView.setAdapter(passwordListAdapter);

        passwordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    AddEditPasswordHelper.showPassword(
                            getActivity(),
                            (Password) passwordListAdapter.getItem(position),
                            position,
                            passwordListAdapter);
                } catch (Exception e) {
                    Logger.show(e.getMessage(), getActivity());
                }
            }
        });

        bottomButtonFragment = new BottomButtonFragment();
        if (bottomButtonFragment != null) {
            bottomButtonFragment.setPasswordListAdapter(passwordListAdapter);
        }


        getActivity()
                .getFragmentManager()
                .beginTransaction()
                .add(R.id.password_list_relative_layout, bottomButtonFragment)
                .commit();

        return view;
    }
}
