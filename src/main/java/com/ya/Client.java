package com.ya;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class Client extends RestAssuredClient {

    private static final String COURIER_PATH = "api/v1/courier/";
    Response response;

    @Step
    public Response create(Courier courier) {
        return response = given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH);

    }

    @Step
    public int login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login/")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    @Step
    public boolean delete(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }

    @Step
    public Response loginBodyResponse(CourierCredentials credentials) {
        return response =
                given()
                        .spec(getBaseSpec())
                        .body(credentials)
                        .when()
                        .post(COURIER_PATH + "login/");

    }

}
