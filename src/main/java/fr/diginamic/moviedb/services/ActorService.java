package fr.diginamic.moviedb.services;

import com.fasterxml.jackson.databind.JsonNode;
import fr.diginamic.moviedb.entities.Actor;
import fr.diginamic.moviedb.entities.Birthplace;

import java.io.IOException;

public class ActorService extends AbstractService {

    public ActorService() {
    }

    @Override
    public Actor create(JsonNode actorNode) throws IOException {

        Actor actor = em.find(Actor.class, actorNode.get("id").asText());
        if (actor == null) {
            actor = objectMapper.readerFor(Actor.class).readValue(actorNode);

            if (actor.getBirthplace() == null && actorNode.get("naissance").has("lieuNaissance")) {
                BirthplaceService birthplaceService = new BirthplaceService();
                Birthplace birthplace = birthplaceService.create(actorNode.get("naissance"));
                if (birthplace.getName().isEmpty()) {
                    actor.setBirthplace(null);
                } else {
                    em.persist(birthplace);
                    actor.setBirthplace(birthplace);
                }
            }
            return actor;

        }
//        actor = em.merge(actor);
        return actor;
    }
}
