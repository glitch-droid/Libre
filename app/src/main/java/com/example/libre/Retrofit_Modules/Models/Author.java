package com.example.libre.Retrofit_Modules.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Author {
        @SerializedName("id")
        private String id;
        @SerializedName("username")
        private String username;

        public Author(){

        }
        public String getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

}
