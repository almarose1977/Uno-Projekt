package at.campus02.nowa.uno;

import java.io.PrintStream;
import java.util.Collections;
import java.util.Scanner;

public class App {
    private final Scanner input;
    private final PrintStream output;
    KartenDeck spielkarten = new KartenDeck();
    Spieler aiste;
    Spieler britta;
    Spieler lena;
    Spieler leo;

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

        aiste = new Spieler("Aiste",7, 0);
        britta = new Spieler("Britta",7, 0);
        leo = new Spieler("Leo", 7,0);
        lena = new Spieler("Lena",7,0);

    }

    private void initializeRound() {    // todo: Karten mischen, 4x 7 Karten verteilen, 1 Karte aufdecken, Startspieler auslosen

        spielkarten.shuffleDeck();

        aiste.makePlayerDeck(spielkarten);
        System.out.println("iiiiiiiiiiiiiiiiii");
        britta.makePlayerDeck(spielkarten);
        lena.makePlayerDeck(spielkarten);
        leo.makePlayerDeck(spielkarten);






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
