package com.monstership.model;

import com.monstership.model.gameobject.GameObject;
import com.monstership.util.EntityUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Game {
    @Id
    @GeneratedValue
    private int id;
    private long width;
    private long height;
    private boolean finished;

    @OneToMany(targetEntity = GameObject.class, mappedBy = "game", fetch = FetchType.LAZY)
    private Set<GameObject> gameObjects;

    private Timestamp createDate;

    @PrePersist
    void onCreate() {
        Timestamp date = EntityUtils.now();
        if (getCreateDate() == null) {
            this.setCreateDate(date);
        }
    }

    public int getId() {
        return id;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    private void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}
