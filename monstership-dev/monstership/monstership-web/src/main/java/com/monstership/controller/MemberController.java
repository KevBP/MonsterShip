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
    
    public void connect() throws Exception {
    	try {
    		memberManager.connect(newMember);
    		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Connected!", "Connection successful"));
        } catch (Exception e) {
            //String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error connection", "Connection Unsuccessful");
            facesContext.addMessage(null, m);
        }
    }

    public void register() throws Exception {
        try {
            memberManager.register(newMember);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful"));
            initNewMember();
        } catch (Exception e) {
            //String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error registration", "Registration Unsuccessful");
            facesContext.addMessage(null, m);
        }
    }

    @PostConstruct
    public void initNewMember() {
        newMember = new Member();
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }
}
