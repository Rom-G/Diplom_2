package ru.stellarburgers.tests;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import ru.stellarburgers.models.OrderCreateResponseModel;
import ru.stellarburgers.models.UserAuthedModel;

import static org.junit.Assert.*;
import static ru.stellarburgers.steps.IngredientHelper.*;
import static ru.stellarburgers.steps.OrderSteps.createOrder;
import static ru.stellarburgers.steps.UserSteps.*;
import static ru.stellarburgers.data.UserData.*;

public class OrderTest {

    private List<String> ingredients;
    private String accessToken;

    @Before
    public void setUp() {
        registerUser(EMAIL, PASSWORD, NAME);
        accessToken = loginUser(EMAIL, PASSWORD)
                .as(UserAuthedModel.class)
                .getAccessToken();

        ingredients =  getRandom_idsIngredients(4, 123L);
    }

    @Test
    @DisplayName("Check the creation of an order with authorization with ingredients")
    @Description("Basic test for /api/orders endpoint")
    public void createOrderWithAuthAndIngredients() {
        OrderCreateResponseModel response = createOrder(ingredients, accessToken).as(OrderCreateResponseModel.class);
        List<String> actual_idsIngredients = getResponse_idsIngredients(response.getOrder().getIngredients());

        assertTrue("Success is not true", response.isSuccess());
        assertNotNull("The name of the burger is null", response.getName());
        assertEquals("The number of ingredients is not the same as expected",
                ingredients.size(), response.getOrder().getIngredients().size());

        for (String ing : ingredients) {
            assertTrue("There is no " + ing + " in the ingredients list",
                    actual_idsIngredients.contains(ing));
        }

        assertEquals("The user's name is different", NAME, response.getOrder().getOwner().getName());
        assertEquals("The email is different", EMAIL, response.getOrder().getOwner().getEmail());
        assertTrue("The order number is zero", response.getOrder().getNumber() > 0);
        assertTrue("The price of the order is zero", response.getOrder().getPrice() > 0.009999f);
    }

    @After
    public void tearDown() {
        UserAuthedModel userAuthed = loginUser(EMAIL, PASSWORD).as(UserAuthedModel.class);

        if (userAuthed.isSuccess()) {
            deleteUser(userAuthed.getAccessToken());
        }
    }
}
