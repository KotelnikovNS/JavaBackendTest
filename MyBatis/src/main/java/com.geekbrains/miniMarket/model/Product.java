package com.geekbrains.miniMarket.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Product extends ProductResponse {

    private String category;
    private Long id;
    private String title;
    private Integer price;
    private String categoryTitle;


}
