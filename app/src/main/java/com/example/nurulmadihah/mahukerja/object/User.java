package com.example.nurulmadihah.mahukerja.object;


public class User {

    String id,firstname,lastname,email,pasword,username,userpass;
    public User() {}

    public User(String id, String firstname, String lastname, String email, String pasword, String username, String userpass) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.pasword = pasword;
        this.username = username;
        this.userpass =userpass;
    }


    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPasword() {
        return pasword;
    }

    public String getUsername() {
        return username;
    }


    public String getUserpass() {
        return userpass;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }
}
