package com.geekbrains;

import com.geekbrains.clients.SpoonacularClient;
import com.geekbrains.spoonaccular.model.AutoCompleteProductResponse;
import com.geekbrains.spoonaccular.model.SearchGroceryProductsRequestBuilder;
import com.geekbrains.spoonaccular.model.SearchGroceryProductsResponse;

public class Main {

    public static void main(String[] args) {

        SpoonacularClient client = new SpoonacularClient();

        AutoCompleteProductResponse pasta = client.autocomplete("pasta", 3L);

        System.out.println(pasta);

        SearchGroceryProductsResponse products = client.findAllProducts(
                SearchGroceryProductsRequestBuilder.builder()
                        .query("pasta")
                        .minCalories(10L)
                        .maxCalories(1000L)
                        .number(3L)
                        .build()
        );

        products.getProducts().forEach(System.out::println);
    }
}
