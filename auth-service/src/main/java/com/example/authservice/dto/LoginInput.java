package com.example.authservice.dto;

public class LoginInput {

    private String password;
    private String email;

    public LoginInput () {}

    public String getPassword () { return password; }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
