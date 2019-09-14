package com.example.myapplication;

public class Lieblingsrezept {

    private String name;
    private String rezeptid;
    private String kurzbeschreibung;


    public Lieblingsrezept() {
    }


    public String getRezeptid() {
        return rezeptid;
    }

    public void setRezeptid(String rezeptid) {
        this.rezeptid = rezeptid;
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
}
