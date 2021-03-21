package com.example.libre.Retrofit_Modules.Models;

import java.util.List;

public class UserDetails {

    private List<Products> products = null;

    private CurrentUser currentUser;

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public CurrentUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }
}
