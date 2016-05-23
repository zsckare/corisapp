package com.example.lizcoria.myapplication.ApiConnections;

import com.example.lizcoria.myapplication.Models.ContestantModel;
import com.example.lizcoria.myapplication.Models.WorkshopModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Query;

public interface ApiFolioRequests {

    @GET("/workshops/validate")
    void checkFolio(@Query("folio") String folio, Callback<List<WorkshopModel>>response);

    @POST("/contestants")
    void createPost(@Body ContestantModel contestantModel, Callback<ContestantModel> reponse);

}
