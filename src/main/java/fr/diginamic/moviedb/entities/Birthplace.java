package fr.diginamic.moviedb.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.diginamic.moviedb.deserializers.BirthplaceDeserializer;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "birthplace")
@JsonDeserialize(using = BirthplaceDeserializer.class)
public class Birthplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "birthplace")
    private Set<Person> persons;

    public Birthplace(String name) {
        this.name = name;
    }

    public Birthplace() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }
}
