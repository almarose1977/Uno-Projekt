package at.campus02.nowa.uno;

import java.io.PrintStream;
import java.util.*;

public class App {
    private final Scanner input;
    private final PrintStream output;
    private KartenDeck spielkarten = new KartenDeck();
    private LinkedList<UnoKarte> ablagestapel = new LinkedList<>();
    private ArrayList<Spieler> spielerListe = new ArrayList<>();
    private int currentSpieler;


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
            output.println(ex);
        }
    }

    private void initializeGame() { // todo: Spielernamen eingeben 4x, Karten mischen, Karten verteilen 7x/Spieler,
                                    // 1 UnoKarte aufdecken, Aktionskarte prüfen, Startspieler auslosen, Anzeige Spieler

        spielkarten.makeDeck();
        System.out.println(".............");

        // Spieler eingeben
        for(int i = 1; i <= 4;i++){
            System.out.println("Name Spieler " + i + ": ");
            Scanner scan = new Scanner(System.in);
            String name = scan.next();
            Spieler s = new Spieler(name);
            addSpieler(s);
        }

        // zufälligen Spieler bestimmen
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

    private void initializeRound() {    // todo: Karten mischen, 4x 7 Karten verteilen, 1 Karte aufdecken, Startspieler auslosen

        //spielkarten.shuffleDeck();

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
