package fr.diginamic.moviedb.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.diginamic.moviedb.deserializers.RoleDeserializer;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "role")
@JsonDeserialize(using = RoleDeserializer.class)
public class Role {

    /** Id - auto incremented */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** name */
    @Column(name = "name")
    private String name;

    /** actor */
    @ManyToOne
    @JoinColumn(name = "id_actor")
    private Actor actor;

    /** movie */
    @ManyToOne
    @JoinColumn(name = "id_movie")
    private Movie movie;

    public Role() {
    }

    /**
     * Constructor
     * @param name - the name of the new role
     */
    public Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return movie.getTitle() +
                " - name='" + name + '\'' +
                ", actor=" + actor.getFullName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(name, role.name) && Objects.equals(actor, role.actor) && Objects.equals(movie, role.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, actor, movie);
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Define the new name
     * @param name - the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the actor
     */
    public Actor getActor() {
        return actor;
    }

    /**
     * Define the new actor
     * @param actor - the new actor
     */
    public void setActor(Actor actor) {
        this.actor = actor;
    }

    /**
     * @return the movie
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * Define the new Movie
     * @param movie - the new Movie
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
