package com.database.dao;

import com.database.dataSet.UserProfile;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class UsersDAO {

    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public UserProfile get(String login) throws HibernateException {
        return (UserProfile) session.get(UserProfile.class, login);
    }

    public String insertUser(String login, String passsword, String email) throws HibernateException {
        return (String) session.save(new UserProfile(login, passsword, email));
    }
}
