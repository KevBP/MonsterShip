package com.monstership.model.module;

import com.monstership.model.Enhancements;

import javax.persistence.Entity;

@Entity
public class Shield extends Module {
    @Override
    public Enhancements effect() {
        return null;
    }

    @Override
    public boolean upgrade() {
        return false;
    }

    @Override
    public long upgradeCost() {
        return 0;
    }
}
