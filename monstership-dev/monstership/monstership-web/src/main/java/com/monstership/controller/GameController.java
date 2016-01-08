package com.monstership.controller;

import com.monstership.model.Member;
import com.monstership.service.GameManager;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Model
public class GameController {

    @Inject
    private GameManager gameManager;
    private Member member;
    private FacesContext context;

    public GameController() {
        context = FacesContext.getCurrentInstance();
        member = (Member) context.getExternalContext().getSessionMap().get("member");
    }

    @Produces
    @Named
    public Member getMember() {
        return member;
    }

    public void disconnect() {
        context.getExternalContext().invalidateSession();
        try {
            context.getExternalContext().redirect("../index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
