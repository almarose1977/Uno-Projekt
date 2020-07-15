package at.campus02.nowa.uno;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class App {
    private final Scanner input;
    private final PrintStream output;
    private KartenDeck spielKarten = new KartenDeck();
    private LinkedList<UnoKarte> stack = new LinkedList<>();        // Ablagestapel
    private ArrayList<Spieler> playersList = new ArrayList<>();
    private int indexCurrentPlayer;
    private int direction = 1;


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
                    readUserInput();                // Eingabe der Karten, Heben, Weiter
                    updateState();                  // nächster Spieler unter Berücksichtigung der Sonderkarten
                    Thread.sleep(1000);
                    printState();                   // Ausgabe der aktuellen Situation: welcher Spieler ist dran, welche Karte liegt oben

                    Thread.sleep(100);
                }
            } while (!gameEnded());

            printFinalScore();
        } catch (Exception ex) {
            ex.printStackTrace();
            output.println(ex);
        }
    }

    private void initializeGame() { //  Karten erstellen und mischen, Spielernamen eingeben 4x

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

    private void initializeRound() {    // 4x 7 Karten verteilen, 1 Karte aufdecken, Aktionskarte prüfen, Startspieler auslosen

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
        indexCurrentPlayer = random.nextInt((max - min) + 1) + min;
        //System.out.println(playersList.toString());       // zur Ausgabe der Spielerliste
        Spieler s = playersList.get(indexCurrentPlayer);
        System.out.println("Startspieler: " + s.getName());
        System.out.println("Los geht's!");
    }

    // Methode, um die erste Karte "aufzudecken"
    public void determineStartCard() {
        UnoKarte startCard = spielKarten.drawPile.remove();     // oberste Karte vom Nachziehstapel wird der Startkarte zugewiesen
        stack.add(startCard);                                   // und dem Stack zugefügt

        if (startCard.getKARTENWERT().equals(Kartenwert.plus4)) {     // falls +4 Startkarte wäre
            startCard = spielKarten.drawPile.remove();
            stack.add(startCard);
        }

    }

    // Methode, um die Karteneingabe zu verarbeiten
    private void readUserInput() {

        Farbe f = null;
        Kartenwert kW = null;
        boolean korrekteEingabe = false;

        int drawCardCounter = 0;

        while (!korrekteEingabe) {
            System.out.println("Wähle eine Karte aus deiner Hand oder hebe eine Karte vom Nachziehstapel...");
            System.out.println("Wenn du dir nicht sicher bist, gib \"Hilfe\" ein.");
            String consoleInput = input.next();
            String userInput = consoleInput.toUpperCase();
            //BufferedReader helpReader = null;

            if (userInput.equals("HILFE")) {
                callHelp();
            } else if (userInput.equals("HEBEN")) {
                drawCardCounter = drawCard(drawCardCounter);
            } else if (userInput.equals("WEITER")) {  // nur wenn bereits 1x gehoben wurde, darf man "weiter sagen"
                if (drawCardCounter >= 1) {
                    korrekteEingabe = true;
                    stack.getLast().setPlayedAlready();
                    drawCardCounter = 0;
                } else
                    System.out.println("Du musst erst eine Karte vom Nachziehstapel nehmen, bevor du \"weiter\" sagen kannst.");
            } else {

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
                            values[0] = null;
                            break;
                    }

                    switch (values[1]) {
                        case "0":
                            kW = Kartenwert.zero;
                            break;
                        case "1":
                            kW = Kartenwert.eins;
                            break;
                        case "2":
                            kW = Kartenwert.zwei;
                            break;
                        case "3":
                            kW = Kartenwert.drei;
                            break;
                        case "4":
                            kW = Kartenwert.vier;
                            break;
                        case "5":
                            kW = Kartenwert.fünf;
                            break;
                        case "6":
                            kW = Kartenwert.sechs;
                            break;
                        case "7":
                            kW = Kartenwert.sieben;
                            break;
                        case "8":
                            kW = Kartenwert.acht;
                            break;
                        case "9":
                            kW = Kartenwert.neun;
                            break;
                        case "RW":
                            kW = Kartenwert.RW;
                            break;
                        case "OUT":
                            kW = Kartenwert.OUT;
                            break;
                        case "+2":
                            kW = Kartenwert.plus2;
                            break;
                        case "+4":
                            kW = Kartenwert.plus4;
                            break;
                        case "WILD":
                            kW = Kartenwert.WILD;
                            break;
                        default:
                            values[1] = null;
                            break;
                    }
                } catch (IndexOutOfBoundsException e) {
                    //System.out.println("Du hast die Karte falsch eingegeben. Richtige Eingabe z.B. \"R-7\". ");
                }

                if (f != null && kW != null) {      // Prüfung, ob die eingegebene Karte im Handkartenset vorhanden ist

                    UnoKarte u = playersList.get(indexCurrentPlayer).getKarte(f, kW);

                    if (u != null) {                // wenn es die Karte im Handkartenset gibt

                        if (validTurn(u)) {         // prüfen, ob Karte gespielt werden darf
                            playersList.get(indexCurrentPlayer).getHandCardDeck().remove(u);   // gültig: entferne sie aus handkarten
                            stack.add(u);                                                       // füge sie zum stack hinzu

                            System.out.println("Korrekte Eingabe.");
                            //u.setPlayedAlready();
                            korrekteEingabe = true;         // while Schleife verlassen, methode readuserinput verlassen --> weiter zu updateState

                        } else {
                            System.out.println("Diese Karte darf nicht gespielt werden. Du bekommst eine Strafkarte. ");
                            // die oberste Karte vom Nachziehstapel wird zu den Handkarten hinzugefügt
                            playersList.get(indexCurrentPlayer).getHandCardDeck().add(spielKarten.drawPile.remove());
                            korrekteEingabe = true;

                        }
                    } else {
                        System.out.println("Diese Karte ist nicht in den Handkarten vorhanden. ");
                    }
                } else
                    System.out.println("FALSCHE EINGABE!");
            }
        }

    }

    // Methode "HILFE"
    private void callHelp() {
        BufferedReader helpReader = null;
        try {
            helpReader = new BufferedReader(new FileReader("help.txt"));

            String line;
            while ((line = helpReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (helpReader != null)
                    helpReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Methode "HEBEN"
    private int drawCard(int drawCardCounter) {
        // wenn bereits 1x gehoben wurde
        if (drawCardCounter == 1) {
            System.out.println("Es kann nur 1x eine Karte gehoben werden. Gib bei der nächsten Eingabe \"weiter\" ein.\n");
        } else {
            drawCardCounter++;
            // die oberste Karte vom Nachziehstapel wird zu den Handkarten hinzugefügt
            playersList.get(indexCurrentPlayer).getHandCardDeck().add(spielKarten.drawPile.remove());
            // die aktuellen Karten werden angezeigt
            System.out.println("Deine " + playersList.get(indexCurrentPlayer).getHandCardDeck().size() + " aktuellen Karten: " + playersList.get(indexCurrentPlayer).getHandCardDeck());
        }
        return drawCardCounter;
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

    // hier werden die Sonderkarten implementiert
    private void updateState() {

        if (stack.getLast().isPlayedAlready() == false) {  // Funktion der Karte wurde noch nicht gespielt

            // wenn "OUT" geschmissen wird, wird der nächste übersprungen
            if (stack.getLast().getKARTENWERT() == Kartenwert.OUT) {
                skip();
            }

            // wenn +2 geschmissen wird --> nächster Spieler bekommt 2 Karten und muss aussetzen, d.h. übernächster Spieler ist an der Reihe
            // todo: was ist, wenn der nächste spieler eine weitere +2 wirft
            else if (stack.getLast().getKARTENWERT() == Kartenwert.plus2) {

                nextPlayer();   // zuerst einen Spieler weiter gehen
                playersList.get(indexCurrentPlayer).getHandCardDeck().add(spielKarten.drawPile.remove());  // 2 Karten vom Nachziehstapel hinzufügen
                playersList.get(indexCurrentPlayer).getHandCardDeck().add(spielKarten.drawPile.remove());
                nextPlayer();   // noch einen Spieler weiter gehen

            }

            // plus 4
            else if (stack.getLast().getKARTENWERT() == Kartenwert.plus4) {

                chooseColor();
                nextPlayer();   // zuerst einen Spieler weiterspringen
                playersList.get(indexCurrentPlayer).getHandCardDeck().add(spielKarten.drawPile.remove());  //  4 Karten vom Nachziehstapel hinzufügen
                playersList.get(indexCurrentPlayer).getHandCardDeck().add(spielKarten.drawPile.remove());
                playersList.get(indexCurrentPlayer).getHandCardDeck().add(spielKarten.drawPile.remove());
                playersList.get(indexCurrentPlayer).getHandCardDeck().add(spielKarten.drawPile.remove());
                nextPlayer();   // noch einen Spieler weiter gehen
            }

            // Farbwunsch
            else if (stack.getLast().getKARTENWERT() == Kartenwert.WILD) {
                chooseColor();
                nextPlayer();
            }

            // Richtungswechsel
            else if (stack.getLast().getKARTENWERT() == Kartenwert.RW) {
                changeDirection();
                nextPlayer();
            }
            // falls "normale" Karte gespielt wurde
            else
                nextPlayer();
        }
        // Funktion der Karte wurde bereits gespielt, dann kommt einfach der nächste Spieler dran
        else
            nextPlayer();

        System.out.println("Der nächste Spieler ist dran...");

    }


    private void chooseColor() {
        boolean correctInput = false;

        while (!correctInput) {
            System.out.println("gib deine gewünschte Farbe an: ");
            String farbEingabe = input.next();
            String farbwunsch = farbEingabe.toUpperCase();

            switch (farbwunsch) {
                case "R":
                    farbwunsch = String.valueOf(Farbe.ROT);
                    break;
                case "B":
                    farbwunsch = String.valueOf(Farbe.BLAU);
                    break;
                case "G":
                    farbwunsch = String.valueOf(Farbe.GRÜN);
                    break;
                case "Y":
                    farbwunsch = String.valueOf(Farbe.YELLOW);
                    break;
                default:
                    farbwunsch = null;
                    System.out.println("Falsche Eingabe! Nur R, G, B und Y sind erlaubt.");
                    break;
            }

            if (farbwunsch != null) {
                correctInput = true;

                stack.getLast().setFARBE(Farbe.valueOf(farbwunsch));
                //stack.getLast().setKARTENWERT(Kartenwert.zero);     // Karten-WERT wird standarfmäßig auf "0" gesetzt --> hinfällig durch playedAlready
            }

        }
    }

    private void skip() {
        nextPlayer();
        nextPlayer();
    }

    private void changeDirection() {
        direction *= -1;
    }


    private Spieler nextPlayer() {
        indexCurrentPlayer = indexCurrentPlayer + direction;

        if (direction == 1) {
            if (indexCurrentPlayer == 4) {
                indexCurrentPlayer = 0;
            }
        } else if (direction == -1) {
            if (indexCurrentPlayer == -1) {
                indexCurrentPlayer = 3;
            }
        }
        return playersList.get(indexCurrentPlayer);
    }

    // aktueller Status wird ausgegeben: welcher Spieler ist dran, welche Karte liegt oben, verfügbare Handkarten
    private void printState() {

        System.out.println("........");
        System.out.println("oberste Karte am Ablagestapel: " + stack.getLast().toString());
        Spieler s = playersList.get(indexCurrentPlayer);
        System.out.println("aktueller Spieler: " + s.getName());
        System.out.println("Deine " + playersList.get(indexCurrentPlayer).getHandCardDeck().size() + " aktuellen Karten: " + s.getHandCardDeck());
    }

    private boolean roundEnded() {
        // todo: abbruch der runde, wenn keine karten mehr im handkartenset vorhanden ist
        if (playersList.get(indexCurrentPlayer).getHandCardDeck().size() == 0) {
            System.out.println("Keine Karten mehr!");

            return true;
        } else
            return false;
    }

    private boolean gameEnded() {

        return false;
    }

    private void printFinalScore() {

    }
}