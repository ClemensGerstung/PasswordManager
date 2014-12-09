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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.password.manager.classes.Password;
import com.password.manager.classes.PasswordListHandler;
import com.password.manager.classes.Settings;
import com.password.manager.classes.User;


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
        int id = item.getItemId();
        switch (id){
            case R.id.menu_logout:
                User.logout();
                PasswordListHandler.logout();
                getFragmentManager().beginTransaction().replace(R.id.main_layout_fragment_to_replace, new LoginFragment()).commit();
                break;

            case R.id.menu_new_password_entry:
                AddEditPasswordHelper.addPassword(getActivity());
                break;

            case R.id.menu_change_password:

                break;
        }

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

        passwordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddEditPasswordHelper.showPassword(getActivity(), PasswordListHandler.getInstance().getObjects().get(position));
            }
        });

        return view;
    }

    public String getResourceString(int id) {
        return getActivity().getResources().getString(id);
    }
}
