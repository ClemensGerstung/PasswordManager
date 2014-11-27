package com.password.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Base64;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
public class PasswordListFragment extends Fragment {

    private List<Password> objects;
    private Button addNewPassword;
    private ListView listView;
    private String keyFile = "";
    private PasswordListAdapter adapter;

    public PasswordListFragment() {

        File f = new File(User.path);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));

            String line = "";
            while((line = reader.readLine()) != null){
                keyFile+=line;
            }

            if(keyFile.length() > 0){
                keyFile = AESHelper.decrypt(keyFile);

                Serializer serializer = new Persister(new AnnotationStrategy());
                StringReader stringReader = new StringReader(keyFile);
                AddOrEditPasswordAlertDialog addOrEditPasswordAlertDialog = serializer.read(AddOrEditPasswordAlertDialog.class, stringReader);
                this.objects = addOrEditPasswordAlertDialog.getObjects();
            }
            else {
                objects = new ArrayList<Password>();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.password_list_fragment, container, false);

        addNewPassword = (Button) v.findViewById(R.id.add_new_password);
        listView = (ListView) v.findViewById(R.id.listView);

        adapter = new PasswordListAdapter(getActivity(), R.layout.password_list_item_fragment, objects);

        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, final int position, long l) {
                PopupMenu popup = new PopupMenu(getActivity(), view);
                MenuInflater menuInflater = getActivity().getMenuInflater();
                menuInflater.inflate(R.menu.passwordeditmenu, popup.getMenu());
                popup.show();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if(id == R.id.menu_password_show) {
                            Password p = objects.get(position);
                            new AlertDialog.Builder(getActivity())
                                    .setMessage("Header: " + p.getHeader() + "\nUsername: " +p.getUsername() + "\nPassword: " + p.getPassword())
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .create()
                                    .show();

                            return true;
                        }
                        if(id == R.id.menu_password_edit) {
                            final LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                            final View view1 = layoutInflater.inflate(R.layout.add_new_password_fragment, null);
                            Button create_random_password = (Button)view1.findViewById(R.id.create_password);
                            create_random_password.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String s = new String(RandomPasswordGenerator.generatePswd(8,12,3,2,3));
                                    ((EditText)view1.findViewById(R.id.password_input)).setText(s, TextView.BufferType.NORMAL);
                                }
                            });
                            EditText header = (EditText)view1.findViewById(R.id.header_edit_text);
                            EditText username = (EditText)view1.findViewById(R.id.user_name_input);
                            EditText password = (EditText)view1.findViewById(R.id.password_input);

                            Password p = objects.get(position);

                            header.setText(p.getHeader(), TextView.BufferType.NORMAL);
                            username.setText(p.getUsername(), TextView.BufferType.NORMAL);
                            password.setText(p.getPassword(), TextView.BufferType.NORMAL);

                            AddOrEditPasswordAlertDialog addOrEditPasswordAlertDialog = new AddOrEditPasswordAlertDialog(view1, getActivity(), objects);
                            addOrEditPasswordAlertDialog.CreateAlertDialog("Edit Password", position).show();

                            adapter.notifyDataSetChanged();

                            return true;
                        }
                        if(id == R.id.menu_password_delete)
                        {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Delete")
                                    .setMessage("Do you really want to delete this entry?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            objects.remove(position);
                                            adapter.notifyDataSetChanged();

                                            Serializer serializer = new Persister(new AnnotationStrategy());

                                            try {
                                                File f = new File(User.path);
                                                f.delete();
                                                f.createNewFile();
                                                StringWriter writer = new StringWriter();
                                                serializer.write(this, writer);

                                                String s = AESHelper.encrypt(writer.toString(), User.password);

                                                OutputStream fo = new FileOutputStream(f);
                                                fo.write(s.getBytes());
                                                fo.close();
                                            }
                                            catch(Exception e)
                                            {

                                            }
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .create()
                                    .show();

                            return true;
                        }


                        return false;
                    }
                });

                return false;
            }
        });

        addNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                final View view1 = layoutInflater.inflate(R.layout.add_new_password_fragment, null);
                Button create_random_password = (Button)view1.findViewById(R.id.create_password);
                create_random_password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s = new String(RandomPasswordGenerator.generatePswd(8,12,3,2,3));
                        ((EditText)view1.findViewById(R.id.password_input)).setText(s, TextView.BufferType.NORMAL);
                    }
                });

                AddOrEditPasswordAlertDialog addOrEditPasswordAlertDialog = new AddOrEditPasswordAlertDialog(view1, getActivity(), objects);
                addOrEditPasswordAlertDialog.CreateAlertDialog("Add Password", -1).show();
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            new AlertDialog.Builder(getActivity())
                    .setMessage("Do you want logout?")
                    .setTitle("Logout")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            objects = null;
                            getFragmentManager().beginTransaction().replace(R.id.fragment_to_replace, new login_fragment()).commit();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create()
                    .show();


            return true;
        }



        return super.onOptionsItemSelected(item);
    }

}
