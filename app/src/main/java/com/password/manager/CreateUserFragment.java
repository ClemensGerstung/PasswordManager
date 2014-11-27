package com.password.manager;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class CreateUserFragment extends Fragment {

    private Button create_user_button;
    private Button return_button;

    private String path = Environment.getExternalStorageDirectory() + File.separator + "Android" + File.separator + "data" + File.separator + "com.password.manager" + File.separator + "USERS";
    private String pathToKeys = Environment.getExternalStorageDirectory() + File.separator + "Android" + File.separator + "data" + File.separator + "com.password.manager" + File.separator + "KEYS";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }
        File folderToKey = new File(pathToKeys);
        if (!folderToKey.exists()) {
            folderToKey.mkdir();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.create_user_fragment, container, false);
        create_user_button = (Button)v.findViewById(R.id.create_user_button);
        create_user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText name = (EditText)v.findViewById(R.id.name_edit_text);
                EditText password = (EditText)v.findViewById(R.id.password_edit_text);

                String pw = password.getText().toString();

                boolean hasSpecial = !pw.matches("^.([0-9a-zA-Z\\W])");

                if(hasSpecial) {

                    CharSequence name_charsequence = name.getText();
                    String password_charsequence = password.getText().toString();

                    XmlSerializer xmlSerializer = Xml.newSerializer();
                    StringWriter writer = new StringWriter();

                    try {
                        xmlSerializer.setOutput(writer);

                        xmlSerializer.startDocument("UTF-8", true);
                        xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

                        xmlSerializer.startTag("", "user");

                        xmlSerializer.startTag("", "name");
                        xmlSerializer.text(name_charsequence.toString());
                        xmlSerializer.endTag("", "name");

                        xmlSerializer.startTag("", "password");
                        xmlSerializer.text(encrypt(password_charsequence, password_charsequence));
                        xmlSerializer.endTag("", "password");

                        File keyfile = new File(pathToKeys + File.separator + name_charsequence.toString() + ".xml");
                        keyfile.createNewFile();

                        xmlSerializer.startTag("", "path");
                        xmlSerializer.text(pathToKeys + File.separator + name_charsequence.toString() + ".xml");
                        xmlSerializer.endTag("", "path");


                        xmlSerializer.endTag("", "user");
                        xmlSerializer.endDocument();

                        File file = new File(path + File.separator + name_charsequence.toString() + ".xml");
                        if (!file.exists()) {
                            file.createNewFile();
                            OutputStream fo = new FileOutputStream(file);
                            fo.write(writer.toString().getBytes());
                            fo.close();
                        }
                        else {
                            Toast.makeText(getActivity(), "User already exists! Choose anothe name", Toast.LENGTH_LONG).show();
                        }

                        getFragmentManager().beginTransaction().replace(R.id.fragment_to_replace, new login_fragment()).commit();

                    } catch (IOException e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IllegalBlockSizeException e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (InvalidKeyException e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (BadPaddingException e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (NoSuchAlgorithmException e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (NoSuchPaddingException e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Password must contains special characters, numbers and upper and lower chars", Toast.LENGTH_LONG).show();
                }
            }
        });

        return_button = (Button)v.findViewById(R.id.return_button);
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_to_replace, new login_fragment()).commit();
            }
        });

        return v;
    }

    private String encrypt(String text, String key_string) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String encrypted_string = "";

        byte[] key = (key_string).getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("MD5");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 32);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(text.getBytes());

        encrypted_string = Base64.encodeToString(encrypted, Base64.DEFAULT);

        return encrypted_string;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
