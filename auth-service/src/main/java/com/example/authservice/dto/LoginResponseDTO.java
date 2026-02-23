package com.example.authservice.dto;

import com.example.authservice.model.User;

public class LoginResponseDTO {

    private String token;
    private UserDTO user;

    public LoginResponseDTO (User user, String token) {
        this.token = token;
        this.user = new UserDTO(user);
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public UserDTO getUser  () { return user;}
    public void setUser(UserDTO user) {
        this.user = user;
    }

}
