package com.password.manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.password.manager.classes.Password;

import java.util.List;


public class PasswordListAdapter extends ArrayAdapter<Password> {
    
    private List<Password> objects;
    private boolean saveLogin;

    public PasswordListAdapter(Context context, int resource, List<Password> objects, boolean saveLogin) {
        super(context, resource, objects);
        this.objects = objects;
        this.saveLogin = saveLogin;
    }

    public void setObjects(List<Password> objects) {
        this.objects = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View v = view;

        LayoutInflater vi = LayoutInflater.from(getContext());
        v = saveLogin ? vi.inflate(R.layout.password_list_item_save_login, null) : vi.inflate(R.layout.password_list_item_layout, null);
        
        TextView header = (TextView)v.findViewById(R.id.header_text_view);
        TextView username = (TextView)v.findViewById(R.id.user_name_text_view);
        TextView password = !saveLogin ? null : (TextView)v.findViewById(R.id.password_text_view);

        Password curr = objects.get(position);

        header.setText(curr.getHeader());
        username.setText(curr.getUsername());
        if(!saveLogin) password.setText(curr.getPassword());


        return v;
    }
}
