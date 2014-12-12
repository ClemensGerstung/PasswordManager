package com.password.manager.handler;

import com.password.manager.core.Password;
import com.password.manager.core.User;

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
        data = data.replace("<username></", "<username> </");

        passwordListHandler = SerializerHandler.deserialize(data, PasswordListHandler.class);
        return passwordListHandler;
    }

    public static void logout()
    {
        if(!isLoggedIn()) return;
        passwordListHandler.objects = null;
        passwordListHandler = null;
    }

    public List<Password> getObjects() {
        return objects;
    }

    public void addAndSave(Password password) throws Exception {
        objects.add(password);
        save();
    }

    public static boolean isLoggedIn(){
        return passwordListHandler != null;
    }

    public void save() throws Exception {
        String ser = SerializerHandler.serialize(this);
        String en = AESHandler.encrypt(ser, User.getInstance("").getPassword());
        PathHandler.writeFile(User.getInstance("").getPath(), en);
    }
}
