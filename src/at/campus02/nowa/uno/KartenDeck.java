package at.campus02.nowa.uno;

import java.util.*;

/* Definition der Karten:
 108 Karten: 4 Farben Rot, Grün, Yellow, Blau zu je 1x 0, 2x 1-9, 2x +2, 2x Richtungswechsel RW, 2x Aussetzen OUT
 zusätzlich 4x Schwarz Wild und 4x Schwarz +4
 */

public class KartenDeck {

    private final ArrayList<UnoKarte> CardDeck;         // die kompletten Karten werden in eine AL gespeichert
    public Queue<UnoKarte> gameCardDeck;                // für die jeweiligen Runden werden die gemischten Karten in eine Queue gespeichert

    public KartenDeck() {

        CardDeck = new ArrayList<>();
        gameCardDeck = new LinkedList<>();
    }

    // ~~~~~~ Methode zum Befüllen des Karten-Arrays ~~~~~~
    public void makeDeck() {
        Farbe[] colors = Farbe.values();                    // Farbarray mit den Werten der Enums (Rot, Grün, Blau, Yellow, Schwarz)
        Kartenwert[] values = Kartenwert.values();          // Kartenarray mit den Werten der Enums

        int cardIndex = 0;                                  // Kartenindex

        // i = Farbindex = 0, length-1, weil schwarz nicht mit iteriert werden soll, i = 0 bedeutet ROT
        for (int i = 0; i < colors.length - 1; i++) {

            CardDeck.add(new UnoKarte(colors[i], Kartenwert.getWert(0)));     // Null gibt es nur jeweils 1x (UnoKarte[0] = rot 0)
            cardIndex++;

            // j = werteindex, endet bei .length -2, weil WILD + PLUS 4 nicht mititeriert werden
            for (int j = 1; j < values.length - 2; j++) {
                CardDeck.add(new UnoKarte(colors[i], values[j]));
                cardIndex++;
                CardDeck.add(new UnoKarte(colors[i], values[j]));
                cardIndex++;
            } // innere for schleife fertig --> farbindex = 1 (grün) --> die grünen karten werden erstellt, usw.
        }

        // schwarze Farbwahlkarte WILD 4x
        for (int i = 0; i < 4; i++) {
            CardDeck.add(new UnoKarte(colors[4], values[values.length - 2]));
            cardIndex++;
        }

        // schwarze Plus 4, 4x
        for (int i = 0; i < 4; i++) {
            CardDeck.add(new UnoKarte(colors[4], values[values.length-1]));
            cardIndex++;
        }

        //Ausgabe nur benötigt zur Prüfung, ob die Kartenerstellung funktioniert hat
        /*for (UnoKarte u : CardDeck) {
            System.out.println(u);
        }*/
    }

    // Methode zum Mischen der Karten
    public void shuffleDeck(){
        Collections.shuffle(CardDeck);

        // nur für Testzwecke, ob die Methode funktioniert
        /*for (UnoKarte u : kartenDeck) {
            System.out.println(u);
        }
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");*/

        gameCardDeck.addAll(CardDeck);     // alle gemischten Karten aus der ArrayList werden der Queue hinzugefügt

        // nur für Testzwecke
        /*for (UnoKarte u : gameCardDeck){     // gemischte Karten ausgeben lassen
            System.out.println(u);
        }*/
    }

    // Methode: von den gemischten Karten die 7 obersten Karten nehmen und sie der Liste HandKarten hinzufügen
    public LinkedList<UnoKarte> makePlayerDeck(){

        LinkedList<UnoKarte> handCards = new LinkedList<>();
        int count = 7;

        // todo: für später
        /*if (spielKartenDeck.isEmpty()){              // wenn der Stapel leer ist
            throw new IllegalArgumentException("Kartenstapel ist leer, mischen Sie die Karten des Ablagestapels neu.");
        }
        if (count > spielKartenDeck.size()){         // wenn der Stapel nicht mehr genug Karten hat
            throw new IllegalArgumentException("Es sind nicht genug Karten am Stapel. Mischen Sie die Karten des " +
                    "Ablagestapels neu.");
        }*/
        for (int i = 0; i < count; i++){
            // aus dem geshuffelten Kartendeck werden die obersten 7 Karten genommen und dem handkarten-deck zugewiesen
            UnoKarte obersteKarte = gameCardDeck.remove();
            handCards.add(obersteKarte);
        }

        // Ausgabe der Handkarten
        /*for (UnoKarte u : handCards) {
            System.out.println(u);
        }
        System.out.println(".............");*/

        return handCards;

    }
}

