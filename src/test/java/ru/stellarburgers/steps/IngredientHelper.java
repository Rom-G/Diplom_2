package ru.stellarburgers.steps;

import java.util.*;

import io.restassured.response.Response;
import ru.stellarburgers.client.ApiClient;
import ru.stellarburgers.models.Ingredient;
import ru.stellarburgers.models.IngredientsListModel;

import static io.restassured.RestAssured.*;
import static ru.stellarburgers.data.OrderData.INGREDIENTS_PATH;

public class IngredientHelper {

    private static List<Ingredient> cachedIngredients = null;

    public static List<Ingredient> getCachedIngredients() {
        if (cachedIngredients == null) {
            Response response = given()
                    .spec(ApiClient.getBaseSpec())
                    .get(INGREDIENTS_PATH);

            IngredientsListModel ingredientsListModel = response.as(IngredientsListModel.class);
            cachedIngredients = ingredientsListModel.getData();
        }

        return cachedIngredients;
    }

    public static List<Ingredient> getRandomIngredients(int count, long seed) {
        List<Ingredient> list = getCachedIngredients();

        if (list.isEmpty()) {
            throw new IllegalStateException("No ingredients available");
        }
        if (count > list.size()) {
            throw new IllegalArgumentException("Cannot pick more ingredients than available: " + list.size());
        }

        Random random = new Random(seed);
        List<Ingredient> result = new ArrayList<>();
        List<Ingredient> shuffled = new ArrayList<>(list);
        Collections.shuffle(shuffled, random);

        for (int i = 0; i < count; i++) {
            result.add(shuffled.get(i));
        }

        return result;
    }
}
