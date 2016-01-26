package com.monstership.controller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.monstership.model.Member;
import com.monstership.service.GameManager;
import com.monstership.service.IGameManagerLocal;
import com.monstership.service.IMemberManagerLocal;
import com.monstership.service.MemberManager;

import java.util.logging.Level;
import java.util.logging.Logger;

@Model
public class MemberController {

    @Inject
    private FacesContext facesContext;
    @EJB
    private IMemberManagerLocal memberManager;

    private Member newMember;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Produces
    @Named
    public Member getNewMember() {
        return newMember;
    }

    public void connect() throws Exception {
        try {
            Member member = memberManager.connect(newMember);
            logger.warning("connect - we got user " + member + " with id " + member.getId());
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("member", member);
            externalContext.getSessionMap().put("member_id", member.getId());
            externalContext.redirect("./game/home.xhtml");
        } catch (Exception e) {
            logger.log(Level.WARNING, "connecting", e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error connection", "Connection Unsuccessful");
            facesContext.addMessage(null, m);
        }
    }

    public void register() throws Exception {
        try {
            Member member = memberManager.register(newMember);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("member", member);
            externalContext.getSessionMap().put("member_id", member.getId());
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
