package com.ya;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.equalTo;

public class CourierLoginTest{
    private Client courierClient;
    private int courierId;
    Courier courier = Courier.getRandom();
    CourierCredentials courierCredentials = new CourierCredentials(courier.login, courier.password);

    @Before
    public void setUp() {
        courierClient = new Client();
    }

    @After
    public void tearDown() {
        courierId = courierClient.login(courierCredentials);
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Successful authorization")
    public void courierCanNotBeCreatedWithSimilarLoginTest() {
        courierClient.create(courier);
        courierId = courierClient.login(new CourierCredentials(courier.login, courier.password));
        assert courierId > 0;
    }

    @Test
    @DisplayName("Error when logging in without a login")
    public void courierCanNotLoginWithoutLoginTest() {
        courierClient.create(courier);
        courierClient.loginBodyResponse(new CourierCredentials(null, courier.password));
        courierClient.response.then().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Error when logging in without a password")
    public void courierCanNotLoginWithoutPasswordTest() {
        courierClient.create(courier);
        courierClient.loginBodyResponse(new CourierCredentials(courier.login , null));
        courierClient.response.then().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Error when logging with incorrect login")
    public void courierCanNotLoginWithInvalidLoginTest() {
        courierClient.create(courier);
        String innvalidLogin = "rjiopt";
        courierClient.loginBodyResponse(new CourierCredentials(innvalidLogin, courier.password));
        courierClient.response.then().statusCode(404).assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Error when logging with incorrect password")
    public void courierCanNotLoginWithInvalidPasswordTest() {
        courierClient.create(courier);
        String innvalidPassword = "rjiopt";
        courierClient.loginBodyResponse(new CourierCredentials(courier.login, innvalidPassword));
        courierClient.response.then().statusCode(404).assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

}
