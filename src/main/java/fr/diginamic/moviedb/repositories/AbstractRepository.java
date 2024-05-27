package fr.diginamic.moviedb.repositories;

public abstract class AbstractRepository {

    /**
     * Find One Object for a given field
     * @param field - The name of the field searched, must match with one of the Entity's
     * @param value - the searched value
     * @return the result of the search: an Object or null
     */
    public abstract Object findOneBy(String field, String value);

    /**
     * Check if the given string match one of the entity fields to avoid SQL injections
     * @param fieldName - the name o the field
     * @return true if the field exists in the matching Entity else false
     */
    public abstract boolean isValidField(String fieldName);
}
