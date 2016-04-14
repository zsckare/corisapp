package com.example.lizcoria.myapplication.ApiConnections;

import com.example.lizcoria.myapplication.Models.FolioModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiFolioRequests {

    @GET("/folios")
    void checkFolio(@Query("folio") String folio, Callback<FolioModel>response);

}
