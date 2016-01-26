package com.monstership.service;

import com.monstership.model.Game;
import com.monstership.model.Member;
import com.monstership.model.gameobject.Planet;
import com.monstership.model.gameobject.Starship;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

@Stateful
@SessionScoped
public class GameManager implements Serializable, IGameManagerLocal {

    private static final transient Logger log = Logger.getAnonymousLogger();

    @PersistenceContext
    private EntityManager em;

    private Member member;

    private Game currentGame;

    @Resource
    private SessionContext ctx;

    public GameManager() {
        if (ctx != null){
            Object member = ctx.getContextData().get("member");
            if (member != null && member instanceof Member){
                setMember((Member) member);
            }
        }
    }

    public GameManager(Member member) {
        this.member = member;
    }

    @Override
    public Member getMember() {
        return member;
    }

    @Override
    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public void setMemberById(Long memberId) {
        Query query = em.createQuery("from Member where id = :id");
        query.setParameter("id", memberId);
        query.setMaxResults(1);
        List resultList = query.getResultList();
        if (!resultList.isEmpty()){
            setMember((Member) resultList.get(0));
        }
    }

    @Override
    @Lock(LockType.WRITE)
    public Game getOrCreateCurrentGame() {
        if (currentGame == null || currentGame.isFinished()) {
            Query q = em.createQuery("from Game where finished = false", Game.class);
            q.setMaxResults(1);
            List resultList = q.getResultList();
            if (resultList.isEmpty()) {
                log.info("Creating new game");
                currentGame = new Game();
                em.persist(currentGame);
                em.flush();
                generateRandomPlanet(currentGame);
            } else {
                currentGame = (Game) resultList.get(0);
                log.info("using game " + currentGame.getId());
            }
        }
        return currentGame;
    }

    private long generateRandomPlanet(Game game) {
        Random random = new Random();
        long l = game.getWidth() * game.getHeight() * 20 / 100;
        Set<Coordinate> coordinates = new TreeSet<>();
        for (int i = 0; i < l; i++) {
            long xcoord = random.nextInt((int) game.getWidth());
            long ycoord = random.nextInt((int) game.getHeight());
            Planet planet = new Planet();
            planet.setGame(game);
            planet.setXPos(xcoord);
            planet.setYPos(ycoord);
            if (coordinates.add(new Coordinate(planet.getxPos(), planet.getyPos()))){
                em.persist(planet);
            }else{
                i--;
            }
        }
        return l;
    }

    static class Coordinate implements Comparable<Coordinate>{
        private final long x;
        private final long y;

        Coordinate(long x, long y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Coordinate o) {
            int compare = Long.compare(x, o.x);
            return compare != 0 ? compare : Long.compare(y, o.y);
        }
    }
    
    @Override
    public List<Starship> listStarships(Integer x, Integer y) {
        Query q = em.createQuery("from Starship s where game = :game and xPos between :x and (:x + 19 - 1) and yPos between :y and (:y + 19 - 1)", Starship.class);
        q.setParameter("game", getOrCreateCurrentGame());
        q.setParameter("x", x);
        q.setParameter("y", y);
        List resultList = q.getResultList();
        return resultList;
    }

    @Override
    public List<Planet> listPlanet(Integer x, Integer y) {
        Query q = em.createQuery("from Planet p where game = :game and xPos between :x and (:x + 19 - 1) and yPos between :y and (:y + 19 - 1)", Planet.class);
        q.setParameter("game", getOrCreateCurrentGame());
        q.setParameter("x", x);
        q.setParameter("y", y);
        List resultList = q.getResultList();
        return resultList; // TODO
    }

    @Override
    public List<Planet> listPlanet() {
        //TODO viewport
        Query q = em.createQuery("from Planet p where game = :game", Planet.class);
        q.setParameter("game", getOrCreateCurrentGame());
        List resultList = q.getResultList();
        return resultList; // TODO
    }

    @Override
    @Lock(LockType.WRITE)
    public Starship getOrCreateStarship() {
        if (getMember() == null) {
            return null;
        }
        Query q = em.createQuery("from Starship a where active = true and a.member.id = :user", Starship.class);
        q.setParameter("user", getMember().getId());
        q.setMaxResults(1);
        List resultList = q.getResultList();
        Starship starship;
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
            log.info("using starship " + getOrCreateCurrentGame().getId());
        }
        return starship;
    }

    @Override
    @Lock(LockType.WRITE)
    public Starship move(String direction) {
        Starship starship = getOrCreateStarship();
        if (starship.getActionPoint() > 0) {
            boolean moved = false;
            switch (direction.toUpperCase().trim()) {
                case "UP":
                    moved = starship.setYPos(starship.getyPos() + 1);
                    break;
                case "DOWN":
                    moved = starship.setYPos(starship.getyPos() - 1);
                    break;
                case "LEFT":
                    moved = starship.setXPos(starship.getxPos() - 1);
                    break;
                case "RIGHT":
                    moved = starship.setXPos(starship.getxPos() + 1);
                    break;
            }
            if (moved) {
                starship.setActionPoint(starship.getActionPoint() - 1L);
            }
            em.persist(starship);
            em.flush();
        }
        return starship;
    }
}
