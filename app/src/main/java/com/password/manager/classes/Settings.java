package com.password.manager.classes;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Clemens on 03.12.2014.
 */

@Root(name = "settings")
public class Settings {
    private static Settings settings;
    @Element(name = "saveLogin")
    private boolean saveLogin;
    @Element(name = "rememberedUserName")
    private String rememberedUserName;

    public boolean isSaveLogin() {
        return saveLogin;
    }

    public void setSaveLogin(boolean saveLogin) {
        this.saveLogin = saveLogin;
    }

    public String getRememberedUserName() {
        return rememberedUserName;
    }

    public void setRememberedUserName(String rememberedUserName) {
        this.rememberedUserName = rememberedUserName;
    }

    private Settings(@Element(name = "rememberedUserName") String rememberedUserName,
                     @Element(name = "saveLogin") boolean saveLogin) {
        this.rememberedUserName = rememberedUserName;
        this.saveLogin = saveLogin;
    }

    public static Settings getInstance() {
        if (settings == null) {

        }

        return settings;
    }


}
