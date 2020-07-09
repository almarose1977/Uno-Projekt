package at.campus02.nowa.uno;

import java.io.PrintStream;
import java.util.*;

public class App {
    private final Scanner input;
    private final PrintStream output;
    private KartenDeck spielKarten = new KartenDeck();
    private LinkedList<UnoKarte> stack = new LinkedList<>();        // Ablagestapel
    private ArrayList<Spieler> playersList = new ArrayList<>();
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
                    Thread.sleep(1000);
                    printState();

                    Thread.sleep(1000);
                }
            } while (!gameEnded());

            printFinalScore();
        } catch (Exception ex) {
            ex.printStackTrace();
            output.println(ex);
        }
    }

    private void initializeGame() { // todo: Karten erstellen und mischen, Spielernamen eingeben 4x, Anzeige Spieler

        spielKarten.makeDeck();
        spielKarten.shuffleDeck();

        // Spieler eingeben
        System.out.println("Bitte geben Sie die 4 Spielernamen ein: ");
        for (int i = 1; i <= 4; i++) {
            System.out.println("Name Spieler " + i + ": ");

            String name = input.next();
            Spieler s = new Spieler(name);
            addSpieler(s);
        }
    }

    private void initializeRound() {    // todo: 4x 7 Karten verteilen, 1 Karte aufdecken, Aktionskarte prüfen, Startspieler auslosen

        dealOutCards();
        chooseRandomPlayer();
        determineStartCard();

    }

    // Methode, um die Spieler der Spielerliste hinzuzufügen
    public void addSpieler(Spieler s) {
        playersList.add(s);
    }

    // Methode, um die Handkarten den Spielern "auszuteilen"
    public void dealOutCards() {
        for (Spieler s : playersList) {
            s.setHandCardDeck(spielKarten.makePlayerDeck());
        }
    }

    // Methode, um Start-Spieler zu bestimmen
    public void chooseRandomPlayer() {
        Random random = new Random();
        int max = 3;
        int min = 0;
        indexCurrentSpieler = random.nextInt((max - min) + 1) + min;
        //System.out.println(playersList.toString());       // zur Ausgabe der Spielerliste
        Spieler s = playersList.get(indexCurrentSpieler);
        System.out.println("Startspieler: " + s.getName());
        System.out.println("Los geht's!");

    }

    // Methode, um die erste Karte "aufzudecken"
    public void determineStartCard() {

        UnoKarte startCard = spielKarten.gameCardDeck.remove();
        stack.add(startCard);
    }

    // Methode, um die Karteneingabe zu verarbeiten
    private void readUserInput() {

        Farbe f = null;
        Kartenwert kW = null;
        boolean korrekteEingabe = false;

        System.out.println("Wähle eine Karte oder hebe eine Karte vom Nachziehstapel...");
        String userInput = input.next();
        if (userInput.equals("Heben")) {
            playersList.get(indexCurrentSpieler).getHandCardDeck().add(spielKarten.gameCardDeck.remove());
            System.out.println("Anzahl Handkarten: " + playersList.get(indexCurrentSpieler).getHandCardDeck().size());
            System.out.println("Der nächste Spieler ist an der Reihe!");
        }
        else {

            do {
                String[] values = userInput.split("-");

                try {                           // IndexOutOfBoundsException wird geworfen, wenn die Eingabe nicht richtig erfolgt
                    // z.B. nur R anstelle von R-9, dann ist das values[] nicht vollständig
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
                        default:
                            //System.out.println("Keine gültige Farbeingabe.");
                            values[0] = null;
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
                        default:
                            //System.out.println("Werteingabe falsch.");
                            values[1] = null;
                            break;
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Du hast die Karte falsch eingegeben. Richtige Eingabe z.B. \"R-7\". ");
                }

                if (f != null && kW != null) {      // Prüfung, ob die eingegebene Karte im Handkartenset vorhanden ist

                    UnoKarte u = playersList.get(indexCurrentSpieler).getKarte(f, kW);

                    if (u != null) {                // wenn es die Karte im Handkartenset gibt

                        if (validTurn(u)) {         // prüfen, ob Karte gespielt werden darf
                            playersList.get(indexCurrentSpieler).getHandCardDeck().remove(u);
                            stack.add(u);

                            System.out.println("Anzahl Handkarten: " + playersList.get(indexCurrentSpieler).getHandCardDeck().size());        // für Testzwecke
                            korrekteEingabe = true;
                        } else
                            System.out.print("Diese Karte darf nicht gespielt werden. ");
                    } else
                        System.out.print("Diese Karte ist nicht in den Handkarten vorhanden. ");
                }
            /*else
                System.out.println("FALSCHE EINGABE, Farbe oder Wert sind null.");*/

            } while (!korrekteEingabe);
        }
    }


    // Methode, um zu prüfen, ob die gewünschte Karte gespielt werden darf
    private boolean validTurn(UnoKarte gespielteKarte) {

        boolean isValid = false;
        UnoKarte currentCard = stack.getLast();
        if (gespielteKarte.getFARBE() == Farbe.SCHWARZ) {       // schwarze Karten dürfen immer gespielt werden
            isValid = true;
        }
        if (currentCard.getFARBE() == gespielteKarte.getFARBE() || currentCard.getKARTENWERT() == gespielteKarte.getKARTENWERT()) {
            isValid = true;
        }

        return isValid;
    }

    private void updateState() {

        if (indexCurrentSpieler == 3) {
            indexCurrentSpieler = 0;
        } else
            indexCurrentSpieler++;
    }

    // aktueller Status wird ausgegeben: welcher Spieler ist dran, welche Karte liegt oben, verfügbare Handkarten
    private void printState() {

        System.out.println("........");
        System.out.println("oberste Karte am Ablagestapel: " + stack.getLast().toString());
        Spieler s = playersList.get(indexCurrentSpieler);
        System.out.println("aktueller Spieler: " + s.getName());
        System.out.println("Deine aktuellen Karten: " + s.getHandCardDeck());
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