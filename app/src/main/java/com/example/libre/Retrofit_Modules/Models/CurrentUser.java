package com.example.libre.Retrofit_Modules.Models;

import java.util.List;

public class CurrentUser {

    private List<Object> myorders = null;

    private List<Object> myproducts = null;

    private List<Object> ordered = null;

    private String id;

    private String username;

    private String name;

    private String token;

    private Boolean active;

    private Integer v;

    public List<Object> getMyorders() {
        return myorders;
    }

    public void setMyorders(List<Object> myorders) {
        this.myorders = myorders;
    }

    public List<Object> getMyproducts() {
        return myproducts;
    }

    public void setMyproducts(List<Object> myproducts) {
        this.myproducts = myproducts;
    }

    public List<Object> getOrdered() {
        return ordered;
    }

    public void setOrdered(List<Object> ordered) {
        this.ordered = ordered;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }
}
