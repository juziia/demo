package com.juzi;

public class User {

    private String username;
    private int id;
    private String abc;

    public User(String username, int id, String abc) {
        this.username = username;
        this.id = id;
        this.abc = abc;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", id=" + id +
                ", abc='" + abc + '\'' +
                '}';
    }
}
