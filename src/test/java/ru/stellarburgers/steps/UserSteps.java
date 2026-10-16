package ru.stellarburgers.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.stellarburgers.models.UserRequestModel;
import ru.stellarburgers.client.ApiClient;

import static io.restassured.RestAssured.given;
import static ru.stellarburgers.data.UserData.*;

public class UserSteps {

    @Step("Send POST request to /api/auth/register")
    public static Response registerUser(String email, String password, String name) {
        UserRequestModel user = new UserRequestModel(email, password, name);
        return given()
                .spec(ApiClient.getBaseSpec())
                .body(user)
                .when()
                .post(USER_REGISTER_PATH);
    }

    @Step("Send POST request to /api/auth/login")
    public static Response loginUser(String email, String password) {
        UserRequestModel user = new UserRequestModel(email, password);
        return given()
                .spec(ApiClient.getBaseSpec())
                .body(user)
                .when()
                .post(USER_LOGIN_PATH);
    }

    @Step("Send DELETE request to /api/auth/user")
    public static void deleteUser(String accessToken) {
        given()
                .spec(ApiClient.getAuthSpec(accessToken))
                .when()
                .post(USER_PATH);
    }
}
