/**
 *Klasse, welche ein Bluray Objekt darstellen soll
 *
 * @author Markus Kuehn
 */
public class BluRay extends LibraryItem{

    private String title;
    private String director;

    /**
     * Konstruktor fuer ein BluRay Objekt
     *
     * @param title Titel des Films
     * @param director Regisseur des Films
     */
    public BluRay(String title, String director){
        this.title = title;
        this.director = director;
    }

    /**
     * Getter fuer den BluRay Titel
     *
     * @return den Titel der BluRay
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Getter fuer den Regisseur
     *
     * @return den Names des Regisseurs
     */
    public String getDirector(){
        return this.director;
    }

    /**
     * Gibt eine Beschreibung des Films aus
     *
     * @return eine Beschreibung des Films
     */
    public String getDescription(){
        return "Name des Filmes:" + this.title + "Regisseur:" + this.director;
    }
}
