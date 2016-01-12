package com.monstership.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
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

    public void connect() throws Exception {
        try {
            memberManager.connect(newMember);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("member", newMember);
            externalContext.redirect("./game/home.xhtml");
        } catch (Exception e) {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error connection", "Connection Unsuccessful");
            facesContext.addMessage(null, m);
        }
    }

    public void register() throws Exception {
        try {
            memberManager.register(newMember);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("member", newMember);
            externalContext.redirect("./game/home.xhtml");
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
