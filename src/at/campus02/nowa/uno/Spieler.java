package at.campus02.nowa.uno;

import java.util.ArrayList;

import java.util.LinkedList;
import java.util.Random;

public class Spieler {
    private String name;
    //private int counterRestkarten;
    private int punkte;
    private LinkedList<UnoKarte> handKarten;

    public Spieler(String name) {
        this.name = name;

        this.punkte = 0;    //anfänglich immer 0
        handKarten = new LinkedList<>();
    }

    public boolean hasCard(UnoKarte k)
    {
        boolean hasCard = false;
        for (UnoKarte handKarte:handKarten) {
            if(handKarte.getFARBE() == k.getFARBE() && handKarte.getKARTENWERT() == k.getKARTENWERT())
            {
                hasCard = true;
            }
        }
        return hasCard;
    }


    public UnoKarte getKarte(Kartenwert kw, Farbe f)
    {
        for (UnoKarte handKarte:handKarten) {
            if(handKarte.getFARBE() == f && handKarte.getKARTENWERT() == kw)
            {
                return handKarte;
            }
        }
        return null;
    }


    public LinkedList<UnoKarte> getHandKarten(){

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

    public void setHandKarten(LinkedList<UnoKarte> handKarten) {
        this.handKarten = handKarten;
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

