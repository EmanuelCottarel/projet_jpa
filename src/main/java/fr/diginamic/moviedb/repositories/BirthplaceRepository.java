package fr.diginamic.moviedb.repositories;

import fr.diginamic.moviedb.entities.Birthplace;
import fr.diginamic.utils.ConnectionDb;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class BirthplaceRepository extends AbstractRepository {
    // https://chatgpt.com/c/ba63a751-f0e3-4798-8fc3-eeb7d1e5ef21

    public Object findOneBy(String field, String value) {
        try (EntityManager em = ConnectionDb.getEm()) {
            TypedQuery<Birthplace> query = em.createQuery("SELECT b FROM Birthplace b WHERE :field LIKE :value", Birthplace.class);

            Birthplace b = query.getSingleResult();
//            ConnectionDb.closeConnection();
            return b;

        }


    }
}
