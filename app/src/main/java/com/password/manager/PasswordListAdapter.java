package com.password.manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class PasswordListAdapter extends ArrayAdapter<Password> {

    private Context context;
    private int resource;
    private List<Password> objects;

    public PasswordListAdapter(Context context, int resource, List<Password> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    public void setObjects(List<Password> objects) {
        this.objects = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View v = view;



        LayoutInflater vi = LayoutInflater.from(getContext());
        v = vi.inflate(R.layout.password_list_item_fragment, null);



        TextView header = (TextView)v.findViewById(R.id.header_text_view);
        TextView username = (TextView)v.findViewById(R.id.user_name_text_view);
        TextView password = (TextView)v.findViewById(R.id.password_text_view);

        Password curr = objects.get(position);

        header.setText(curr.getHeader());
        username.setText(curr.getUsername());
        password.setText(curr.getPassword());


        return v;
    }
}
