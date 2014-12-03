package com.password.manager.classes;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Clemens on 27.11.2014.
 */

/// a new class!
@Root(name = "CompletePasswordFile")
public class PasswordListHandler {
    @ElementList(name = "objects")
    public List<Password> objects;

    public PasswordListHandler(@ElementList(name = "objects") List<Password> objects) {
        this.objects = objects;
    }


}
