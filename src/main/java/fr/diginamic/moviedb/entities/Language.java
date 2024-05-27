package fr.diginamic.moviedb.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.diginamic.moviedb.deserializers.LanguageDeserializer;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "language")
@JsonDeserialize(using = LanguageDeserializer.class)
public class Language {

    /** ID - Auto Incremented */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** name */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /** Movies associated  */
    @OneToMany(mappedBy = "language")
    private Set<Movie> movies;

    public Language() {

    }

    /**
     * Constructor
     * @param name - the name of the language
     */
    public Language(String name) {
        this.name = name;
    }

    /**
     * @return the ID
     */
    public Integer getId() {
        return id;
    }

    /**
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
     * @return Set of Movie
     */
    public Set<Movie> getMovies() {
        return movies;
    }

    /**
     * Define a new Set of Movie
     * @param movies - the new Set of Movie
     */
    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
