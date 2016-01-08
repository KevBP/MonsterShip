package com.monstership.model.module;

import com.monstership.model.Enhancements;
import com.monstership.model.Upgradable;
import com.monstership.model.gameobject.Starship;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Module implements Upgradable {
    protected int level = 1;
    @Min(0)
    protected int monsterCount = 0;
    @ManyToOne(optional = false)
    protected Starship starship;

    @Id
    @GeneratedValue
    private int id;

    public abstract Enhancements effect();

    public int getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Starship getStarship() {
        return starship;
    }

    public void setStarship(Starship starship) {
        this.starship = starship;
    }

    public int getMonsterCount() {
        return monsterCount;
    }

    public void setMonsterCount(int monsterCount) {
        this.monsterCount = monsterCount;
    }
}
