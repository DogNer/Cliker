package com.example.clickertwo.Modal;

public class User {
    private String id;
    private String name;
    private String email;
    private String pass;
    private String score;

    public User(String id, String name, String email, String pass, String score) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.score = score;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
