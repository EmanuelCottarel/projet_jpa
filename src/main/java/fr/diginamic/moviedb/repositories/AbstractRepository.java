package fr.diginamic.moviedb.repositories;

public abstract class AbstractRepository {

    public abstract Object findOneBy(String field, String value);
}
