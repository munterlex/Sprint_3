package com.ya;

import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class IncorrectCreateCourierTest {
    private Client courierClient;

    @Before
    public void setUp() {
        courierClient = new Client();

    }
    @Test
    @DisplayName("Error when creating a courier without a login")
    public void createCourierWithNullLogin(){
        Courier courier = new Courier(null, "1111Po", "Gorro");
        courierClient.create(courier).then().assertThat().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Error when creating a courier without a password")
    public void createCourierWithNullPassword(){
        String login = "xxxxxx" + RandomStringUtils.randomAlphabetic(10);
        Courier courier = new Courier(login, null, "Gorro");
        courierClient.create(courier).then().assertThat().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Error when creating a courier without a first name")
    public void createCourierWithNullFirstname(){
        String login = "xxxxxx" + RandomStringUtils.randomAlphabetic(10);
        Courier courier = new Courier(login, "1111Po", null);
        courierClient.create(courier).then().assertThat().statusCode(201);
    }
}
