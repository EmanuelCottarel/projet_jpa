package fr.diginamic.moviedb.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.diginamic.moviedb.deserializers.DirectorDeserializer;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "director")
@JsonDeserialize(using = DirectorDeserializer.class)
public class Director extends Person {

    @ManyToMany(mappedBy = "directors")
    private Set<Movie> movies = new HashSet<Movie>();

    public Director() {
    }

    /**
     *
     * @param id
     * @param fullName
     * @param birthDate
     * @param urlIMDB
     */
    public Director(String id, String fullName, LocalDate birthDate, String urlIMDB) {
        super(id, fullName, birthDate, urlIMDB);
    }

    @Override
    public String toString() {
        return "Director{" +
                "} " + super.toString();
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }
}
