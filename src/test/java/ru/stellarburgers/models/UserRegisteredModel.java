package ru.stellarburgers.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisteredModel {

    private boolean success;
    private UserEmailName user;
    private String accessToken;
    private String refreshToken;
}
