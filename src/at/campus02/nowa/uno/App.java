package at.campus02.nowa.uno;

import java.io.PrintStream;
import java.util.Scanner;

public class App {
    private final Scanner input;
    private final PrintStream output;

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

        KartenDeck kd = new KartenDeck();
        kd.makeDeck();
    }

    private void initializeRound() {

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
