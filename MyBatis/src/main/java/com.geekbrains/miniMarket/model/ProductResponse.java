package com.geekbrains.miniMarket.model;


public class ProductResponse {

    private Category category;
    private Product product;
    private String error;

    public boolean ok() {return product != null;}

    public Category getCategory() {return category;}

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product getProduct() {return product;}

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
