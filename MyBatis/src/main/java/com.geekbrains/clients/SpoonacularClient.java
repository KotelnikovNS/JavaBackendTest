package com.geekbrains.clients;

import com.geekbrains.api.ResponseUtils;
import com.geekbrains.api.SpoonacularApi;
import com.geekbrains.spoonaccular.model.AutoCompleteProductResponse;
import com.geekbrains.spoonaccular.model.SearchGroceryProductsRequestBuilder;
import com.geekbrains.spoonaccular.model.SearchGroceryProductsResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SpoonacularClient {

    private static final String API_URL = "https://api.spoonacular.com/";
    private static final String API_KEY = "0b901035bc9243459ebe3b7969ede958";

    private SpoonacularApi api;

    public SpoonacularClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(log::info);

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .readTimeout(1000, TimeUnit.MILLISECONDS)
                .writeTimeout(1000, TimeUnit.MILLISECONDS)
                .callTimeout(Duration.ofSeconds(1))
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        log.debug("client created!");

        this.api = retrofit.create(SpoonacularApi.class);
    }

    public SearchGroceryProductsResponse findAllProducts(
            SearchGroceryProductsRequestBuilder request
    ) {
        Call<SearchGroceryProductsResponse> responseCall = api.findAllProducts(
                API_KEY,
                request.getQuery(),
                request.getMinCalories(),
                request.getMaxCalories(),
                request.getMinCarbs(),
                request.getMaxCarbs(),
                request.getMinProtein(),
                request.getMaxProtein(),
                request.getMinFat(),
                request.getMaxFat(),
                request.getOffset(),
                request.getNumber()
        );
        return ResponseUtils.executeCall(responseCall);
    }

    public AutoCompleteProductResponse autocomplete(
            String query,
            Long number
    ) {
        Call<AutoCompleteProductResponse> responseCall = api.autocomplete(API_KEY, query, number);
        return ResponseUtils.executeCall(responseCall);
    }


}
