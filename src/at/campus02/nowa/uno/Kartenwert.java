package at.campus02.nowa.uno;

public enum Kartenwert {
    ZERO, EINS, ZWEI, DREI, VIER, FÜNF, SECHS, SIEBEN, ACHT, NEUN, PLUS2, RW, OUT, WILD, PLUS4;

    private static final Kartenwert[] WERTE = Kartenwert.values();// Returns an array containing the constants
                                                                // of this enum type, in the order they're declared.

    public static Kartenwert getWert(int i){
        return WERTE[i];
    }
}
