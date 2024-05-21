package fr.diginamic.moviedb.entities;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.diginamic.utils.MovieDeserializer;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movie")
@JsonDeserialize(using = MovieDeserializer.class)
public class Movie {

    @Id
    @Column(length = 10)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "rating")
    private double rating;

    @Column(name = "locations")
    private String locations;

    @Column(name = "summary")
    private String summary;

    @Column(name = "url_imdb")
    private String urlIMDB;

    @OneToMany(mappedBy = "movie")
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(name = "main_casting",
            joinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"))
    private Set<Actor> mainActors;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "movie_director",
            joinColumns = @JoinColumn(name = "director_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"))
    private Set<Director> directors = new HashSet<Director>();

    @ManyToMany
    @JoinTable(name = "movie_type",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "type_id", referencedColumnName = "id"))
    private Set<Type> types;

    @ManyToOne
    @JoinColumn(name = "id_language")
    private Language language;

    @ManyToOne
    @JoinColumn(name = "id_country")
    private Country country;

    public Movie() {
    }

    public Movie(String title) {
        this.title = title;
    }

    /**
     * @param id
     * @param title
     * @param releaseYear
     * @param rating
     * @param summary
     * @param urlIMDB
     */
    public Movie(String id, String title, Integer releaseYear, double rating, String summary, String urlIMDB) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.summary = summary;
        this.urlIMDB = urlIMDB;
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
                ", roles=" + roles +
                ", mainActors=" + mainActors +
                ", directors=" + directors +
                ", types=" + types +
                ", language=" + language +
                ", country=" + country +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrlIMDB() {
        return urlIMDB;
    }

    public void setUrlIMDB(String urlIMDB) {
        this.urlIMDB = urlIMDB;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Actor> getMainActors() {
        return mainActors;
    }

    public void setMainActors(Set<Actor> mainActors) {
        this.mainActors = mainActors;
    }

    public Set<Director> getDirectors() {
        return directors;
    }

    /**
     * Add a Director Object to the Director Collection
     *
     * @param director
     */
    public void addDirector(Director director) {
        this.directors.add(director);
    }

    public void setDirectors(Set<Director> directors) {
        this.directors = directors;
    }

    public Set<Type> getTypes() {
        return types;
    }

    public void setTypes(Set<Type> types) {
        this.types = types;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
