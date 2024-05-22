package fr.diginamic.moviedb.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.diginamic.moviedb.deserializers.CountryDeserializer;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="country")
@JsonDeserialize(using = CountryDeserializer.class)
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "url_imdb")
    private String urlIMDB;

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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlIMDB() {
        return urlIMDB;
    }

    public void setUrlIMDB(String urlIMDB) {
        this.urlIMDB = urlIMDB;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public void addMovie(Movie movie){
        this.movies.add(movie);
    }


}
