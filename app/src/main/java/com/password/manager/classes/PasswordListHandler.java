package com.password.manager.classes;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clemens on 27.11.2014.
 */

/// a new class!
@Root(name = "CompletePasswordFile")
public class PasswordListHandler {
    private static PasswordListHandler passwordListHandler;
    @ElementList(name = "objects")
    public List<Password> objects;


    public PasswordListHandler(@ElementList(name = "objects") List<Password> objects) {
        this.objects = objects;
    }

    public static PasswordListHandler getInstance() {
        if (passwordListHandler == null)
            passwordListHandler = new PasswordListHandler(new ArrayList<Password>());

        return passwordListHandler;
    }

    public static PasswordListHandler createPasswordListHandlerFromString(String data) throws Exception {
        passwordListHandler = PMSerializer.deserialize(data, PasswordListHandler.class);
        return passwordListHandler;
    }

    public static void logout()
    {
        passwordListHandler.objects = null;
        passwordListHandler = null;
    }

    public List<Password> getObjects() {
        return objects;
    }

    public void addAndSave(Password password) throws Exception {
        objects.add(password);
        String ser = PMSerializer.serialize(this);
        String en = AESHelper.encrypt(ser, User.getInstance("").getPassword());
        PathHandler.writeFile(User.getInstance("").getPath(), en);
    }
}
