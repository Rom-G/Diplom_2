package ru.stellarburgers.steps;

import java.util.List;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.stellarburgers.client.ApiClient;
import ru.stellarburgers.models.OrderCreateRequestModel;

import static io.restassured.RestAssured.given;
import static ru.stellarburgers.data.OrderData.ORDER_CREATE_PATH;

public class OrderSteps {

    @Step("Send POST request to /api/orders")
    public static Response createOrder(List<String> _idsIngredients, String accessToken) {
        RequestSpecification requestSpec = accessToken == null ?
                ApiClient.getBaseSpec() :
                ApiClient.getAuthSpec(accessToken);

        return given()
                .log().all()
                .spec(requestSpec)
                .body(new OrderCreateRequestModel(_idsIngredients))
                .when()
                .post(ORDER_CREATE_PATH);
    }
}
