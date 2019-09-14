package com.example.myapplication;

import java.util.ArrayList;

public class User {


    private String nutzerID, name;
    ArrayList<String>lieblingsrezepte;


    public User (){

    }


    public String getNutzerID() {
        return nutzerID;
    }

    public void setNutzerID(String nutzerID) {
        this.nutzerID = nutzerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getLieblingsrezepte() {
        return lieblingsrezepte;
    }

    public void setLieblingsrezepte(ArrayList<String> lieblingsrezepte) {
        this.lieblingsrezepte = lieblingsrezepte;
    }
}
