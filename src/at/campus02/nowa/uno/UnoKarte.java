package at.campus02.nowa.uno;

public class UnoKarte {

    private Farbe FARBE;    // darf ich nicht final machen, weil ich sonst keine farbwünsche vergeben kann
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

    public void setFARBE(Farbe FARBE) {
        this.FARBE = FARBE;
    }

    @Override
    public String toString() {
        return FARBE +" " + "\"" + KARTENWERT + "\"";
    }
}
