package ru.stellarburgers.steps;

import java.util.List;

import io.restassured.response.Response;
import ru.stellarburgers.client.ApiClient;
import ru.stellarburgers.models.Ingredient;
import ru.stellarburgers.models.IngredientsListModel;

import static io.restassured.RestAssured.*;
import static ru.stellarburgers.data.OrderData.INGREDIENTS_PATH;

public class IngredientHelper {

    private static List<Ingredient> cashedIngredients = null;

    public List<Ingredient> getCashedIngredients() {
        if (cashedIngredients == null) {
            Response response = given()
                    .spec(ApiClient.getBaseSpec())
                    .get(INGREDIENTS_PATH);

            IngredientsListModel ingredientsListModel = response.as(IngredientsListModel.class);
            cashedIngredients = ingredientsListModel.getData();
        }
        return cashedIngredients;
    }
}
