package at.campus02.nowa.uno;

import java.util.ArrayList;

import java.util.Random;

public class Spieler {
    private String name;
    //private int counterRestkarten;
    private int punkte;
    private ArrayList<UnoKarte> handKarten;

    public Spieler(String name) {
        this.name = name;

        this.punkte = 0;    //anfänglich immer 0
        handKarten = new ArrayList<>();
    }


    public ArrayList<UnoKarte> getHandKarten(){

        return handKarten;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    @Override
    public String toString() {
        return "Spieler{" +
                "name='" + name + '\'' +
                ", punkte=" + punkte +
                ", handKarten=" + handKarten +
                '}';
    }
}

