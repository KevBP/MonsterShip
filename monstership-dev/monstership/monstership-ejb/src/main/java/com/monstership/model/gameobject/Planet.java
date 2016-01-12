package com.monstership.model.gameobject;

import com.monstership.model.MonsterProducer;

import javax.persistence.Entity;
import java.util.Random;

@Entity
public class Planet extends GameObject implements MonsterProducer {
    private final static Random rand = new Random();

    public Planet() {
        this.setModel("planet");
    }

    @Override
    public int produce(Starship starship) {
        return rand.nextInt((int) (Math.sqrt(starship.getLevel()) * 10)) + 3;
    }
}
