package com.main.tech.Accounts;

import com.database.DBService;
import com.database.dataSet.UserProfile;
import org.hibernate.HibernateException;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class AccountService {
    private DBService base = new DBService();

    public AccountService() {
    }

    public boolean AddNewUser(String login, String password, String email) {
        UserProfile user = new UserProfile(login, password, email);
        if (base.getUser(login) != null) {
            return false;
        }
        try {
            base.addUser(user.getLogin(), user.getPassword(), user.getEmail());
        } catch (SQLException e) { //Hibernate exception
            return false;
        }
        return true;
    }

    public boolean FindUser(String login){
        if(base.getUser(login) == null){
            return true;
        }
        return false;
    }

    public boolean AuthorizateUser(UserProfile authProfile, HttpSession sessionID) {
        UserProfile baseProfile = base.getUser(authProfile.getLogin());
        if (baseProfile != null) {
            if (baseProfile.getPassword().compareTo(authProfile.getPassword()) == 0) {
                sessionID.setAttribute("user", baseProfile);
                return true;
            }
            return false;
        }
        return false;
    }

    public String getLoginBySessionId(HttpSession sessionID) {
        if (sessionID.getAttribute("user") != null) {
            return ((UserProfile) sessionID.getAttribute("user")).getLogin();
        }
        return null;
    }

    public boolean CheckSessionId(HttpSession sessionID) {
        if (sessionID.getAttribute("user") != null) {
            return true;
        }
        return false;
    }

    public boolean Quit(HttpSession sessionID) {
        if (sessionID.getAttribute("user") != null) {
            sessionID.removeAttribute("user");
            return true;
        }
        return false;
    }
}
