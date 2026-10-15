package ru.stellarburgers.data;

import com.github.javafaker.Faker;

public class UserData {

    public static Faker user = new Faker(new java.util.Locale("ru"));
    public final static String EMAIL = user.internet().emailAddress();
    public final static String PASSWORD = user.name().lastName() + user.regexify("[0-9]{4}[!@#$%^&*]");
    public final static String NAME = user.name().firstName();

    public final static String USER_REGISTER_PATH = "/api/auth/register";
    public final static String USER_LOGIN_PATH = "/api/auth/login";
    public final static String USER_PATH = "/api/auth/user";


    public final static String USER_ALREADY_EXISTS = "User already exists";
    public final static String USER_REQUIRED_FIELDS = "Email, password and name are required fields";
}
