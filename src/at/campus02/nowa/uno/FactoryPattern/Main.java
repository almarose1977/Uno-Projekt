package at.campus02.nowa.uno.FactoryPattern;

public class Main {
    public static void main(String[] args) {


       AbstractGenerator generator = new SpielerGenerator();


       Spieler spieler1 = generator.createBot("Leo");
       Spieler spieler2 = generator.createMensch("Lena");
       Spieler spieler3 = generator.createMensch("Britta");


    }
}

