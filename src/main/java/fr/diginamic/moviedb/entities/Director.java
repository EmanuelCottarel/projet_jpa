package fr.diginamic.moviedb.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.diginamic.moviedb.deserializers.DirectorDeserializer;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "director")
@JsonDeserialize(using = DirectorDeserializer.class)
public class Director {

    @Id
    @Column(length = 10)
    private String id;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "birthdate")
    private LocalDate birthDate;

    @Column(name = "urlimdb")
    private String urlIMDB;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_birthplace")
    private Birthplace birthplace;

    @ManyToMany(mappedBy = "directors")
    private Set<Movie> movies = new HashSet<Movie>();

    public Director() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return Objects.equals(this.getId(), director.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }

    @Override
    public String toString() {
        return "Director{" +
                "} " + super.toString();
    }

    public Director(String id, String fullName, LocalDate birthDate, String urlIMDB) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.urlIMDB = urlIMDB;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getUrlIMDB() {
        return urlIMDB;
    }

    public void setUrlIMDB(String urlIMDB) {
        this.urlIMDB = urlIMDB;
    }

    public Birthplace getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(Birthplace birthplace) {
        this.birthplace = birthplace;
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
