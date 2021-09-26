package com.example.chatapp;

public class Users {

    String name;
    String age;
    String email;
    String status;

    public Users(String name, String age, String email, String status) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.status = status;
    }

    public Users() {
    }

    public Users(String name) {
        this.name = name;
    }

    public String getUsername() {
        return name;
    }
    public String getAge() {
        return age;
    }
    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
