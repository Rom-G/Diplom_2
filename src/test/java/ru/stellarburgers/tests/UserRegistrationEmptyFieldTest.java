package ru.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.stellarburgers.models.UserAuthedModel;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.Matchers.equalTo;
import static ru.stellarburgers.data.UserData.*;
import static ru.stellarburgers.steps.UserSteps.*;

@RunWith(Parameterized.class)
public class UserRegistrationEmptyFieldTest {

    private final String email;
    private final String password;
    private final String name;

    public UserRegistrationEmptyFieldTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Object[][] getUserData() {
        return new Object[][] {
                { null, PASSWORD, NAME },
                { NAME, null, NAME },
                { NAME, PASSWORD, null }
        };
    }

    @Test
    @DisplayName("Check a user cannot be registered with an empty required field")
    @Description("The test verifies that it is impossible to create invalid users")
    public void registerUserWithEmptyFieldFailsTest() {
        registerUser(email, password, name)
                .then()
                .log().all()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo(USER_REQUIRED_FIELDS));
    }

    @After
    public void tearDown() {
        UserAuthedModel userAuthed = loginUser(email, password).as(UserAuthedModel.class);

        if (userAuthed.isSuccess()) {
            deleteUser(userAuthed.getAccessToken());
        }
    }
}
