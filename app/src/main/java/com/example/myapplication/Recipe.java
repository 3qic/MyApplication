package com.example.myapplication;

public class Recipe {

    private String Name;
    private String Kurzbeschreibung;
    private String Anleitung;
    private String Zutaten;
    private static int id = 0;


    public Recipe(){
        id++;
    }

    public static int getId(){
        return id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getKurzbeschreibung() {
        return Kurzbeschreibung;
    }

    public void setKurzbeschreibung(String kurzbeschreibung) {
        Kurzbeschreibung = kurzbeschreibung;
    }

    public String getAnleitung() {
        return Anleitung;
    }

    public void setAnleitung(String anleitung) {
        Anleitung = anleitung;
    }

    public String getZutaten() {
        return Zutaten;
    }

    public void setZutaten(String zutaten) {
        Zutaten = zutaten;
    }
}
