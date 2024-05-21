package fr.diginamic.moviedb.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
public class Director extends Person{

    @ManyToMany(mappedBy = "directors")
    private Set<Movie> movies;

    public Director() {
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
