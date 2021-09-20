package com.example.mypassword.model;

public class Account implements Comparable<Account>{

    private String id;
    private String servizio;
    private String username;
    private String password;
    private String appunti;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServizio() {
        return servizio;
    }

    public void setServizio(String servizio) {
        this.servizio = servizio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppunti() { return appunti; }

    public void setAppunti(String appunti) { this.appunti = appunti; }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", servizio='" + servizio + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", appunti='" + appunti + '\'' +
                '}';
    }

    @Override
    public int compareTo(Account a) {
        if (getServizio() == null || a.getServizio() == null) {
            return 0;
        }

        return getServizio().compareTo(a.getServizio());
    }

}
