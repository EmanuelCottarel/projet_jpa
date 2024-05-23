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

    @Column(name = "height")
    private Double height;

    @OneToMany(mappedBy = "actor")
    private Set<Role> roles;

    @ManyToMany(mappedBy = "mainActors")
    private Set<Movie> mainRoleMovies;

    public Actor() {

    }

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

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
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
