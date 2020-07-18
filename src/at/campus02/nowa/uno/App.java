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
    private Stack<UnoKarte> stack = new Stack<>();        // Ablagestapel
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
                //printState();

                while (!roundEnded()) {
                    printState();                   // Ausgabe der aktuellen Situation: welcher Spieler ist dran, welche Karte liegt oben
                    makeTurn();               // Eingabe der Karten, Heben, Weiter
                    updateState();                  // nächster Spieler unter Berücksichtigung der Sonderkarten
                    Thread.sleep(1000);

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

        System.out.println("Bitte gib die Anzahl der Bots ein (0-4): ");
        int botCount = input.nextInt();
        while (botCount < 0 || botCount > 4) {
            System.out.println("Anzahl muss zwischen 0 und 4 sein. Gib die Anzahl erneut ein:");
            botCount = input.nextInt();
        }
        /*
        // Bot eingeben
        if (botCount < 0 || botCount > 4) {

            return;
        }*/

        for (int i = 0; i < botCount; i++) {
            System.out.println("Name Bot " + (i + 1) + ": ");
            String name = input.next();
            Bot b = new Bot(name);
            addSpieler(b);
        }

        int playerCount = 4 - botCount;
        for (int i = 0; i < playerCount; i++) {
            System.out.println("Name Spieler " + (i + 1) + ": ");

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
    private void addSpieler(Spieler s) {
        playersList.add(s);
    }

    // Methode, um die Handkarten den Spielern "auszuteilen"
    private void dealOutCards() {
        for (Spieler s : playersList) {
            s.setHandCardDeck(spielKarten.makePlayerDeck());
        }
    }

    // Methode, um Start-Spieler zu bestimmen
    private void chooseRandomPlayer() {
        Random random = new Random();
        int max = playersList.size() - 1;
        int min = 0;
        indexCurrentPlayer = random.nextInt((max - min) + 1) + min;
        //System.out.println(playersList.toString());       // zur Ausgabe der Spielerliste
        Spieler s = playersList.get(indexCurrentPlayer);
        System.out.println("Startspieler: " + s.getName());
        System.out.println("Los geht's!");
    }

    // Methode, um die erste Karte "aufzudecken"
    private void determineStartCard() {
        UnoKarte startCard = spielKarten.drawPile.remove();     // oberste Karte vom Nachziehstapel wird der Startkarte zugewiesen
        //spielKarten.stack.add(startCard);                                   // und dem Stack zugefügt
        stack.push(startCard);

        /*spielKarten.stack.lastElement()
        if (startCard.getKARTENWERT().equals(Kartenwert.plus4)) {     // falls +4 Startkarte wäre
            startCard = spielKarten.drawPile.remove();
            spielKarten.stack.add(startCard);
//            stack.add(startCard);
        }*/
    }

    // Methode: Bot macht seinen Zug
    private void makeBotTurn() {
        Bot b = (Bot) playersList.get(indexCurrentPlayer);
        //UnoKarte u = b.getFirstValidCard(spielKarten.stack.getLast());
        UnoKarte u = b.getFirstValidCard(stack.lastElement());
        if (u != null) {
            System.out.println(b.getName() + " legt " + u.toString());
            b.getHandCardDeck().remove(u);
            //spielKarten.stack.add(u);
            stack.push(u);

        } else {
            System.out.println(b.getName() + " hat keine passende Karte und hebt eine neue vom Stapel...");
            if (spielKarten.drawPile.size() <= 1) {  // falls der Nachziehstapel weniger/gleich 1 Karten beinhaltet
                makeNewDeckWhenPileIsEmpty();
            }
            b.getHandCardDeck().add(spielKarten.drawPile.remove()); // bot bekommt 1 Karte vom Pile in sein Hand-deck
            //spielKarten.stack.getLast().setPlayedAlready();                     // Funktion playedAlready wird aktiviert
            stack.lastElement().setPlayedAlready();                     // Funktion playedAlready wird aktiviert
        }
    }

    // Methode, um die Karteneingabe des menschlichen Spielers zu verarbeiten
    private void readUserInput() { //    makeUserTurn()

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
                    stack.lastElement().setPlayedAlready();
                    //spielKarten.stack.getLast().setPlayedAlready();
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
                            stack.push(u);                                         // füge sie zum stack hinzu

                            System.out.println("Korrekte Eingabe.");

                            korrekteEingabe = true;         // while Schleife verlassen, methode readuserinput verlassen --> weiter zu updateState
                            //roundEnded();                   // falls es die letzte Karte war, wird die Runde beendet

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

    // Spielzug abhängig ob Mensch oder BOT
    private void makeTurn() {
        Spieler currentPlayer = playersList.get(indexCurrentPlayer);

        if (currentPlayer.isBot())
            makeBotTurn();
        else
            readUserInput();
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
            if (spielKarten.drawPile.size() <= 1) {  // falls der Nachziehstapel weniger/gleich 1 Karten beinhaltet
                makeNewDeckWhenPileIsEmpty();
            }
            // die oberste Karte vom Nachziehstapel wird zu den Handkarten hinzugefügt
            playersList.get(indexCurrentPlayer).getHandCardDeck().add(spielKarten.drawPile.remove());
            // die aktuellen Karten werden angezeigt
            System.out.println("Deine " + playersList.get(indexCurrentPlayer).getHandCardDeck().size() + " aktuellen Karten: " + playersList.get(indexCurrentPlayer).getHandCardDeck());

        }
        return drawCardCounter;
    }

    // Methode, um zu prüfen, ob die gewünschte Karte gespielt werden darf
    private boolean validTurn(UnoKarte currentCard) {

        return validCard(currentCard, stack.lastElement());
    }

    // Prüfung, ob die Karte gültig ist
    public static boolean validCard(UnoKarte currentCard, UnoKarte previousCard) {
        if (currentCard.getFARBE() == Farbe.SCHWARZ)
            return true;
        if (currentCard.getFARBE() == previousCard.getFARBE() || currentCard.getKARTENWERT() == previousCard.getKARTENWERT())
            return true;

        return false;
    }

    // hier werden die Sonderkarten implementiert
    private void updateState() {

        if (playersList.get(indexCurrentPlayer).getHandCardDeck().size() == 0) {
            //System.out.println("[INFO-UpdateState: Rundenende, weiter zu roundEnded()]");
            makeNewDeckWhenRoundEnded();
            roundEnded();
        } else {
            //if (spielKarten.stack.getLast().isPlayedAlready() == false) {  // Funktion der Karte wurde noch nicht gespielt
            if (stack.lastElement().isPlayedAlready() == false) {  // Funktion der Karte wurde noch nicht gespielt

                // wenn "OUT" geschmissen wird, wird der nächste übersprungen
                //if (spielKarten.stack.getLast().getKARTENWERT() == Kartenwert.OUT) {
                if (stack.lastElement().getKARTENWERT() == Kartenwert.OUT) {
                    System.out.println("[INFO-UpdateState] Nächster Spieler wird übersprungen");
                    skip();
                }

                // wenn +2 geschmissen wird --> nächster Spieler bekommt 2 Karten und muss aussetzen, d.h. übernächster Spieler ist an der Reihe
                // todo: was ist, wenn der nächste spieler eine weitere +2 wirft
                //else if (spielKarten.stack.getLast().getKARTENWERT() == Kartenwert.plus2) {
                else if (stack.lastElement().getKARTENWERT() == Kartenwert.plus2) {

                    nextPlayer();   // zuerst einen Spieler weiter gehen
                    System.out.println("[INFO-UpdateState] Nächster Spieler " + playersList.get(indexCurrentPlayer).getName() + " hebt zwei Karten");

                    if (spielKarten.drawPile.size() <= 2) {  // falls der Nachziehstapel weniger/gleich 2 Karten beinhaltet
                        makeNewDeckWhenPileIsEmpty();
                    }
                    playersList.get(indexCurrentPlayer).getHandCardDeck().add(spielKarten.drawPile.remove());  // 2 Karten vom Nachziehstapel hinzufügen
                    playersList.get(indexCurrentPlayer).getHandCardDeck().add(spielKarten.drawPile.remove());
                    nextPlayer();   // noch einen Spieler weiter gehen

                }

                // plus 4
                //else if (spielKarten.stack.getLast().getKARTENWERT() == Kartenwert.plus4) {
                else if (stack.lastElement().getKARTENWERT() == Kartenwert.plus4) {

                    chooseColor();
                    nextPlayer();   // zuerst einen Spieler weiterspringen
                    System.out.println("[INFO-UpdateState] Nächster Spieler " + playersList.get(indexCurrentPlayer).getName() + " hebt vier Karten");

                    if (spielKarten.drawPile.size() <= 4) {  // falls der Nachziehstapel weniger/gleich 4 Karten beinhaltet
                        makeNewDeckWhenPileIsEmpty();
                    }
                    playersList.get(indexCurrentPlayer).getHandCardDeck().add(spielKarten.drawPile.remove());  //  4 Karten vom Nachziehstapel hinzufügen
                    playersList.get(indexCurrentPlayer).getHandCardDeck().add(spielKarten.drawPile.remove());
                    playersList.get(indexCurrentPlayer).getHandCardDeck().add(spielKarten.drawPile.remove());
                    playersList.get(indexCurrentPlayer).getHandCardDeck().add(spielKarten.drawPile.remove());
                    nextPlayer();   // noch einen Spieler weiter gehen
                }

                // Farbwunsch
                //else if (spielKarten.stack.getLast().getKARTENWERT() == Kartenwert.WILD) {
                else if (stack.lastElement().getKARTENWERT() == Kartenwert.WILD) {
                    chooseColor();
                    nextPlayer();
                }

                // Richtungswechsel
                //else if (spielKarten.stack.getLast().getKARTENWERT() == Kartenwert.RW) {
                else if (stack.lastElement().getKARTENWERT() == Kartenwert.RW) {
                    System.out.println("[INFO-UpdateState] Spielrichtung wird geändert");

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
        }
        //System.out.println("[iNFO-Schluss UpdateState] Der nächste Spieler ist dran...");

    }


    private void chooseColor() {
        Spieler currentPlayer = playersList.get(indexCurrentPlayer);

        if (currentPlayer.isBot()) {
            ArrayList<String> colors = new ArrayList<>(
                    Arrays.asList(
                            String.valueOf(Farbe.ROT),
                            String.valueOf(Farbe.BLAU),
                            String.valueOf(Farbe.GRÜN),
                            String.valueOf(Farbe.YELLOW)));

            Random rand = new Random();

            String selectedColor = colors.get(rand.nextInt(3) + 1);

            //spielKarten.stack.getLast().setFARBE(Farbe.valueOf(selectedColor));
            stack.lastElement().setFARBE(Farbe.valueOf(selectedColor));

            System.out.println(currentPlayer.getName() + " hat die Farbe " + selectedColor + " ausgewählt");

            return;
        }

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

                //spielKarten.stack.getLast().setFARBE(Farbe.valueOf(farbwunsch));
                stack.lastElement().setFARBE(Farbe.valueOf(farbwunsch));

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
            if (indexCurrentPlayer == playersList.size()) {
                indexCurrentPlayer = 0;
            }
        } else if (direction == -1) {
            if (indexCurrentPlayer == -1) {
                indexCurrentPlayer = playersList.size() - 1;
            }
        }
        return playersList.get(indexCurrentPlayer);
    }

    // Nachziehstapel ist fast leer --> Ablagestapel neu mischen und zum Nachziehstapel machen
    private void makeNewDeckWhenPileIsEmpty() {

        System.out.println("Anzahl der Karten im Ablagestapel: " + stack.size()); // nur für Testzwecke

        //UnoKarte oK = spielKarten.stack.removeLast();       // oberste Karte wird "auf die Seite gelegt" (nicht mitgemischt)
        UnoKarte oK = stack.pop();       // oberste Karte wird "auf die Seite gelegt" (nicht mitgemischt)

        System.out.println("oberste Karte " + oK.toString());   // nur für Testzwecke
        System.out.println("Anzahl der Karten im Ablagestapel nach Entfernen der obersten Karte: " + stack.size()); // nur für Testzwecke

        for (UnoKarte u : stack) {           // die Funktion playedAlready wieder auf FALSE setzen
            u.setPlayedAlreadyToFalse();
        }
        Collections.shuffle(stack);                     // Ablagestapel wird gemischt

        spielKarten.drawPile.addAll(stack);             // die gemischten Karten werden dem Nachziehstapel zugefügt
        stack.clear();          // Stack leeren
        //System.out.println("Oberste Karte: " + stack.getLast());    // nur für Testzwecke

        stack.push(oK);                   // oberste Karte wird wieder aufgelegt
        //System.out.println("Oberste Karte, nachdem die letzte Karte wieder aufgelegt wurde: " + spielKarten.stack.getLast());   // nur für Testzwecke
        System.out.println("Oberste Karte, nachdem die letzte Karte wieder aufgelegt wurde: " + stack.lastElement());   // nur für Testzwecke

    }

    private void makeNewDeckWhenRoundEnded() {

        for (Spieler p : playersList) {              // alle übrigen Handkarten werden geleert
            p.getHandCardDeck().clear();
        }
        spielKarten.drawPile.clear();               // Nachziehstapel "leeren"
        stack.clear();                 // Ablagestapel "leeren
        spielKarten.shuffleDeck();
        for (UnoKarte u : spielKarten.drawPile) {     // "Rücksetzen" der farbigen Wilden oder farbigen +4 aus voriger Runde
            if (u.getKARTENWERT() == Kartenwert.plus4 || u.getKARTENWERT() == Kartenwert.WILD) {
                u.setFARBE(Farbe.SCHWARZ);
            }
            u.setPlayedAlreadyToFalse();            // "Rücksetzen" der playedAlready Funktion
        }
    }

    // aktueller Status wird ausgegeben: welcher Spieler ist dran, welche Karte liegt oben, verfügbare Handkarten
    private void printState() {

        System.out.println("........");
        System.out.println("Anzahl Nachziehstapel: " + spielKarten.drawPile.size()); // für testzwecke
        System.out.println("Anzahl Ablagestapel/Stack: " + stack.size());
        System.out.println("oberste Karte am Ablagestapel: " + stack.lastElement().toString());
        Spieler s = playersList.get(indexCurrentPlayer);
        System.out.println("aktueller Spieler: " + s.getName());
        System.out.println("Deine " + playersList.get(indexCurrentPlayer).getHandCardDeck().size() + " aktuellen Karten: " + s.getHandCardDeck());
        System.out.println("........");
    }

    // abbruch der runde, wenn keine karten mehr im handkartenset vorhanden ist
    private boolean roundEnded() {

        if (playersList.get(indexCurrentPlayer).getHandCardDeck().size() != 0)
            return false;
        else {
            System.out.println(playersList.get(indexCurrentPlayer).getName() + " hat die Runde gewonnen - eine neue Runde beginnt.");
            return true;
        }
    }

    private boolean gameEnded() {

        return false;
    }

    private void printFinalScore() {

    }
}