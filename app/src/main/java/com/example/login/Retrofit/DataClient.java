package com.example.login.Retrofit;

import com.example.login.Login.Result;
import com.example.login.Menu.Grade.Grade;
import com.example.login.Menu.Grade.HTML;
import com.example.login.Menu.Info.Info;
import com.example.login.Menu.TKB.TKB;

import com.example.login.Menu.CheckIn.CheckInList;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataClient {
    @GET("api/test/")
    Call<String> get();
    @GET("android")
    Call<String> getJson();
    @GET("android/array")
    Call<ArrayList<String>> getArray();

    @GET("api/login")
    Call<String>  signin( @Query("mahv") String mahv, @Query("password") String password);
///real
    @FormUrlEncoded
    @POST("api/login")
    Call<Result> login (@Field("mahv") String mahv, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/reset")
    Call<String> reset(@Field("mahv") String mahv);

    @FormUrlEncoded
    @POST("api/setpassword")
    Call<String> setPassword(@Field("mahv") String mahv, @Field("password") String password, @Field("token") String token);

    @GET("/api/diemdanh/{TOKEN}/{MAHV}/{MAC}")
    Call <String> checkIn(@Path("TOKEN") String TOKEN, @Path("MAHV") String MAHV, @Path("MAC") String MAC);

    @GET("/api/checkInInfor/{MAHV}")
    Call <ArrayList<CheckInList>> getCheckInInfor(@Path("MAHV") String MAHV);



    //Timetable
    @GET("api/tkb/{MAHV}")
    Call<List<TKB>> getTKB(@Path("MAHV") String MAHV);

    //Grade
//    @GET("api/grade/{MAHV}")
//    Call<List<Grade>> getGrade(@Path("MAHV") String MAHV);
    @GET("api/grade/{MAHV}")
    Call<HTML> getGrade(@Path("MAHV") String MAHV);

    //Info
//    @GET("api/info/{MAHV}")
//    Call<Info> getInfo(@Path("MAHV") String MAHV);

    @GET("api/info/{MAHV}")
    Call<List<Info>> getInfo(@Path("MAHV") String MAHV);



}
