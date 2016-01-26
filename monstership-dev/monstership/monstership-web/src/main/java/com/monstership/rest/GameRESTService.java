package com.monstership.rest;

import com.monstership.model.Game;
import com.monstership.model.Member;
import com.monstership.model.gameobject.Planet;
import com.monstership.model.gameobject.Starship;
import com.monstership.service.GameManager;
import com.monstership.service.IGameManagerLocal;

import java.util.Collections;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;

@Path("/game")
@RequestScoped
public class GameRESTService {

    @Context
    private HttpServletRequest request;

    @EJB
    transient IGameManagerLocal gameManager;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Game getGame() {
        setMemberWithSession();
        return gameManager.getOrCreateCurrentGame();
    }

    @GET
    @Path("/starship")
    @Produces(MediaType.APPLICATION_JSON)
    public Starship getStarship() {
        setMemberWithSession();
        return gameManager.getOrCreateStarship();
    }

    public void setMemberWithSession(){
        if (gameManager.getMember() == null){
            Long member = (Long) request.getSession().getAttribute("member_id");
            gameManager.setMemberById(member);
        }
        Logger.getLogger(GameRESTService.class.getSimpleName()).info("gameManager's member " + gameManager.getMember());
    }

    @GET
    @Path("/member")
    @Produces(MediaType.APPLICATION_JSON)
    public Member getMember() {
        setMemberWithSession();
        return gameManager.getMember();
    }

    @GET
    @Path("/move/{direction:(UP|DOWN|LEFT|RIGHT)}")
    @Produces(MediaType.APPLICATION_JSON)
    public Starship move(@PathParam("direction") String direction) {
        setMemberWithSession();
        return gameManager.move(direction);
    }

    @GET
    @Path("/planets")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Planet> listPlanets(
            @DefaultValue("-1") @QueryParam("x") int x,
            @DefaultValue("-1") @QueryParam("y") int y
    ) {
        setMemberWithSession();
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
        setMemberWithSession();
        if (x != -1 && y != -1){
            return Collections.EMPTY_LIST;
        }
        return gameManager.listStarships((Integer)x, (Integer)y);
    }

}
