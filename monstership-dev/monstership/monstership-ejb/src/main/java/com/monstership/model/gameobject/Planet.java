package com.monstership.model.gameobject;

import com.monstership.model.MonsterProducer;

import javax.persistence.Entity;

@Entity
public class Planet extends GameObject implements MonsterProducer {
    private long monsterCount;

    public Planet() {
        this.setModel("planet");
    }

    public long getMonsterCount() {
        return monsterCount;
    }

    public synchronized void setMonsterCount(long monsterCount) {
        this.monsterCount = monsterCount;
    }

    @Override
    public int produce() {
        return 0; // TODO
    }
}
