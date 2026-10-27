package ru.stellarburgers.tests;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import ru.stellarburgers.models.*;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static ru.stellarburgers.data.UserData.*;
import static ru.stellarburgers.steps.UserSteps.*;

public class UserLoginTest {

    @Before
    public void setUp() {
        registerUser(EMAIL, PASSWORD, NAME);
    }

    @Test
    @DisplayName("Check if the user can log in")
    @Description("Basic test for /api/auth/login endpoint")
    public void loginUserSuccessTest() {
        Response response = loginUser(EMAIL, PASSWORD);
        UserAuthedModel userAuthed = response.as(UserAuthedModel.class);

        response.then().log().all().statusCode(SC_OK);
        assertTrue("The success field is not true", userAuthed.isSuccess());
        assertNotNull("AccessToken is null", userAuthed.getAccessToken());
        assertNotNull("RefreshToken is null", userAuthed.getRefreshToken());
        assertEquals("The email in the response is different", EMAIL, userAuthed.getUser().getEmail());
        assertEquals("The name in the response is different", NAME, userAuthed.getUser().getName());
    }

    @Test
    @DisplayName("Check impossible of logging in with invalid login")
    @Description("The test checks the handling of incorrect requests")
    public void loginWithInvalidLoginFailsTest() {
        loginUser(EMAIL_UNREGISTERED, PASSWORD)
                .then()
                .log().all()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo(USER_INCORRECT_FIELDS));
    }

    @Test
    @DisplayName("Check impossible of logging in with invalid password")
    @Description("The test checks the handling of incorrect requests")
    public void loginWithInvalidPasswordFailsTest() {
        loginUser(EMAIL, "WrongPass!")
                .then()
                .log().all()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo(USER_INCORRECT_FIELDS));
    }

    @After
    public void tearDown() {
        UserAuthedModel userAuthed = loginUser(EMAIL, PASSWORD).as(UserAuthedModel.class);

        if (userAuthed.isSuccess()) {
            deleteUser(userAuthed.getAccessToken());
        }
    }
}
