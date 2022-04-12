package com.geekbrains.spoonacular;

import com.geekbrains.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class SpoonacularAPITest extends BaseTest {

    private static final String API_KEY = "0b901035bc9243459ebe3b7969ede958";
    private static final String HASH = "8ccec44db03d298a0ab046ab08ecc9d5fdb81e1e";
    private static final String UN = "coxycucumber";
    private static final String BASE_URL = "https://api.spoonacular.com";
    String id;

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = BASE_URL;
    }


    @Test
    @Order(1)
    @DisplayName("Сomplex Search")
    void testGetComplexSearch1() throws IOException {

        String actually1 = given()
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
//                .body("results", contains(hasEntry(is("id"),is("654959"))))
//                .body("results[0].id", notNullValue())
                .body("offset", is(0))
                .body("number", is(10))
                .body("totalResults", is(127))
                .when()
                .get("recipes/complexSearch")
                .body()
                .asPrettyString();
    }

    @Test
    @Order(2)
    @DisplayName("Classify Grocery Product var1")
    void testPostClassifyGroceryProductVar1() throws IOException {
        String actually2 = given()
                .queryParam("apiKey", API_KEY)
                .body("{\n"
                        + " \"title\": \"Kroger Vitamin A & D Reduced Fat 2% Milk\",\n"
                        + " \"upc\": \"\",\n"
                        + " \"plu_code\": \"\",\n"
                        + "}")
                .when()
                .post(BASE_URL + "/food/products/classify")
                .then()
                .statusCode(200)
                .time(lessThan(1000L))
                .body("matched", is("2% milk"))
                .body("usdaCode", is(1174))
                .body("image", is("https://spoonacular.com/cdn/ingredients_100x100/milk.png"))
                .extract()
                .jsonPath()
                .toString();
    }

    @Test
    @Order(3)
    @DisplayName("Classify Grocery Product var2")
    void testPostClassifyGroceryProductVar2() throws JSONException {

        JSONObject requestBody = new JSONObject();
        requestBody.put("title", "Kroger Vitamin A & D Reduced Fat 2% Milk");
        requestBody.put("upc", "");
        requestBody.put("plu_code", "someRandomString");

        RequestSpecification request = given();
        request.queryParam("apiKey", API_KEY);
        request.queryParam("locale", "en-US");
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post(BASE_URL + "/food/products/classify");

        System.out.println(response.getBody().asString());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        long responseTime = response.getTime();
        Assert.assertEquals(responseTime, lessThan(1000L));
        String bodyContained = response.jsonPath().get("matched");
        Assert.assertEquals(bodyContained, "2% milk");
    }

    @Test
    @Order(4)
    @DisplayName("Add to Meal Plan")

    void addMealTest() throws IOException {
//        long ts = System.currentTimeMillis()/1000;
        id = given()
                .queryParam("hash", HASH)
                .queryParam("apiKey", API_KEY)
                .pathParam("username", UN)
                .body("{\n"
                        + " \"date\": 1649659661,\n"
                        + " \"slot\": 1,\n"
                        + " \"position\": 0,\n"
                        + " \"type\": \"INGREDIENTS\",\n"
                        + " \"value\": {\n"
                        + " \"ingredients\": [\n"
                        + " {\n"
                        + " \"name\": \"1 banana\"\n"
                        + " }\n"
                        + " ]\n"
                        + " }\n"
                        + "}")
                .when()
                .post("https://api.spoonacular.com/mealplanner/"+ UN +"/items"+HASH)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();
    }

    @AfterEach
    void tearDown() {
        given()
                .queryParam("hash", HASH)
                .queryParam("apiKey", API_KEY)
                .delete("https://api.spoonacular.com/mealplanner/"+ UN +"/items/" + id)
                .then()
                .statusCode(200);
    }
    @Test
    @DisplayName("Shopping List")

    void addShoppingMealTest() throws IOException {

        id = given()
                .queryParam("hash", HASH)
                .queryParam("apiKey", API_KEY)
                .pathParam("username", UN)
                .body("{\n"
                        + " \"item\": \"1 package baking powder\",\n"
                        + " \"aisle\": \"Baking\",\n"
                        + " \"parse\": true,\n"
                        + "}")
                .when()
                .post("/mealplanner/" + UN + "/shopping-list/items?hash="+ HASH)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();
    }

    @AfterTest
    void getShoppingMeal() {
        given()
                .queryParam("hash", HASH)
                .queryParam("apiKey", API_KEY)
                .get("/mealplanner/"+ UN + "/shopping-list/items/:" + id + "?hash=" + HASH)
                .then()
                .statusCode(200);
    }

    @AfterEach
    void dellShoppingMeal() {
        given()
                .queryParam("hash", HASH)
                .queryParam("apiKey", API_KEY)
                .body("{\n"
                        + " \"username\": \"coxycucumber\",\n"
                        + " \"id\": \"1050023\",\n"
                        + " \"hash\": \"8ccec44db03d298a0ab046ab08ecc9d5fdb81e1e\",\n"
                        + "}")
                .when()
                .delete("/mealplanner/" + UN + "/shopping-list/items?hash="+ HASH)
                .then()
                .statusCode(200);
    }
}





