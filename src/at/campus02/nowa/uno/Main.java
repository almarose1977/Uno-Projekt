package at.campus02.nowa.uno;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        App app = new App(input, System.out);

        System.out.println("Herzlich willkommen zu einer neuen Partie UNO!");
        System.out.println("Das Spiel wird nun gestartet ...");
        System.out.println();
        app.Run();

        input.close();
        System.out.println("UNO wird beendet ...");

    }
}
