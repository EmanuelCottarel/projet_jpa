package fr.diginamic.moviedb.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.diginamic.utils.DirectorDeserializer;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.time.LocalDate;
import java.util.Set;

@Entity
@JsonDeserialize(using = DirectorDeserializer.class)
public class Director extends Person {

    @ManyToMany(mappedBy = "directors")
    private Set<Movie> movies;

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
                "movies=" + movies +
                "} " + super.toString();
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
