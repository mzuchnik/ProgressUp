package com.example.progressup;

public class UserRowData {


    private int id;
    private String name;
    private String login;
    private String surname;

    public UserRowData(){}

    public UserRowData(int id, String name, String login, String surname) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
