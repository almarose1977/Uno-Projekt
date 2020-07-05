package at.campus02.nowa.uno;

import java.util.ArrayList;

import java.util.Random;

public class Spieler {
    private String name;
    private int counterRestkarten;
    private int punkte;
    private ArrayList<UnoKarte> handKarten;

    public Spieler(String name, int counterRestkarten, int punkte) {
        this.name = name;
        this.counterRestkarten = counterRestkarten;
        this.punkte = punkte;
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

    public ArrayList<UnoKarte> makePlayerDeck(ArrayList<UnoKarte> kartenDeck) {

        ArrayList<UnoKarte> handKarten = new ArrayList<>();
        int count = 7;
        Random random = new Random();

        /*if (kartenDeck.isEmpty()){              // wenn der Stapel leer ist
            throw new IllegalArgumentException("Kartenstapel ist leer, mischen Sie die Karten des Ablagestapels neu.");
        }
        if (count > kartenDeck.size()){         // wenn der Stapel nicht mehr genug Karten hat
            throw new IllegalArgumentException("Es sind nicht genug Karten am Stapel. Mischen Sie die Karten des " +
                    "Ablagestapels neu.");
        }*/
        for (int i = 0; i < count; i++) {
            // aus dem bestehenden Kartendeck wird ein zufälliges Element ausgewählt, das nicht größer sein darf
            // als die Größe des Kartendecks
            UnoKarte randomElement = kartenDeck.get(random.nextInt(kartenDeck.size()));
            handKarten.add(randomElement);  // dieses zufällige Element wird dem HandKarten-Set hinzugefügt
        }

        for (UnoKarte u : handKarten) {     // nur zum Anzeigen der Handkarten
            System.out.println(u);
        }
        return handKarten;
    }
}

