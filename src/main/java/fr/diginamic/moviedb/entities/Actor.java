package fr.diginamic.moviedb.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.diginamic.moviedb.deserializers.ActorDeserializer;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "actor")
@JsonDeserialize(using = ActorDeserializer.class)
public class Actor extends Person {



    @Column(name = "height")
    private Double height;

    @OneToMany(mappedBy = "actor")
    private Set<Role> roles;

    @ManyToMany(mappedBy = "mainActors")
    private Set<Movie> mainRoleMovies;

    public Actor() {

    }

    /**
     *
     * @param id
     * @param fullName
     * @param birthDate
     * @param urlIMDB
     * @param height
     */
    public Actor(String id, String fullName, LocalDate birthDate, String urlIMDB, Double height) {
        super(id, fullName, birthDate, urlIMDB);
        this.height = height;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Movie> getMainRoleMovies() {
        return mainRoleMovies;
    }

    public void setMainRoleMovies(Set<Movie> mainRoleMovies) {
        this.mainRoleMovies = mainRoleMovies;
    }
}
