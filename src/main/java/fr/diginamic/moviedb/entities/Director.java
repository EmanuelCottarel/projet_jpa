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

    /** ID - the IMDB ID */
    @Id
    @Column(length = 10)
    private String id;

    /** fullName */
    @Column(name = "fullname")
    private String fullName;

    /** birthDate */
    @Column(name = "birthdate")
    private LocalDate birthDate;

    /** urlIMDB */
    @Column(name = "urlimdb")
    private String urlIMDB;

    /** birthplace */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_birthplace")
    private Birthplace birthplace;

    /** movies */
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

    /**
     * Constructor
     * @param id - the IMDB id
     * @param fullName - FullName
     * @param birthDate - birthDate
     * @param urlIMDB - ulrIMDB
     */
    public Director(String id, String fullName, LocalDate birthDate, String urlIMDB) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.urlIMDB = urlIMDB;
    }

    /**
     * @return the ID
     */
    public String getId() {
        return id;
    }

    /**
     * @return the fullname
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Define a new fullname
     * @param fullName - the new fullname
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the birthDate
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Define the new birthDate
     * @param birthDate - the new birthdate
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the IMDB URL
     */
    public String getUrlIMDB() {
        return urlIMDB;
    }

    /**
     * Define the new urlIMDB
     * @param urlIMDB - the new urlIMDB
     */
    public void setUrlIMDB(String urlIMDB) {
        this.urlIMDB = urlIMDB;
    }

    /**
     * @return the birthPlace
     */
    public Birthplace getBirthplace() {
        return birthplace;
    }

    /**
     * Define a new birthplace
     * @param birthplace - the new birthplace
     */
    public void setBirthplace(Birthplace birthplace) {
        this.birthplace = birthplace;
    }

    /**
     * @return a Set of Movie
     */
    public Set<Movie> getMovies() {
        return movies;
    }

    /**
     * Define a new Set of Movie
     * @param movies - the new Set
     */
    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Add a new Movie to the actual Set of Movie
     * @param movie - the new Movie
     */
    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }
}
