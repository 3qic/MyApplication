package com.example.myapplication;

public class ListItem {
    private String name;
    private String amount;

    public ListItem(String name, String amount){
        this.name = name;
        this.amount = amount;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount(){
        return amount;
    }

}
