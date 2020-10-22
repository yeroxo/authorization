package com.database.dataSet;
import javax.persistence.*;
@Entity
@Table(name= "users")
public class UserProfile {
    @Id
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;

    public UserProfile(){

    }

    public UserProfile(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getLogin() {return login;}
    public String getPassword() {return password;}
    public String getEmail() {return email;}
}
