package at.campus02.nowa.uno;

import java.util.ArrayList;

public class Spieler {
    private String name;
    private int counterRestkarten;
    private int punkte;

    public Spieler(String name, int counterRestkarten, int punkte) {
        this.name = name;
        this.counterRestkarten = counterRestkarten;
        this.punkte = punkte;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCounterRestkarten() {
        return counterRestkarten;
    }

    public void setCounterRestkarten(int counterRestkarten) {
        this.counterRestkarten = counterRestkarten;
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    ArrayList<UnoKarte> blatt = new ArrayList<>();

   /* public void addCard(UnoKarte k){

        blatt.add(k);
    }

    public void removeCard(UnoKarte k){
        blatt.remove(k);
    }*/


    }

