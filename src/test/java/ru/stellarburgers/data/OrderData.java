package ru.stellarburgers.data;

import com.github.javafaker.Faker;

public class OrderData {

    public static Faker order = new Faker();
    public static final String INVALID_INGREDIENT_HASH_RANDOM = order.regexify("[a-z0-9]{24}");
    public static final String INVALID_INGREDIENT_HASH_RANDOM_ALT = order.regexify("[a-z0-9]{24}");

    public static final String INGREDIENTS_GET_PATH = "/api/ingredients";
    public static final String ORDER_CREATE_PATH = "/api/orders";

    public static final String ORDER_IDS_REQUIRED = "Ingredient ids must be provided";
}
