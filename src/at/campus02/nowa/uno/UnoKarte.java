package at.campus02.nowa.uno;

public class UnoKarte {

    private final Farbe FARBE;
    private final Kartenwert KARTENWERT;

    public UnoKarte(Farbe FARBE, Kartenwert KARTENWERT) {
        this.FARBE = FARBE;
        this.KARTENWERT = KARTENWERT;
    }

    public Farbe getFARBE() {
        return FARBE;
    }

    public Kartenwert getKARTENWERT() {
        return KARTENWERT;
    }

    @Override
    public String toString() {
        return FARBE +" " + KARTENWERT;
    }
}
