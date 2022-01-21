package com.ya;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest extends RestAssuredClient {
    private final String color;

    private String orderBody = "{\"firstName\": \"Naruto\", "
            + "\"lastName\": \"Uchiha\", "
            + "\"address\": \"Konoha, 142 apt.\", "
            + "\"metroStation\": 4, "
            + "\"phone\": \"+7 800 355 35 35\", "
            + "\"rentTime\": 5, "
            + "\"deliveryDate\": \"2020-06-06\", "
            + "\"comment\": \"Saske, come back to Konoha\", "
            + "\"color\":";

    public OrderCreateTest(String color) {
        this.color = color;
    }


    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{
                {"BLACK"},
                {"GREY"},
                {"BLACK\" , \"GREY"},
                {""}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Can create order with different colors")
    @Description("Checking that can use black, grey or both colors, or create order without color")

    public void createOrderTest() {
        Response response = given()
                .spec(getBaseSpec())
                .and()
                .body(orderBody + "[\"" + color + "\"]}")
                .when()
                .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }
}