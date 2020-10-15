package com.main.tech.Accounts;

import com.database.DBService;
import com.database.dataSet.UserProfile;
import org.hibernate.HibernateException;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static Map<String, UserProfile> sessionBase = new HashMap<>();
    private DBService base = new DBService();

    public AccountService() {
    }

    public boolean AddNewUser(String login, String password, String email) {
        UserProfile user = new UserProfile(login, password, email);
        if (base.getUser(login) != null) {
            return false;
        }
        try {
            base.addUser(user.getLogin(), user.getPasssword(), user.getEmail());
        } catch (HibernateException e) {
            return false;
        }
        return true;
    }

    public boolean AuthorizateUser(UserProfile authProfile, String sessionID) {
        UserProfile baseProfile = base.getUser(authProfile.getLogin());
        if (baseProfile != null) {
            if (baseProfile.getPasssword().compareTo(authProfile.getPasssword()) == 0) {
                sessionBase.put(sessionID, baseProfile);
                return true;
            }
            return false;
        }
        return false;
    }

    public String getLoginBySessionId(String sessionID) {
        if (sessionBase.containsKey(sessionID)) {
            return sessionBase.get(sessionID).getLogin();
        }
        return null;
    }

    public boolean CheckSessionId(String sessionID) {
        if (sessionBase.containsKey(sessionID)) {
            return true;
        }
        return false;
    }

    public boolean Quit(String sessionID) {
        if (sessionBase.containsKey(sessionID)) {
            sessionBase.remove(sessionID);
            return true;
        }
        return false;
    }
}
