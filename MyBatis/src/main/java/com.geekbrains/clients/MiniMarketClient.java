package com.geekbrains.clients;

import com.geekbrains.api.MiniMarketApi;
import com.geekbrains.miniMarket.model.Product;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

@Slf4j
public class MiniMarketClient {

    private final MiniMarketApi api;

    public MiniMarketClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://minimarket1.herokuapp.com/market/v2/api-docs/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.api = retrofit.create(MiniMarketApi.class);
    }


    public List<Product> getCategory() throws IOException {
        return api.getProducts()
                .execute()
                .body();
    }
    public List<Product> getProducts() throws IOException {
        return api.getProducts()
                .execute()
                .body();
    }

    public Product getProduct(long id) throws IOException {
        Response<Product> response = api.getProduct(id).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new RuntimeException(response.errorBody().string());
        }
    }

    public Long createProduct(Product product) throws IOException {
        return api.createProduct(product)
                .execute()
                .body()
                .getId();
    }

    public void deleteProduct(long id) throws IOException {
        api.deleteProduct(id).execute();
    }

    public void updateProduct(Product product) throws IOException {
        api.updateProduct(product).execute();
    }

}
