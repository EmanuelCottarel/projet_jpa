package fr.diginamic.moviedb.services;

import com.fasterxml.jackson.databind.JsonNode;
import fr.diginamic.moviedb.entities.Actor;
import fr.diginamic.moviedb.entities.Role;

import java.io.IOException;

public class RoleService extends AbstractService {

    public RoleService() {
    }

    @Override
    public Role create(JsonNode roleNode) throws IOException {

        Role role = objectMapper.readerFor(Role.class).readValue(roleNode);

        Actor actor = em.find(Actor.class, roleNode.get("acteur").get("id").asText());
        if (actor == null) {
            ActorService actorService = new ActorService();
            actor = actorService.create(roleNode.get("acteur"));
        }
        actor = em.merge(actor);
        role.setActor(actor);

        return role;
    }
}
