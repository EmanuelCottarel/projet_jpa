package fr.diginamic.moviedb.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
public class Actor extends Person {

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
