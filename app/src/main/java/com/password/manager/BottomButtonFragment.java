package com.password.manager;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.password.manager.core.PasswordListAdapter;
import com.password.manager.gui.helper.AddEditPasswordHelper;
import com.password.manager.gui.helper.OrderPasswordListHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class BottomButtonFragment extends Fragment {

    private Button orderButton;
    private Button addButton;
    private Button searchButton;
    private PasswordListAdapter passwordListAdapter;

    public BottomButtonFragment() {

    }

    public void setPasswordListAdapter(PasswordListAdapter passwordListAdapter) {
        this.passwordListAdapter = passwordListAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottom_button_fragment, container, false);

        orderButton = (Button) view.findViewById(R.id.password_list_button_line_order_button);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderPasswordListHelper.orderPasswordList(getActivity(), v, passwordListAdapter);
            }
        });

        addButton = (Button) view.findViewById(R.id.password_list_button_line_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEditPasswordHelper.addPassword(getActivity());
            }
        });


        searchButton = (Button) view.findViewById(R.id.password_list_button_line_search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                BottomSearchFragment bsf = new BottomSearchFragment();
                bsf.setPasswordListAdapter(passwordListAdapter);

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.password_list_relative_layout, bsf)
                        .commit();
            }
        });


        return view;
    }


}
