package com.monstership.service;

import com.monstership.model.Game;
import com.monstership.model.Member;
import com.monstership.model.gameobject.Planet;
import com.monstership.model.gameobject.Starship;

import javax.ejb.Local;
import javax.ejb.Lock;
import java.util.List;

@Local
public interface IGameManagerLocal {
    Member getMember();

    void setMember(Member member);
    void setMemberById(Long memberId);

    Game getOrCreateCurrentGame();

    List<Starship> listStarships(Integer x, Integer y);

    List<Planet> listPlanet(Integer x, Integer y);

    List<Planet> listPlanet();

    Starship getOrCreateStarship();

    Starship move(String direction);
}
