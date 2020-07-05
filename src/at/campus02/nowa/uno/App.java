package at.campus02.nowa.uno;

import java.io.PrintStream;
import java.util.*;

public class App {
    private final Scanner input;
    private final PrintStream output;
    private KartenDeck kartenDeck = new KartenDeck();
    private LinkedList<UnoKarte> ablagestapel = new LinkedList<>();
    private ArrayList<Spieler> spielerListe = new ArrayList<>();
    private int indexCurrentSpieler;


    public App(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
    }

    public void Run() {
        initializeGame();
        try {
            do {
                initializeRound();
                printState();

                while (!roundEnded()) {
                    readUserInput();
                    updateState();
                    printState();

                    Thread.sleep(100); // diese zeile sollen wir ignorieren - BRISCH
                }
            } while (!gameEnded());

            printFinalScore();
        } catch (Exception ex) {
            ex.printStackTrace();
            output.println(ex);
        }
    }

    private void initializeGame() { // todo: Spielernamen eingeben 4x, Karten mischen,
        // 1 UnoKarte aufdecken, Aktionskarte prüfen, Anzeige Spieler

        kartenDeck.makeDeck();
        kartenDeck.shuffleDeck();

        // Spieler eingeben
        for (int i = 1; i <= 4; i++) {
            System.out.println("Name Spieler " + i + ": ");

            String name = input.next();
            Spieler s = new Spieler(name);
            addSpieler(s);
        }
    }


    private void initializeRound() {    // todo: 4x 7 Karten verteilen, 1 Karte aufdecken, Startspieler auslosen

        verteileHandkarten();
        chooseRandomPlayer();
        startKarteFestlegen();

    }

    public void chooseRandomPlayer() {  // zufälligen Spieler bestimmen
        Random random = new Random();
        int max = 3;
        int min = 0;
        indexCurrentSpieler = random.nextInt((max - min) + 1) + min;
        System.out.println(spielerListe.toString());
        System.out.println("Startspieler: " + indexCurrentSpieler);
    }

    public void addSpieler(Spieler s) {
        spielerListe.add(s);
    }

    public void verteileHandkarten() {
        for (Spieler s : spielerListe) {
            s.setHandKarten(kartenDeck.makePlayerDeck());
        }
    }

    public void startKarteFestlegen() {

        UnoKarte startCard = kartenDeck.spielKartenDeck.remove();
        ablagestapel.add(startCard);
    }

    private void readUserInput() {


        Farbe f = null;
        Kartenwert kW = null;
        boolean korrekteEingabe = false;
        do {
            System.out.println("bitte gib deine Karte bekannt.");

            String s = input.next();
            String values[] = s.split("-");

            switch (values[0]) {
                case "R":
                    f = Farbe.ROT;
                    break;
                case "B":
                    f = Farbe.BLAU;
                    break;
                case "G":
                    f = Farbe.GRÜN;
                    break;
                case "Y":
                    f = Farbe.YELLOW;
                    break;
                case "S":
                    f = Farbe.SCHWARZ;
                    break;
            }
            switch (values[1]) {
                case "0":
                    kW = Kartenwert.ZERO;
                    break;
                case "1":
                    kW = Kartenwert.EINS;
                    break;
                case "2":
                    kW = Kartenwert.ZWEI;
                    break;
                case "3":
                    kW = Kartenwert.DREI;
                    break;
                case "4":
                    kW = Kartenwert.VIER;
                    break;
                case "5":
                    kW = Kartenwert.FÜNF;
                    break;
                case "6":
                    kW = Kartenwert.SECHS;
                    break;
                case "7":
                    kW = Kartenwert.SIEBEN;
                    break;
                case "8":
                    kW = Kartenwert.ACHT;
                    break;
                case "9":
                    kW = Kartenwert.NEUN;
                    break;
                case "RW":
                    kW = Kartenwert.RW;
                    break;
                case "OUT":
                    kW = Kartenwert.OUT;
                    break;
                case "+2":
                    kW = Kartenwert.PLUS2;
                    break;
                case "+4":
                    kW = Kartenwert.PLUS4;
                    break;
                case "WILD":
                    kW = Kartenwert.WILD;
                    break;
            }
            if (f != null && kW != null) {
                //UnoKarte u =  new UnoKarte(f, kW);
                UnoKarte u = spielerListe.get(indexCurrentSpieler).getKarte(kW, f);

                if (u != null) {
                    // prüfen, ob Karte gespielt werden darf
                    spielerListe.get(indexCurrentSpieler).getHandKarten().remove(u);
                    ablagestapel.add(u);

                        System.out.println("Anzahl Handkarten: " + spielerListe.get(indexCurrentSpieler).getHandKarten().size());
                        korrekteEingabe = true;

                    }
                }

        } while (!korrekteEingabe);


    }


    private boolean validTurn(UnoKarte gespielteKarte){

        UnoKarte currentCard = ablagestapel.getLast();
        //if currentCard.getFARBE()
    }

    private void updateState() {

        if(indexCurrentSpieler == 3){
            indexCurrentSpieler = 0;
        }
        else
            indexCurrentSpieler++;
    }

    private void printState() {

        UnoKarte currentCard = ablagestapel.getLast();
        System.out.println("aktuelle Karte am Stack: " + currentCard.toString());
        Spieler s = spielerListe.get(indexCurrentSpieler);
        System.out.println("Aktueller Spieler: " + s.getName());
        System.out.println("Deine aktuellen Karten:" + s.getHandKarten());
    }

    private boolean roundEnded() {

        return false;
    }

    private boolean gameEnded() {

        return false;
    }

    private void printFinalScore() {

    }
}