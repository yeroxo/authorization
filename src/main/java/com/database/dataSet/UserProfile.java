package com.database.dataSet;
import javax.persistence.*;
@Entity
@Table(name= "users")
public class UserProfile {
    @Id
    @Column(name = "login")
    private String login;
    @Column(name = "passsword")
    private String passsword;
    @Column(name = "email")
    private String email;

    public UserProfile(){

    }

    public UserProfile(String login, String passsword, String email) {
        this.login = login;
        this.passsword = passsword;
        this.email = email;
    }

    public String getLogin() {return login;}
    public String getPasssword() {return passsword;}
    public String getEmail() {return email;}
}
