package com.example.libre.Retrofit_Modules.Models;

import com.google.gson.annotations.SerializedName;

public class CommentsAuthor {
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;

        public CommentsAuthor(){

        }
        public String getId() {
            return id;
        }

        public String getUsername() {
            return name;
        }

}
