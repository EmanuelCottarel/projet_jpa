package fr.diginamic.moviedb.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.diginamic.moviedb.deserializers.TypeDeserializer;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "type")
@JsonDeserialize(using = TypeDeserializer.class)
public class Type {

    /** Id - auto incremented */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** name */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /** movies */
    @ManyToMany(mappedBy = "types")
    private Set<Movie> movies;

    public Type() {
    }

    /**
     * Constructor
     * @param name - name of the new type
     */
    public Type(String name) {
        this.name = name;
    }

    /**
     * @return the id
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
     * @return the Set of Movie
     */
    public Set<Movie> getMovies() {
        return movies;
    }

    /**
     * Defien a new Set of Movie
     * @param movies - the new Set of Movie
     */
    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
