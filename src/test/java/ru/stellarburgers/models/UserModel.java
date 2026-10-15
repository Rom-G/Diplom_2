package ru.stellarburgers.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserModel {

    private String email;
    private String password;
    private String name;

    public UserModel(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
