package ru.stellarburgers.client;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ApiClient {

    public static final String BASE_URL = "https://stellarburgers.education-services.ru";

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setConfig(RestAssured.config()
                        .logConfig(LogConfig.logConfig()
                                .enablePrettyPrinting(true)))
                .build();
    }
}
