package com.monstership.controller;

import com.monstership.model.Member;
import com.monstership.service.GameManager;
import com.monstership.service.IGameManagerLocal;

import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Model
public class GameController {

    @EJB
    private IGameManagerLocal gameManager;
    private ExternalContext externalContext;

    public GameController() {
        externalContext = FacesContext.getCurrentInstance().getExternalContext();
        //gameManager = new GameManager((Member) externalContext.getSessionMap().get("member"));
    }

    @Produces
    @Named
    public Member getMember() {
        return gameManager.getMember();
    }

    public void disconnect() {
        externalContext.invalidateSession();
        externalContext.getSessionMap().clear();
        try {
            externalContext.redirect("../index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
