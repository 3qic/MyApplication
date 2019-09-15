package com.example.myapplication;

public class Recipe {

    private String name;
    private String kurzbeschreibung;
    private String anleitung;
    private String zutaten;
    private String arbeitszeit;
    private String rezeptid;


    public Recipe(){

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKurzbeschreibung() {
        return kurzbeschreibung;
    }

    public void setKurzbeschreibung(String kurzbeschreibung) {
        this.kurzbeschreibung = kurzbeschreibung;
    }

    public String getAnleitung() {
        return anleitung;
    }

    public void setAnleitung(String anleitung) {
        this.anleitung = anleitung;
    }

    public String getZutaten() {
        return zutaten;
    }

    public void setZutaten(String zutaten) {
        this.zutaten = zutaten;
    }

    public String getArbeitszeit() {
        return arbeitszeit;
    }

    public void setArbeitszeit(String arbeitszeit) {
        this.arbeitszeit = arbeitszeit;
    }

    public String getRezeptid() {
        return rezeptid;
    }

    public void setRezeptid(String rezeptid) {
        this.rezeptid = rezeptid;
    }
}
