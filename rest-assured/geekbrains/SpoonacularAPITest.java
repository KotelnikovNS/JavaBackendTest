package com.geekbrains.spoonacular;

import com.geekbrains.BaseTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.*;

public class SpoonacularAPITest extends BaseTest {

    private static final String API_KEY = "0b901035bc9243459ebe3b7969ede958";
    private static final String BASE_URL = "https://api.spoonacular.com";

    @BeforeAll
    static void beforeAll(){
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    void testGetComplexSearch() throws IOException {

        String actually = RestAssured.given()
                .queryParams("apiKey", API_KEY)
                .queryParams("query", "pasta")
                .queryParams("cuisine", "italian")
                .log()
                .uri()
                .expect()
                .log()
                .body()
                .statusCode(200)
                .time(lessThan(2000L))
                .body("results", contains(hasEntry(is("id"),is("654959"))))
                .body("results[0].id", notNullValue())
                .body("offset", is(0))
                .body("number", is(10))
                .body("totalResults", is(127))
                .when()
                .get("recipes/complexSearch")
                .body()
                .asPrettyString();

//        String expected = getResourceAsString("expected.json");
//
//        JsonAssert.assertJsonEquals(
//                expected,
//                actually,
//                JsonAssert.when(Option.IGNORING_ARRAY_ORDER)
//        );

    }

}
