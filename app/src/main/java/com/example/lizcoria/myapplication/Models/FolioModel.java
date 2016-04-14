package com.example.lizcoria.myapplication.Models;

public class FolioModel {
    int id;
    String folio, expiration;

    public FolioModel(String folio) {
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

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    @Override
    public String toString() {
        return "FolioModel{" +
                "id=" + id +
                ", folio='" + folio + '\'' +
                ", expiration='" + expiration + '\'' +
                '}';
    }
}
