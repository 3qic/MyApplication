package com.example.myapplication;

public class Recipe {

    public String Name;
    public String Kurzbeschreibung;
    public String RezeptID;


    public Recipe(){

    }

    public Recipe(String Name, String Kurzbeschreibung, String RezeptID) {
        this.Kurzbeschreibung = Kurzbeschreibung;
        this.Name = Name;
        this.RezeptID = RezeptID;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setKurzbeschreibung(String Kurzbeschreibung) {
        this.Kurzbeschreibung = Kurzbeschreibung;
    }

    public String getName() {
        return Name;
    }

    public String getKurzbeschreibung() {
        return Kurzbeschreibung;
    }

    public String getRezeptID() {
        return RezeptID;
    }

    public void setId(String RezeptID) {
        this.RezeptID = RezeptID;
    }

}
