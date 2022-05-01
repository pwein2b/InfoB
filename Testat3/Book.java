/**
 *
 *
 * @author Markus Kuehn
 */
public class Book extends LibraryItem {

    private String title;
    private String author;

    /**
     * Konstruktor der Book Klasse
     *
     * @param title der Titel des Buches
     * @param author der Autor des Buches
     */
    public Book(String title, String author){
        this.title = title;
        this.author = author;
    }

    /**
     * Getter fuer den Buchtitel
     *
     * @return den Titel des Buches
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Getter fuer den Autor
     *
     * @return den Autor des Buches
     */
    public String getAuthor(){
        return this.author;
    }

    /**
     * Gibt eine Beschreibung des Buches zurück
     *
     * @return eine beschreibung des Buches
     */
    public String getDescription(){
        return "Name des Buches:" + this.title + "Autor:" + this.author;
    }
}
