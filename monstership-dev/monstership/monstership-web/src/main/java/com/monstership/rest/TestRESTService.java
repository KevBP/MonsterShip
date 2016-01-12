package com.monstership.rest;

import com.monstership.model.Game;
import com.monstership.model.Member;
import com.monstership.model.gameobject.Starship;
import com.monstership.service.GameManager;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;

@Path("/test")
@RequestScoped
public class TestRESTService {

    @Inject
    transient GameManager gameManager;

    @GET
    @Path("/game")
    @Produces(MediaType.APPLICATION_JSON)
    public Game getGame() {
        return gameManager.getOrCreateCurrentGame();
    }

    @GET
    @Path("/starship")
    @Produces(MediaType.APPLICATION_JSON)
    public Starship getStarship() {
        return gameManager.getOrCreateStarship();
    }

    @GET
    @Path("/member")
    @Produces(MediaType.APPLICATION_JSON)
    public Member getMember() {
        return gameManager.getMember();
    }
}
