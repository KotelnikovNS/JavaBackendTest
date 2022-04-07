package com.geekbrains;

import com.geekbrains.clients.MiniMarketClient;
import com.geekbrains.miniMarket.model.Product;
import com.geekbrains.miniMarket.model.ProductResponse;

import java.io.IOException;

public class MiniMarketMain {
    public static void main(String[] args) throws IOException {
        MiniMarketClient client = new MiniMarketClient();
        ProductResponse product1 = Product.builder()
                    .title("Bread")
                    .categoryTitle("Food")
                    .price(100)
                    .build();
        System.out.println(product1);
    }
}



