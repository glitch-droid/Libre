package com.example.libre.Retrofit_Modules;

import com.example.libre.Retrofit_Modules.Models.AllProducts;
import com.example.libre.Retrofit_Modules.Models.BookmarkMessage;
import com.example.libre.Retrofit_Modules.Models.CurrentUser;
import com.example.libre.Retrofit_Modules.Models.EditProductFormat;
import com.example.libre.Retrofit_Modules.Models.LoginFormat;
import com.example.libre.Retrofit_Modules.Models.MessageFormat;
import com.example.libre.Retrofit_Modules.Models.PostCommentFormat;
import com.example.libre.Retrofit_Modules.Models.Products;
import com.example.libre.Retrofit_Modules.Models.RegisterFormat;
import com.example.libre.Retrofit_Modules.Models.UserDetails;
import com.example.libre.Retrofit_Modules.Models.VerificationFormat;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface API_Caller {
    @GET
    Call<List<Products>> getAllProducts(@Url String url);

    @POST("login")
    Call<String> loginUser(@Body LoginFormat loginFormat);

    @POST("register")
    Call<String> registerUser(@Body RegisterFormat registerFormat);

    @POST
    Call<MessageFormat> verifyUser(@Body VerificationFormat verificationFormat,@Url String url);

    @GET
    Call<UserDetails> getUserDetailsAfterLogin(@Url String url);

    @Multipart
    @POST
    Call<MessageFormat> createProduct(@Url String url,
                                      @Query("title") String title,
                                      @Query("description") String description,
                                      @Query("amount") String amount,
                                      @Query("bookauthor") String bookauthor,
                                      @Query("phoneNumber") String phoneNumber,
                                      @Query("area") String area,
                                      @Query("city") String city,
                                      @Query("state") String state,
                                      @Query("country") String country,
                                      @Part MultipartBody.Part image);

    @GET
    Call<Products> getProductFromID(@Url String url);

    @GET
    Call<CurrentUser> getUserFromID(@Url String url);

    @POST
    Call<MessageFormat> postComment(@Url String url, @Body PostCommentFormat commentFormat);

    @DELETE
    Call<ResponseBody> deleteComment(@Url String url);

    @DELETE
    Call<ResponseBody> deleteProduct(@Url String url);

    @PUT
    Call<MessageFormat> editProduct(@Url String url,@Body EditProductFormat product);

    @POST
    Call<MessageFormat> addBookmark(@Url String url,@Body BookmarkMessage message);

    @POST
    Call<MessageFormat> deleteBookmark(@Url String url, @Body BookmarkMessage message);

    @GET
    Call<ResponseBody> logoutUSer(@Url String url);

    @GET
    Call<List<AllProducts>> searchProducts(@Url String url);
}
