package at.campus02.nowa.uno;

// Definition der Karten
public class Kartendeck {
    public static void main(String[] args) {

        String[] farben = {"grün", "gelb", "blau", "rot"};
        String[] karten = new String[100];

        kartenZuweisung(farben, karten);
        for (int i= 0; i < karten.length; i++){
            System.out.println("[" + i +"] - " + karten[i]);
        }
    }

    // die 0 gibt's jeweils nur 1x, die nummern 1-9 gibt's 2x ( plus 2, richtungswechsel, aussetzen je 2x)
    public static void kartenZuweisung(String[] farben, String[] karten) {
        int index = 0;
        for (int i = 0; i < farben.length; i++) {   // farbindex auf 0                                                                  | farbindex = 1
            karten[index] = farben[i] + " 0";           //todo: karten[0] = grün 0                                                      | karten[25] = gelb 0
            index++;                                // kartenindex 1                                                                    | kartenindex 26
            for (int j = 1; j < 10; j++) {          // nummernindex 1                   | j=2                   ..| j=9                 | j=1                   | j=2                   ..| j=9
                karten[index] = farben[i] + " " + j;    // todo: karten[1] = grün 1     | karten[3] = grün 2    ..| karten[17] = grün 9 | karten[26] = gelb 1   | karten[28] = gelb 2   ..| karten[42] = gelb 9
                index++;                            //kartenindex 2                     | kartenindex 4         ..| kartenindex 18       | kartenindex 27        | kartenindex 29       ..| kartenindex 43
                karten[index] = farben[i] + " " + j;    // todo: karten[2] = grün 1     | karten[4] = grün 2    ..| karten[18] = grün 9 | karten[27] = gelb 1   | karten[29] = gelb 2   ..| karten[43] = gelb 9
                index++;                            //kartenindex 3                     | kartenindex 5         ..| kartenindex 19       | kartenindex 28        | kartenindex 30       ..| kartenindex 44
                                                    // gehen in der 1. inneren for-schleife weiter
                                                    //--> j=2                           | j=3                   ..| j=10                 | j=2                  | j=3                   ..| j=10 --> fertig
            }                                                                                                   //  --> 1.innere for schleife fertig
            // Schleife für Plus 2                                                                             // jetzt kommt diese Schleife dran
            for (int j = 10; j< 11; j++) {
                karten[index] = farben[i] + " Plus2";                                                          // todo: karten[19] = grün Plus2                                           | karten[44] = gelb Plus2
                index++;                                                                                        // kartenindex 20                                                         | kartenindex 45
                karten[index] = farben[i] + " Plus2";                                                           // todo: karten[20] = grün Plus2                                          | karten[45] = gelb Plus2
                index++;                                                                                        // kartenindex 21                                                         | kartenindex 46
            }                                                                                                    // j=11 --> schleife fertig                                              | j=11 --> schleife fertig

            // Schleife für Richtungswechsel RW
            for (int j = 11; j< 12; j++) {
                karten[index] = farben[i] + " RW";                                                              // todo: karten[21] = grün RW                                               | karten[46] = gelb RW
                index++;                                                                                        // kartenindex 22                                                           | kartenindex 47
                karten[index] = farben[i] + " RW";                                                              // todo: karten[22] = grün RW                                               | karten[47] = gelb RW
                index++;                                                                                        // kartenindex 23                                                           | kartenindex
            }                                                                                                   // j=12 --> schleife fertig                                                 | j=12 --> schleife fertig

            // Schleife für Aussetzen OUT
            for (int j = 12; j< 13; j++){
                karten[index] = farben[i] + " OUT";                                                             // todo: karten[23] = grün OUT                                              | karten[48] = gelb OUT
                index++;                                                                                        // kartenindex 24                                                           | kartenindex 49
                karten[index] = farben[i] + " OUT";                                                             // todo: karten[24] = grün OUT                                              | karten[49] = gelb OUT
                index++;                                                                                        // kartenindex 25                                                           | kartenindex 50
            }                                                                                                   // j=13 --> schleife fertig --> zurück zu äußerer FOR                       | j=13 --> schleife fertig --> äußere FOR

        }
    }
}
