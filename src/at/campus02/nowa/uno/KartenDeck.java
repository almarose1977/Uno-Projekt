package at.campus02.nowa.uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/* Definition der Karten:
 108 Karten: 4 Farben Rot, Grün, Yellow, Blau zu je 1x 0, 2x 1-9, 2x +2, 2x Richtungswechsel RW, 2x Aussetzen OUT
 zusätzlich 4x Schwarz Wild und 4x Schwarz +4
 */

public class KartenDeck {

    private List<UnoKarte> kartenDeck;                      // eine Liste vom Typ UnoKarte wird deklariert

    private int index = 0;                                  // ein kartenindex wird erstellt

    public KartenDeck() {                                   // Konstruktor

        kartenDeck = new ArrayList<>();                     // die ArrayListe wird initialisiert
    }

    public void makeDeck() {                                // Methode zum Befüllen des Karten-Arrays
        Farbe[] colors = Farbe.values();                    // ein Farbarray wird erstellt mit den Werten der Enums
                                                            // (Rot, Grün, Blau, Yellow, Schwarz)
        Kartenwert[] values = Kartenwert.values();          // ein Kartenarray mit den Werten der Enums erstellen

        // i = Farbindex = 0, length-1, weil schwarz nicht mit iteriert werden soll, i = 0 bedeutet ROT
        for (int i = 0; i < colors.length - 1; i++) {

            kartenDeck.add(new UnoKarte(colors[i], Kartenwert.getWert(0)));     //todo: UnoKarte[0] = rot 0
            index++;                                                            //kartenindex 1

            // j = werteindex, endet bei .length -2, weil WILD + PLUS 4 nicht mititeriert werden
            for (int j = 1; j < values.length - 2; j++) {                       // j = werteindex = 1          | j=2                    ..| j=9                   | j=10                    | j=11                          | j=12
                kartenDeck.add(new UnoKarte(colors[i], Kartenwert.getWert(j))); // todo: UnoKarte[1] = rot 1   | UnoKarte[3] = rot 2    ..| UnoKarte[17] = rot 9  | UnoKarte[19] = rot +2   | UnoKarte[21] = rot RW         | UnoKarte[23] = rot OUT
                index++;                                                        //kartenindex 2                | kartenindex 4          ..| kartenindex 18        | kartenindex 20          | kartenindex 22                | kartenindex 24
                kartenDeck.add(new UnoKarte(colors[i], values[j]));             // todo: UnoKarte[2] = rot 1   | UnoKarte[4] = rot 2    ..| UnoKarte[18] = rot 9  | UnoKarte[20] = rot +2   | karUnoKarteten[22] = rot RW   | UnoKarte[24] = rot OUT
                index++;                                                        //kartenindex 3                | kartenindex 5          ..| kartenindex 19        | kartenindex 21          | kartenindex 23                | kartenindex 25
                                                                                // gehen in der 1. inneren for-schleife weiter
                                                                                //--> j=2                      | j=3                    ..| j=10                  | j=11                    | j=12                        ..| j=13= values.length --> fertig
            } //  --> 1.innere for schleife fertig --> farbindex = 1 (grün) --> die grünen karten werden erstellt, usw.
        }

        // schwarze Farbwahlkarte WILD
        for (int i = 0; i < 4; i++) {
            kartenDeck.add(new UnoKarte(colors[4], values[values.length - 2]));
            index++;
        }

        // schwarze Plus 4
        for (int i = 0; i < 4; i++) {
            kartenDeck.add(new UnoKarte(colors[4], values[values.length-1]));
            index++;
        }

        // Ausgabe index + Karte
        /*for (int i = 0; i < kartenDeck.size(); i++){
            System.out.println(i + " " + kartenDeck.get(i).toString());
        }*/

        //Ausgabe nur karte
        for (UnoKarte u : kartenDeck) {
            System.out.println(u);
        }
    }

    public void shuffleDeck(){
        Collections.shuffle(kartenDeck);

        for (UnoKarte u : kartenDeck) {
            System.out.println(u);
        }
    }

    // todo: random rausfinden
    public ArrayList<UnoKarte> makePlayerDeck(ArrayList<UnoKarte> kartenDeck, int count){
        /*
        nimm die gemischten Karte und ziehe zufällig 7 Karten daraus und steck sie in die ArrayListe HandKarten
        */
        Random random = new Random();
        if (kartenDeck.isEmpty()){
            throw new IllegalArgumentException("Kartenstapel ist leer, mischen Sie die Karten des Ablagestapels neu.");
        }
        if (count > kartenDeck.size()){
            throw new IllegalArgumentException("Es sind nicht genug Karten am Stapel. Mischen Sie die Karten des " +
                    "Ablagestapels neu.");
        }
        ArrayList<UnoKarte> handKarten = new ArrayList<>();
        for (int i = 0; i < count; i++){
           // handKarten.add(getRandomItem(kartenDeck));
        }
        return handKarten;
    }
}