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

        public void setId(String id) {
                this.id = id;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

}
