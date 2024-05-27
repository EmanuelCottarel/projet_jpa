package fr.diginamic.moviedb.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.diginamic.moviedb.deserializers.BirthplaceDeserializer;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "birthplace")
@JsonDeserialize(using = BirthplaceDeserializer.class)
public class Birthplace {

    /** Auto incremented ID  */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Name */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /** Set of actors born in this place */
    @OneToMany(mappedBy = "birthplace")
    private Set<Actor> actors;

    /** Set of directors born in this place */
    @OneToMany(mappedBy = "birthplace")
    private Set<Director> directors;

    /**
     *
     * @param name - the name of the new birthplace
     */
    public Birthplace(String name) {
        this.name = name;
    }

    /**
     * No parameter constructor
     */
    public Birthplace() {
    }

    /**
     * @return the name of the birthplace
     */
    public String getName() {
        return name;
    }

    /**
     * Define the new name
     * @param name - the name of the birthplace
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id of the birthplace
     */
    public Integer getId() {
        return id;
    }
}
