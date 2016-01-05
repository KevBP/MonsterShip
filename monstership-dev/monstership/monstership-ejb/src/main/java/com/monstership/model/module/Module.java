package com.monstership.model.module;

import com.monstership.model.Enhancements;
import com.monstership.model.Upgradable;
import com.monstership.model.gameobject.Starship;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Module implements Upgradable {
    protected int level = 1;
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
}
