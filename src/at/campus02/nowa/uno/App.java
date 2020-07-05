package at.campus02.nowa.uno;

import java.io.PrintStream;
import java.util.*;

public class App {
    private final Scanner input;
    private final PrintStream output;
    private KartenDeck kartenDeck = new KartenDeck();
    private LinkedList<UnoKarte> ablagestapel = new LinkedList<>();
    private ArrayList<Spieler> spielerListe = new ArrayList<>();
    private int currentSpieler;
    private UnoKarte currentCard;


    public App(Scanner input, PrintStream output){
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
        }catch (Exception ex){
            ex.printStackTrace();
            output.println(ex);
        }
    }

    private void initializeGame() { // todo: Spielernamen eingeben 4x, Karten mischen,
                                    // 1 UnoKarte aufdecken, Aktionskarte prüfen, Anzeige Spieler

        kartenDeck.makeDeck();
        kartenDeck.shuffleDeck();

        // Spieler eingeben
        for(int i = 1; i <= 4;i++){
            System.out.println("Name Spieler " + i + ": ");
            //Scanner scan = new Scanner(System.in);
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
        int max = 4;
        int min = 1;
        currentSpieler = random.nextInt((max-min) + 1) + min;
        System.out.println(spielerListe.toString());
        System.out.println("Startspieler: " + currentSpieler);
    }

    public void addSpieler(Spieler s){
        spielerListe.add(s);
    }

    public void verteileHandkarten(){
        for(Spieler s : spielerListe){
            s.setHandKarten(kartenDeck.makePlayerDeck());
        }
    }

    public void startKarteFestlegen(){

        currentCard = kartenDeck.spielKartenDeck.remove();
    }

    private void readUserInput() {


    }

    private void updateState() {

    }

    private void printState() {

    }

    private boolean roundEnded(){

        return false;
    }

    private boolean gameEnded(){

        return false;
    }

    private void printFinalScore(){

    }
}
