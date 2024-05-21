package fr.diginamic.moviedb.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

    @Id
    @Column(length = 10)
    private String id;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "birthdate")
    private LocalDate birthDate;

    @Column(name = "urlimdb")
    private String urlIMDB;

    @ManyToOne
    @JoinColumn(name = "id_birthplace")
    private Birthplace birthplace;

    public Person() {
    }

    /**
     *
     * @param id
     * @param fullName
     * @param birthDate
     * @param urlIMDB
     */
    public Person(String id, String fullName, LocalDate birthDate, String urlIMDB) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.urlIMDB = urlIMDB;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", birthDate=" + birthDate +
                ", urlIMDB='" + urlIMDB + '\'' +
                ", birthplace=" + birthplace +
                '}';
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


}
