package com.example.lizcoria.myapplication.Models;

/**
 * Created by macbook on 06/05/16.
 */
public class ContestantModel {
    String url_image,asistentes;
    int learn, workshop,userid;

    public ContestantModel(int learn,String url_image, int workshop, int userid) {
        this.url_image = url_image;
        this.learn = learn;
        this.workshop = workshop;
        this.userid=userid;
    }

    public ContestantModel(int learn,String url_image,  int workshop, int userid, String asistentes) {
        this.url_image = url_image;
        this.learn = learn;
        this.workshop = workshop;
        this.userid = userid;
        this.asistentes = asistentes;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public int getWorkshop() {
        return workshop;
    }

    public void setWorkshop(int workshop) {
        this.workshop = workshop;
    }

    public int getLearn() {
        return learn;
    }

    public void setLearn(int learn) {
        this.learn = learn;
    }
}
