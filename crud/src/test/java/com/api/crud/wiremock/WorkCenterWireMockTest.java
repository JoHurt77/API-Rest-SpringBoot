package com.api.crud.wiremock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;

class WorkCenterWireMockTest {
    @BeforeEach
    void setUp() {
        // Establece la URL base para tu servidor WireMock
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    @DisplayName("Should return ok when try to connect to the localhost 8080")
    void testOne() {
        // Realiza una solicitud GET al endpoint especificado
        RestAssured.given()
                .get("/employee")
                .then()
                .assertThat()
                .statusCode(200);
    }
}
