package com.ya;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class CreateCoutierTest {
    private Client courierClient;
    private int courierId;
    Courier courier = Courier.getRandom();
    CourierCredentials credentials = new CourierCredentials(courier.login, courier.password);
    @Before
    public void setUp() {
        courierClient = new Client();

    }

    @After
    @Step
    public void tearDown() {
        courierId = courierClient.login(credentials);
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Creating a courier with correct data")
    public void courierCanBeCreatedWithValidDataTest() {
        courierClient.create(courier).then().assertThat().statusCode(201);
        boolean actual = courierClient.response.body().path("ok");
        assertTrue(actual);
    }

    @Test
    @DisplayName("Error when creating a courier with an existing username")
    public void courierErrorMessageWhenCreatingSimilarCourierTest() {
        courierClient.create(courier).then().assertThat().statusCode(201);
        courierClient.create(courier).then().assertThat().statusCode(409).body("message", equalTo("Этот логин уже используется"));
    }
}
