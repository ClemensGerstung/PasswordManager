package com.password.manager;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class BottomSearchFragment extends Fragment {

    private EditText searchText;
    private ImageButton closeButton;


    public BottomSearchFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_search_fragment, container, false);
        closeButton = (ImageButton) view.findViewById(R.id.bottom_search_close_button);
        searchText = (EditText) view.findViewById(R.id.bottom_search_search_text_edit_text);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.password_list_relative_layout, new BottomButtonFragment())
                        .commit();
            }
        });

        return view;
    }


}
