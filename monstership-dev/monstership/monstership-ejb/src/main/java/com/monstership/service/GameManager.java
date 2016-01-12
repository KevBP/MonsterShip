package com.monstership.service;

import com.monstership.model.Game;
import com.monstership.model.Member;
import com.monstership.model.gameobject.Starship;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Stateful
@SessionScoped
public class GameManager implements Serializable {

    private static final transient Logger log = Logger.getAnonymousLogger();

    @Inject
    private EntityManager em;

    private Member member;
    private Game currentGame;
    private Starship starship;

    public GameManager() {
    }

    public GameManager(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Game getOrCreateCurrentGame() {
        if (currentGame == null || currentGame.isFinished()) {
            Query q = em.createQuery("from Game where finished is false", Game.class);
            q.setMaxResults(1);
            List resultList = q.getResultList();
            if (resultList.isEmpty()) {
                log.info("Creating new game");
                currentGame = new Game();
                em.persist(currentGame);
                em.flush();
            } else {
                currentGame = (Game) resultList.get(0);
                log.info("using game " + currentGame.getId());
            }
        }
        return currentGame;
    }

    public Starship getOrCreateStarship() {
        if(getMember() == null) {
            return null;
        }
        if(starship == null || !starship.isActive()) {
            Query q = em.createQuery("from Starship a where active is true and a.member.id = :user", Starship.class);
            q.setParameter("user", getMember().getId());
            q.setMaxResults(1);
            List resultList = q.getResultList();
            if (resultList.isEmpty()) {
                log.info("Creating new starship");
                starship = new Starship();
                Game currentGame1 = getOrCreateCurrentGame();
                starship.setGame(currentGame1);
                starship.setMember(getMember());
                em.persist(starship);
                em.flush();
            } else {
                starship = (Starship) resultList.get(0);
                log.info("using starship " + currentGame.getId());
            }
        }
        return starship;
    }
}
