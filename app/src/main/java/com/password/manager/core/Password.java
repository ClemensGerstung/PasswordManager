package com.password.manager.core;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.StringWriter;


/// One of the old classes
/// Just for using
@Root(name = "PasswordFile")
public class Password {

    public static final String PasswordREGEX = "^((?=.*\\d)(?=.*[a-zA-Z])[a-zA-Z0-9!*_+-/;:]{6,20})$";

    @Element(name = "header")
    public String program;
    @Element(name = "username")
    public String username;
    @Element(name = "password")
    public String password;

    public Password(@Element(name = "header") String program,
                    @Element(name = "username") String username,
                    @Element(name = "password") String password) {
        this.program = program;
        this.username = username;
        this.password = password;
    }

    public String serialize() throws Exception {
        String s = "";
        Serializer serializer = new Persister();
        StringWriter writer = new StringWriter();
        writer.append(s);
        serializer.write(this, writer);
        return writer.toString();
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String header) {
        this.program = header;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
