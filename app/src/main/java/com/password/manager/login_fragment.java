package com.password.manager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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


public class login_fragment extends Fragment {

    private EditText input_Name;
    private EditText input_Password;
    private Button submit_login;
    private Button create_user;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_login, container, false);

        input_Name = (EditText)v.findViewById(R.id.input_name);
        input_Password = (EditText)v.findViewById(R.id.input_password);
        submit_login = (Button)v.findViewById(R.id.login_button);
        create_user = (Button)v.findViewById(R.id.create_user);


        create_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_to_replace, new CreateUserFragment()).commit();
            }
        });

        submit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = Environment.getExternalStorageDirectory() + File.separator + "Android" + File.separator + "data" + File.separator + "com.password.manager" + File.separator + "USERS";

                File folder = new File(path);
                if(!folder.exists()){
                    Toast.makeText(getActivity(), "Folder doesn't exist! Create an user!", Toast.LENGTH_LONG).show();
                }
                else {
                    String user_name_string = input_Name.getText().toString();
                    String password_string = input_Password.getText().toString();

                    File user = new File(path + File.separator + user_name_string + ".xml");

                    if(!user.exists()){
                        Toast.makeText(getActivity(), "User doesn't exist! Create an user!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        BufferedReader br = null;
                        try {
                            br = new BufferedReader(new FileReader(user));

                            String line;

                            String user_file_content = "";

                            while ((line = br.readLine()) != null) {
                                user_file_content+=line;
                            }

                            int begin = user_file_content.indexOf("<password>") + ("<password>").length();
                            int end = user_file_content.indexOf("</password>");

                            String password = user_file_content.substring(begin, end);

                            String compare_password = encrypt(password_string, password_string);

                            if(compare_password.startsWith(password)){
                                User.password = password_string;
                                begin = user_file_content.indexOf("<path>") + ("<path>").length();
                                end = user_file_content.indexOf("</path>");
                                User.path = user_file_content.substring(begin, end);
                                begin = user_file_content.indexOf("<path>") + ("<path>").length();
                                end = user_file_content.indexOf("</path>");
                                User.username = user_file_content.substring(begin, end);

                                getFragmentManager().beginTransaction().replace(R.id.fragment_to_replace, new PasswordListFragment()).commit();
                            }
                            else {
                                Toast.makeText(getActivity(), "Wrong Password", Toast.LENGTH_LONG).show();
                            }

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (IllegalBlockSizeException e) {
                            e.printStackTrace();
                        } catch (InvalidKeyException e) {
                            e.printStackTrace();
                        } catch (BadPaddingException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (NoSuchPaddingException e) {
                            e.printStackTrace();
                        }

                    }
                }

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input_Password.getWindowToken(), 0);

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

}
