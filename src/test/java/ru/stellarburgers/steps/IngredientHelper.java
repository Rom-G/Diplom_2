package ru.stellarburgers.steps;

import java.util.*;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.stellarburgers.client.ApiClient;
import ru.stellarburgers.models.Ingredient;
import ru.stellarburgers.models.IngredientsResponseModel;

import static io.restassured.RestAssured.*;
import static ru.stellarburgers.data.OrderData.INGREDIENTS_GET_PATH;

public class IngredientHelper {

    private static List<Ingredient> cachedIngredients = null;

    @Step("Send GET request to /api/ingredients")
    public static List<Ingredient> getCachedIngredients() {
        if (cachedIngredients == null) {
            Response response = given()
                    .spec(ApiClient.getBaseSpec())
                    .get(INGREDIENTS_GET_PATH);

            IngredientsResponseModel ingredientsResponseModel = response.as(IngredientsResponseModel.class);
            cachedIngredients = ingredientsResponseModel.getData();
        }

        return cachedIngredients;
    }

    @Step("Get the _id of random ingredients by quantity")
    public static List<String> getRandom_idsIngredients(int count, long seed) {
        List<Ingredient> list = getCachedIngredients();

        if (list.isEmpty()) {
            throw new IllegalStateException("No ingredients available");
        }
        if (count > list.size()) {
            throw new IllegalArgumentException("Cannot pick more ingredients than available: " + list.size());
        }

        Random random = new Random(seed);
        List<String> result = new ArrayList<>();
        List<Ingredient> shuffled = new ArrayList<>(list);
        Collections.shuffle(shuffled, random);

        for (int i = 0; i < count; i++) {
            result.add(shuffled.get(i).get_id());
        }

        return result;
    }

    @Step("Get the _id of ingredients from response")
    public static List<String> getResponse_idsIngredients(List<Ingredient> ingredients) {
        List<String> result = new ArrayList<>();

        for (Ingredient ing : ingredients) {
            result.add(ing.get_id());
        }

        return result;
    }
}
