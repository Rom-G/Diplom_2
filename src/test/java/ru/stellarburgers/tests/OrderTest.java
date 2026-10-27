package ru.stellarburgers.tests;

import java.util.ArrayList;
import java.util.List;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import ru.stellarburgers.models.OrderCreateResponseModel;
import ru.stellarburgers.models.UserAuthedModel;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static ru.stellarburgers.steps.IngredientHelper.*;
import static ru.stellarburgers.steps.OrderSteps.createOrder;
import static ru.stellarburgers.steps.UserSteps.*;
import static ru.stellarburgers.data.OrderData.*;
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
    public void createOrderWithAuthAndIngredientsSuccessTest() {
        Response response = createOrder(ingredients, accessToken);
        OrderCreateResponseModel responseModel = response.as(OrderCreateResponseModel.class);
        List<String> actual_idsIngredients = getResponse_idsIngredients(responseModel.getOrder().getIngredients());

        response.then().log().all().statusCode(SC_CREATED);
        assertTrue("Success is not true", responseModel.isSuccess());
        assertNotNull("The name of the burger is null", responseModel.getName());
        assertEquals("The number of ingredients is not the same as expected",
                ingredients.size(), responseModel.getOrder().getIngredients().size());

        for (String ing : ingredients) {
            assertTrue("There is no " + ing + " in the ingredients list",
                    actual_idsIngredients.contains(ing));
        }

        assertEquals("The user's name is different", NAME, responseModel.getOrder().getOwner().getName());
        assertEquals("The email is different", EMAIL, responseModel.getOrder().getOwner().getEmail());
        assertTrue("The order number is zero", responseModel.getOrder().getNumber() > 0);
        assertTrue("The price of the order is zero", responseModel.getOrder().getPrice() > 0.009999f);
    }

    @Test
    @DisplayName("Check that the order can't be created without authorization")
    @Description("the test checks the not ability to place an order before authorization")
    public void createOrderWithoutAuthWithIngredientsFailsTest() {
        createOrder(ingredients, null)
                .then()
                .log().all()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", notNullValue());
    }

    @Test
    @DisplayName("Check the order can't be created without ingredients")
    @Description("The test verifies the processing of incorrect orders")
    public void createOrderWithAuthWithoutIngredientsFailsTest() {
        createOrder(new ArrayList<>(), accessToken)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                .body("message", equalTo(ORDER_IDS_REQUIRED));
    }

    @Test
    @DisplayName("Check the order can't be created with invalid id of ingredient")
    @Description("The test verifies the processing of incorrect orders")
    public void createOrderWithAuthAndInvalidIngredientFailsTest() {
        List<String> invalidIngredients = new ArrayList<>(
                List.of(INVALID_INGREDIENT_HASH_RANDOM,
                        INVALID_INGREDIENT_HASH_RANDOM_ALT));

        createOrder(invalidIngredients, accessToken)
                .then()
                .log().all()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @After
    public void tearDown() {
        UserAuthedModel userAuthed = loginUser(EMAIL, PASSWORD).as(UserAuthedModel.class);

        if (userAuthed.isSuccess()) {
            deleteUser(userAuthed.getAccessToken());
        }
    }
}
