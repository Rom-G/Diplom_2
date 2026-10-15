package ru.stellarburgers.tests;

import org.junit.Test;
import org.junit.After;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import ru.stellarburgers.models.UserAuthedModel;
import ru.stellarburgers.models.UserRequestModel;
import ru.stellarburgers.models.UserRegisteredModel;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static ru.stellarburgers.data.UserData.*;
import static ru.stellarburgers.steps.UserSteps.*;

public class UserRegistrationTest {

    @Test
    @DisplayName("Check a user can be registered")
    @Description("Basic test for /api/auth/register endpoint")
    public void registerUserSuccessTest() {
        UserRequestModel user = new UserRequestModel(EMAIL, PASSWORD, NAME);
        UserRegisteredModel userRegistered = registerUser(user).as(UserRegisteredModel.class);

        assertTrue("The success field is not true", userRegistered.isSuccess());
        assertEquals("The email in the response is different", EMAIL, userRegistered.getUser().getEmail());
        assertEquals("The name in the response is different", NAME, userRegistered.getUser().getName());
        assertNotNull("AccessToken is null", userRegistered.getAccessToken());
        assertNotNull("RefreshToken is null", userRegistered.getRefreshToken());
    }

    @Test
    @DisplayName("Check the existing user cannot be registered")
    @Description("The test verifies that it is impossible to create duplicates of users")
    public void registerExistingUserFailsTest() {
        UserRequestModel user = new UserRequestModel(EMAIL, PASSWORD, NAME);
        registerUser(user);
        registerUser(user)
                .then()
                .log().all()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo(USER_ALREADY_EXISTS));
    }

    @After
    public void tearDown() {
        UserAuthedModel userAuthed = loginUser().as(UserAuthedModel.class);

        if (userAuthed.isSuccess()) {
            deleteUser(userAuthed.getAccessToken());
        }
    }
}
