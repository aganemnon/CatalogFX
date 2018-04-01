package ru.netcracker.ibublig.server.model;

public class User {
    private String fistName;
    private String lastName;
    private String login;
    private String password;
    private boolean admin;

    public User(String fistName, String lastName, String login, String password, boolean admin) {
        this.fistName = fistName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.admin = admin;
    }

    public User() {
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
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
}
