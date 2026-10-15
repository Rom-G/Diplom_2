package ru.stellarburgers.tests;

import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import ru.stellarburgers.models.*;

import static org.junit.Assert.*;
import static ru.stellarburgers.data.UserData.*;
import static ru.stellarburgers.steps.UserSteps.*;

public class UserLoginTest {

    @Test
    @DisplayName("Check if the user can log in")
    @Description("Basic test for /api/auth/login endpoint")
    public void loginUserSuccessTest() {
        UserRequestModel user = new UserRequestModel(EMAIL, PASSWORD, NAME);
        registerUser(user);
        UserAuthedModel userAuthed = loginUser().as(UserAuthedModel.class);

        assertTrue("The success field is not true", userAuthed.isSuccess());
        assertNotNull("AccessToken is null", userAuthed.getAccessToken());
        assertNotNull("RefreshToken is null", userAuthed.getRefreshToken());
        assertEquals("The email in the response is different", EMAIL, userAuthed.getUser().getEmail());
        assertEquals("The name in the response is different", NAME, userAuthed.getUser().getName());
    }
}
