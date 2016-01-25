package com.monstership.rest;

import com.monstership.model.Game;
import com.monstership.model.Member;
import com.monstership.model.gameobject.Planet;
import com.monstership.model.gameobject.Starship;
import com.monstership.service.GameManager;
import java.util.Collections;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/game")
@RequestScoped
public class GameRESTService {

    @Inject
    transient GameManager gameManager;

    @GET
    @Path("/")
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

    @GET
    @Path("/move/{direction:(UP|DOWN|LEFT|RIGHT)}")
    @Produces(MediaType.APPLICATION_JSON)
    public Starship move(@PathParam("direction") String direction) {
        return gameManager.move(direction);
    }

    @GET
    @Path("/planets")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Planet> listPlanets(
            @DefaultValue("-1") @QueryParam("x") int x,
            @DefaultValue("-1") @QueryParam("y") int y
    ) {
        if (x != -1 && y != -1){
            return gameManager.listPlanet((Integer)x, (Integer)y);
        }
        return gameManager.listPlanet();
    }
    
    @GET
    @Path("/starships")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Starship> listStarships(
            @DefaultValue("-1") @QueryParam("x") int x,
            @DefaultValue("-1") @QueryParam("y") int y
    ) {
        if (x != -1 && y != -1){
            return Collections.EMPTY_LIST;
        }
        return gameManager.listStarships(x, y);
    }

}
