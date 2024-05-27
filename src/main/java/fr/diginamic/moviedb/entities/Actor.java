package fr.diginamic.moviedb.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.diginamic.moviedb.deserializers.ActorDeserializer;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "actor")
@JsonDeserialize(using = ActorDeserializer.class)
public class Actor{


    /** Identifier of the Actor wich is the IMDB id */
    @Id
    @Column(length = 10)
    private String id;

    /** Fullname format: "Firstname LastName" */
    @Column(name = "fullname")
    private String fullName;

    /** Birthdate */
    @Column(name = "birthdate")
    private LocalDate birthDate;

    /** IMDB url */
    @Column(name = "urlimdb")
    private String urlIMDB;

    /** Birthplace */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_birthplace")
    private Birthplace birthplace;

    /** Height */
    @Column(name = "height")
    private Double height;

    /** Roles */
    @OneToMany(mappedBy = "actor")
    private Set<Role> roles;

    /** Main roles */
    @ManyToMany(mappedBy = "mainActors")
    private Set<Movie> mainRoleMovies;

    /** No argument constructor */
    public Actor() {

    }

    /**
     * Constructor
     * @param id String
     * @param fullName String
     * @param birthDate Birthdate
     * @param urlIMDB String
     * @param height Double
     */
    public Actor(String id, String fullName, LocalDate birthDate, String urlIMDB, Double height) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.urlIMDB = urlIMDB;
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(this.getId(), actor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }

    /**
     * Return the id of the Actor
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * @return String the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Define the fullName
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
     * Define the birthdate
     * @param birthDate - the new birthDate
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the URLIMBD
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
     * @return the Birthplace
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
     * @return the height
     */
    public Double getHeight() {
        return height;
    }

    /**
     * Define the new height
     * @param height - the new height
     */
    public void setHeight(Double height) {
        this.height = height;
    }

    /**
     * @return list of roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Define a new list of roles
     * @param roles - list of the actor roles
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * @return the list of Movies where the actor has a main role
     */
    public Set<Movie> getMainRoleMovies() {
        return mainRoleMovies;
    }

    /**
     * Define a new list of main roles
     * @param mainRoleMovies - list of main roles
     */
    public void setMainRoleMovies(Set<Movie> mainRoleMovies) {
        this.mainRoleMovies = mainRoleMovies;
    }
}
