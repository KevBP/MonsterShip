package com.monstership.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.monstership.model.Member;
import com.monstership.service.MemberManager;

@Model
public class MemberController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private MemberManager memberManager;

    private Member newMember;

    @Produces
    @Named
    public Member getNewMember() {
        return newMember;
    }

    public String connect() throws Exception {
        try {
            memberManager.connect(newMember);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Connected!", "Connection successful"));
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("member", newMember);
            return "/game/home.xhtml";
        } catch (Exception e) {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error connection", "Connection Unsuccessful");
            facesContext.addMessage(null, m);
            return null;
        }
    }

    public void register() throws Exception {
        try {
            memberManager.register(newMember);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful"));
            initNewMember();
        } catch (Exception e) {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error registration", "Registration Unsuccessful");
            facesContext.addMessage(null, m);
        }
    }

    @PostConstruct
    public void initNewMember() {
        newMember = new Member();
    }
}
