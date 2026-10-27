package ru.stellarburgers.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserRequestModel {

    private String email;
    private String password;
    private String name;

    public UserRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
