package ru.stellarburgers.client;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ApiClient {

    private static final String BASE_URL = "https://stellarburgers.education-services.ru";

    private static RequestSpecification baseSpec;

    public static RequestSpecification getBaseSpec() {
        if (baseSpec == null) {
            baseSpec = new RequestSpecBuilder()
                    .setBaseUri(BASE_URL)
                    .setContentType(ContentType.JSON)
                    .setConfig(RestAssured.config()
                            .logConfig(LogConfig.logConfig()
                                    .enablePrettyPrinting(true)))
                    .build();
        }
        return baseSpec;
    }

    public static RequestSpecification getAuthSpec(String token) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addHeader("Authorization", token)
                .setContentType(ContentType.JSON)
                .setConfig(RestAssured.config()
                        .logConfig(LogConfig.logConfig()
                                .enablePrettyPrinting(true)))
                .build();
    }
}
