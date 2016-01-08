package com.monstership.model.log;

import com.monstership.model.gameobject.Starship;
import com.monstership.util.EntityUtils;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class CombatLog {
    @Id
    @GeneratedValue
    private int id;

    private long monsterCountBefore;
    private long monsterCountAfter;

    private Timestamp createDate;

    @ManyToOne(optional = false)
    private Starship starship;

    @PrePersist
    void onCreate() {
        Timestamp date = EntityUtils.now();
        if (getCreateDate() == null){
            this.setCreateDate(date);
        }
    }

    public int getId() {
        return id;
    }

    public long getMonsterCountBefore() {
        return monsterCountBefore;
    }

    public void setMonsterCountBefore(long monsterCountBefore) {
        this.monsterCountBefore = monsterCountBefore;
    }

    public long getMonsterCountAfter() {
        return monsterCountAfter;
    }

    public void setMonsterCountAfter(long monsterCountAfter) {
        this.monsterCountAfter = monsterCountAfter;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    private void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}
