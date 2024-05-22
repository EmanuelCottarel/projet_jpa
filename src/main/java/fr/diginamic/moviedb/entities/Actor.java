package fr.diginamic.moviedb.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "actor")
public class Actor extends Person {

    @Column(name = "height")
    private Double height;

    @OneToMany(mappedBy = "actor")
    private Set<Role> roles;

    @ManyToMany(mappedBy = "mainActors")
    private Set<Movie> mainRoleMovies;

    public Actor() {

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
