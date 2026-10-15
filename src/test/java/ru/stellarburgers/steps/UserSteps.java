package ru.stellarburgers.steps;

import java.util.Collections;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.stellarburgers.models.UserRequestModel;
import ru.stellarburgers.client.ApiClient;

import static io.restassured.RestAssured.given;
import static ru.stellarburgers.data.UserData.*;

public class UserSteps {

    @Step("Send POST request to /api/auth/register")
    public static Response registerUser(UserRequestModel user) {
        return given()
                .spec(ApiClient.requestSpec())
                .body(user)
                .when()
                .post(USER_REGISTER_PATH);
    }

    @Step("Send POST request to /api/auth/login")
    public static Response loginUser() {
        UserRequestModel user = new UserRequestModel(EMAIL, PASSWORD);
        return given()
                .spec(ApiClient.requestSpec())
                .body(user)
                .when()
                .post(USER_LOGIN_PATH);
    }

    @Step("Send DELETE request to /api/auth/user")
    public static void deleteUser(String accessToken) {
        given()
                .spec(ApiClient.requestSpec())
                .body(Collections.singletonMap("authorization", accessToken))
                .when()
                .post(USER_PATH);
    }
}
