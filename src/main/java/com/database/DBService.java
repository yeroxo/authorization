package com.database;

import com.database.dao.UsersDAO;
import com.database.dataSet.UserProfile;
import org.h2.jdbcx.JdbcDataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;



public class DBService {

    private final Connection connection;

    public DBService() {
        this.connection = getH2Connection();
    }

    public UserProfile getUser(String login){
        return new UsersDAO(connection).get(login);
    }

    public void addUser(String login, String password, String email) throws SQLException {
        connection.setAutoCommit(false);
        UsersDAO dao = new UsersDAO(connection);
        dao.createTable();
        dao.insertUser(login, password, email);
        connection.commit();
//        return dao.getUserId(name);
    }

    public void cleanUp() throws SQLException {
        UsersDAO dao = new UsersDAO(connection);
        dao.dropTable();
    }

    public void printConnectInfo() throws SQLException {
        System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
        System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
        System.out.println("Driver: " + connection.getMetaData().getDriverName());
        System.out.println("Autocommit: " + connection.getAutoCommit());
    }

    public static Connection getH2Connection() {
        try {
            String url = "jdbc:h2:./h2db";
            String name = "tully";
            String pass = "tully";

            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(pass);

            return DriverManager.getConnection(url, name, pass);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}


//public class DBService {
//    private static final String hibernate_show_sql = "true";
//    private static final String hibernate_hbm2ddl_auto = "update";
//
//    private final SessionFactory sessionFactory;
//
//    public DBService() {
//        Configuration configuration = getH2Configuration();
//        sessionFactory = createSessionFactory(configuration);
//    }
//
//    private Configuration getH2Configuration() {
//        Configuration configuration = new Configuration();
//        configuration.addAnnotatedClass(UserProfile.class);
//
//        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
//        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
//        configuration.setProperty("hibernate.connection.username", "tully");
//        configuration.setProperty("hibernate.connection.password", "tully");
//        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
//        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
//        return configuration;
//    }
//
//
//    public UserProfile getUser(String login) {
//        try {
//            Session session = sessionFactory.openSession();
//            UsersDAO dao = new UsersDAO(session);
//            UserProfile dataSet = dao.get(login);
//            session.close();
//            return dataSet;
//        } catch (HibernateException e) {
//            throw null;
//        }
//    }
//
//    public void addUser(String login, String password, String email) {
//        try {
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
//            UsersDAO dao = new UsersDAO(session);
//            dao.insertUser(login, password, email);
//            transaction.commit();
//            session.close();
//        } catch (HibernateException e) {
//            throw new HibernateException("asdadas");
//        }
//    }
//
//    private static SessionFactory createSessionFactory(Configuration configuration) {
//        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
//        builder.applySettings(configuration.getProperties());
//        ServiceRegistry serviceRegistry = builder.build();
//        return configuration.buildSessionFactory(serviceRegistry);
//    }
//}

