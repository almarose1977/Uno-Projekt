package at.campus02.nowa.uno;

public enum Kartenwert {
    zero, eins, zwei, drei, vier, fünf, sechs, sieben, acht, neun, plus2, RW, OUT, WILD, plus4;

    private static final Kartenwert[] WERTE = Kartenwert.values();// Returns an array containing the constants
    // of this enum type, in the order they're declared.

    public static Kartenwert getWert(int i) {
        return WERTE[i];
    }
}
