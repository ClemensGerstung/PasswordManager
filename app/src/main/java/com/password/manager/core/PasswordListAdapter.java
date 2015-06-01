package com.password.manager.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.password.manager.R;
import com.password.manager.core.query.Query;

import java.util.LinkedList;
import java.util.List;

public class PasswordListAdapter extends BaseAdapter {

    private final List<Password> backupList;
    private List<Password> objects;
    private boolean saveLogin;
    private Context context;
    private final Object lock = new Object();

    public PasswordListAdapter(Context context, List<Password> objects, boolean saveLogin) {
        this.context = context;
        this.objects = new LinkedList<>(objects);
        this.backupList = new LinkedList<>(objects);
        this.saveLogin = saveLogin;

    }

    public void order(Query query) throws Exception {
        synchronized (lock) {
            objects.clear();
            objects.addAll(query.run(backupList));
        }

        notifyDataSetChanged();
    }

    public void order(String query) throws Exception {
        Query q = new Query(query);
        synchronized (lock) {
            objects.clear();
            objects.addAll(q.run(backupList));
        }

        notifyDataSetChanged();
    }

    public void reset() {
        synchronized (lock) {
            objects.clear();
            objects.addAll(backupList);
        }

        notifyDataSetChanged();
    }

    public void setObjects(List<Password> objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }

    public void add(Password pw){
        objects.add(pw);
        backupList.add(pw);
    }

    public void set(Password pw, int index){
        objects.set(index, pw);
        backupList.set(index, pw);
    }

    public void remove(int index) {
        objects.remove(index);
        backupList.remove(index);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View v = view;

        LayoutInflater vi = LayoutInflater.from(context);
        v = saveLogin ? vi.inflate(R.layout.password_list_item_save_login, null) : vi.inflate(R.layout.password_list_item_layout, null);

        TextView header = (TextView) v.findViewById(R.id.header_text_view);
        TextView username = (TextView) v.findViewById(R.id.user_name_text_view);
        TextView password = !saveLogin ? (TextView) v.findViewById(R.id.password_text_view) : null;

        if (position < objects.size()) {
            Password curr = objects.get(position);

            header.setText(curr.getProgram());
            username.setText(curr.getUsername());
            if (!saveLogin) password.setText(curr.getPassword());
        }

        return v;
    }



}
