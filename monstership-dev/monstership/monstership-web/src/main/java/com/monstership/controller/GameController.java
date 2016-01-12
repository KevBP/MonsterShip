package com.monstership.controller;

import com.monstership.model.Member;
import com.monstership.service.GameManager;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Model
public class GameController {

    @Inject
    private GameManager gameManager;
    private ExternalContext externalContext;

    public GameController() {
        externalContext = FacesContext.getCurrentInstance().getExternalContext();
        gameManager = new GameManager((Member) externalContext.getSessionMap().get("member"));
    }

    @Produces
    @Named
    public Member getMember() {
        return gameManager.getMember();
    }

    public void disconnect() {
        externalContext.invalidateSession();
        try {
            externalContext.redirect("../index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
