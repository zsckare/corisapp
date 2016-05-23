package com.example.lizcoria.myapplication.Models;

/**
 * Created by macbook on 05/05/16.
 */
public class WorkshopModel {
    int id;
    String folio;

    public WorkshopModel(int id, String folio) {
        this.id = id;
        this.folio = folio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }
}

