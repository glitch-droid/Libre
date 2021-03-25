package com.example.libre.Retrofit_Modules.Models;

import java.util.List;

public class UserDetails {

    private List<AllProducts> products = null;

    private CurrentUser currentUser;

    public List<AllProducts> getProducts() {
        return products;
    }

    public void setProducts(List<AllProducts> products) {
        this.products = products;
    }

    public CurrentUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }
}
