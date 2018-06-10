package com.fa7.controle;

import android.content.Context;
import android.util.Log;

import com.fa7.modelo.User;
import com.fa7.persistence.FilePersistence;

public class ControlUser {

    private User user;

    public User getUser() {
        return user;
    }

    public static void SaveUser(Context context, User user){
        FilePersistence.saveText(context, "Config", user.getLogin() + "|" + user.getPassword());
    }

    public ControlUser(Context context)
    {
        try {
            String config = FilePersistence.getText(context, "Config");
            if(config.length()>0) {
                String[] part = config.split("\\|");
                user = new User(part[0], part[1]);
            }
        }
        catch (Exception e)
        {
            Log.e("LoadConfig", e.getMessage());
        }
    }
}
