package fr.diginamic.moviedb.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.diginamic.moviedb.deserializers.CountryDeserializer;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="country")
@JsonDeserialize(using = CountryDeserializer.class)
public class Country {

    /** ID - Auto Incremented */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Name */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /** URL IMDB */
    @Column(name = "url_imdb")
    private String urlIMDB;

    /** Set of movies linked with this Country */
    @OneToMany(mappedBy = "country")
    private Set<Movie> movies;

    public Country() {
    }

    /**
     *
     * @param name - the name of the country
     * @param urlIMDB - IMDB url
     */
    public Country(String name, String urlIMDB) {
        this.name = name;
        this.urlIMDB = urlIMDB;
    }

    /**
     * @return the ID
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Define the new name
     * @param name - the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the IMDB URL
     */
    public String getUrlIMDB() {
        return urlIMDB;
    }

    /**
     * Define the ne URLIMDB value
     * @param urlIMDB - the new IMDB URL
     */
    public void setUrlIMDB(String urlIMDB) {
        this.urlIMDB = urlIMDB;
    }

    /**
     * @return the Movies
     */
    public Set<Movie> getMovies() {
        return movies;
    }

    /**
     * Define a new Set of Movies
     * @param movies - the new Set of movies
     */
    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Add a new Movie to the actual set of Movies
     * @param movie - the new Movie
     */
    public void addMovie(Movie movie){
        this.movies.add(movie);
    }


}
