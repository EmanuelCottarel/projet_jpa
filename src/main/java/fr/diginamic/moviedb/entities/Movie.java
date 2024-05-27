package fr.diginamic.moviedb.entities;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.diginamic.moviedb.deserializers.MovieDeserializer;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "movie")
@JsonDeserialize(using = MovieDeserializer.class)
public class Movie {

    /** ID - IMDB ID */
    @Id
    @Column(length = 10)
    private String id;

    /** Title */
    @Column(name = "title", nullable = false)
    private String title;

    /** Realease year */
    @Column(name = "release_year")
    private Integer releaseYear;

    /** IMDB rating */
    @Column(name = "rating")
    private double rating;

    /** locations where the movie take place */
    @Column(name = "locations")
    private String locations;

    /** Summary */
    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    /** url IMDB */
    @Column(name = "url_imdb")
    private String urlIMDB;

    /** Roles */
    @OneToMany(mappedBy = "movie", cascade = CascadeType.PERSIST)
    private Set<Role> roles = new HashSet<Role>();

    /** Main actors */
    @ManyToMany
    @JoinTable(name = "main_casting",
            joinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Actor> mainActors = new HashSet<Actor>();

    /** Directors */
    @ManyToMany
    @JoinTable(name = "movie_director",
            joinColumns = @JoinColumn(name = "director_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Director> directors = new HashSet<Director>();

    /** Types */
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "movie_type",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "type_id", referencedColumnName = "id"))
    private Set<Type> types = new HashSet<Type>();

    /** Original Language */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_language")
    private Language language;

    /** Country of origin */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_country")
    private Country country;

    public Movie() {
    }

    /**
     * Constructor
     * @param title - the title of the movie
     */
    public Movie(String title) {
        this.title = title;
    }

    /**
     * @param id - imdb id
     * @param title - the title
     * @param releaseYear - realease year
     * @param rating - rate on IMDB
     * @param summary - movie plot
     * @param urlIMDB - url IMDB
     */
    public Movie(String id, String title, Integer releaseYear, double rating, String summary, String urlIMDB, String locations) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.summary = summary;
        this.urlIMDB = urlIMDB;
        this.locations = locations;

    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                ", rating=" + rating +
                ", locations='" + locations + '\'' +
                ", summary='" + summary + '\'' +
                ", urlIMDB='" + urlIMDB + '\'' +
                ", mainActors=" + mainActors +
                ", directors=" + directors +
                ", types=" + types +
                ", language=" + language +
                ", country=" + country +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Define the new title
     * @param title - the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the releaseYear
     */
    public Integer getReleaseYear() {
        return releaseYear;
    }

    /**
     * Define the new realeaseYear
     * @param releaseYear - the new releaseYear
     */
    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * @return the locations
     */
    public String getLocations() {
        return locations;
    }

    /**
     * Define the new locations
     * @param locations - the new locations
     */
    public void setLocations(String locations) {
        this.locations = locations;
    }

    /**
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Define the new summary
     * @param summary - the new summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return the new urlIMDB
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
     * @return the roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Define a new Set of roles
     * @param roles - the new Set of roles
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * @return a Set of mainActors
     */
    public Set<Actor> getMainActors() {
        return mainActors;
    }

    /**
     * Define a new Set of mainActors
     * @param mainActors - the new Set of Actors
     */
    public void setMainActors(Set<Actor> mainActors) {
        this.mainActors = mainActors;
    }

    /**
     * @return the Set of Directors
     */
    public Set<Director> getDirectors() {
        return directors;
    }

    /**
     * Add a Director Object to the Director Collection
     * @param director - the new director to add
     */
    public void addDirector(Director director) {
        if (!directors.contains(director)) {
            directors.add(director);
        }
    }

    /**
     * Define a new Set of Directors
     * @param directors - the new Set of Directors
     */
    public void setDirectors(Set<Director> directors) {
        this.directors = directors;
    }

    /**
     * @return a Set of Type
     */
    public Set<Type> getTypes() {
        return types;
    }

    /**
     * Define a new Set of Types
     * @param types - the new Set of types
     */
    public void setTypes(Set<Type> types) {
        this.types = types;
    }

    /**
     * Add a Type object to the movie
     * @param type Type
     */
    public void addType(Type type) {
        this.types.add(type);
    }

    /**
     * @return the language
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * Define a new language
     * @param language - the new language
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Set a new country
     * @param country - the new country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * Add an Actor to the Set of mainActors
     * @param actor - the new actor
     */
    public void addMainActor(Actor actor) {
        this.mainActors.add(actor);
    }

    /**
     * Add a role to the Set of roles
     * @param role - the new role
     */
    public void addRole(Role role){
        this.roles.add(role);
    }
}
