package com.password.manager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clemens on 15.09.2014.
 */
@Root(name="CompletePasswordFile")
public class AddOrEditPasswordAlertDialog {

    private View view;
    private AlertDialog alertDialog;
    private Context context;
    @ElementList(name="objects")
    private List<Password> objects;

    public AddOrEditPasswordAlertDialog(@ElementList(name="objects")List<Password> objects)
    {
        this.objects = objects;
        context = null;
        view = null;
    }

    public AddOrEditPasswordAlertDialog(View view, Context context, List<Password> objects)
    {
        this.view = view;
        this.context = context;
        this.objects = objects;
    }

    private void SerializeAndSavePasswords(int pos) throws Exception
    {
        String header = ((EditText) view.findViewById(R.id.header_edit_text)).getText().toString();
        String username = ((EditText) view.findViewById(R.id.user_name_input)).getText().toString();
        String password = ((EditText) view.findViewById(R.id.password_input)).getText().toString();

        if (header.length() > 0 && password.length() >= 8)
        {
            Password passwordObject = new Password(header, username, password);

            if(pos == -1)
            {
                objects.add(passwordObject);
            }
            else if(pos >= 0)
            {
                objects.set(pos, passwordObject);
            }


            Serializer serializer = new Persister(new AnnotationStrategy());

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
        else
        {
            Toast.makeText(context, "You must fill all textboxes", Toast.LENGTH_LONG).show();
        }
    }

    public AlertDialog CreateAlertDialog(String title, final int pos)
    {
        alertDialog = new AlertDialog.Builder(context)
                      .setView(view)
                      .setTitle(title)
                      .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {
                              dialogInterface.dismiss();
                          }
                      })
                      .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {
                              try {
                                  SerializeAndSavePasswords(pos);
                              } catch (Exception e) {
                                  Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                              }
                          }
                      })
                      .create();

        return alertDialog;
    }

    public ArrayList<Password> getObjects() {
        return new ArrayList<Password>(objects);
    }
}
