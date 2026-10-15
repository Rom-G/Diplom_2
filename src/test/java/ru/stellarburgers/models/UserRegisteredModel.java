package ru.stellarburgers.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisteredModel {

    private boolean success;
    private UserModel user;
    private String accessToken;
    private String refreshToken;
}
