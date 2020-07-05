package at.campus02.nowa.uno.FactoryPattern;

public interface AbstractGenerator {

    public Mensch createMensch(String name);
    public Bot createBot(String name);

}
