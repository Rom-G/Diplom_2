package ru.stellarburgers.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthedModel {

    private boolean success;
    private String accessToken;
    private String refreshToken;
    private UserEmailName user;
}
