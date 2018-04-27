package com.netcracker.ibublig.catalog.model;

import java.io.Serializable;

public class User implements Serializable {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private boolean admin;
    private boolean reg;

    public User(String firstName, String lastName, String login, String password, boolean admin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.admin = admin;
        reg = true;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;

        firstName = "Auth";
        lastName = "Auth";
        admin = false;
        reg = false;
    }

    public User(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isReg() {
        return reg;
    }

    public void setReg(boolean reg) {
        this.reg = reg;
    }

    @Override
    public String toString() {
        return "User{}";
    }
}
