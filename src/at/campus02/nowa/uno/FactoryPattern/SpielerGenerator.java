package at.campus02.nowa.uno.FactoryPattern;

public class SpielerGenerator implements AbstractGenerator {


    @Override
    public Mensch createMensch(String name) {
        return new Mensch();
    }

    @Override
    public Bot createBot(String name) {
        return new Bot();
    }
}
