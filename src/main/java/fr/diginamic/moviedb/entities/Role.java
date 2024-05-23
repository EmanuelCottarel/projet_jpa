package fr.diginamic.moviedb.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.diginamic.moviedb.deserializers.RoleDeserializer;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "role")
@JsonDeserialize(using = RoleDeserializer.class)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_actor")
    private Actor actor;

    @ManyToOne
    @JoinColumn(name = "id_movie")
    private Movie movie;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", actor=" + actor+
                '}';
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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
