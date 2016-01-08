package com.monstership.model.gameobject;

import com.monstership.Const;
import com.monstership.model.Game;
import com.monstership.util.EntityUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * An abstract class may be declared an entity by decorating the class with @Entity.
 * Abstract entities are like concrete entities but cannot be instantiated.
 * Abstract entities can be queried just like concrete entities.
 * If an abstract entity is the target of a query, the query operates on all the concrete subclasses of the abstract entity
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class GameObject implements Const, Serializable {
    @Id
    @GeneratedValue
    private int id;
    protected long xPos;
    protected long yPos;
    protected String model;
    protected Float rotation;
    protected boolean visible = true;

    @ManyToOne(optional = false)
    protected Game game;

    @Version
    private Integer version;

    private Timestamp createDate;
    private Timestamp updateDate;
    private Timestamp moveDate;

    @PrePersist
    void onCreate() {
        Timestamp date = EntityUtils.now();
        this.setCreateDate(date);
        this.setUpdateDate(date);
    }

    @PreUpdate
    void onPersist() {
        this.setUpdateDate(EntityUtils.now());
    }

    public long getxPos() {
        return xPos;
    }

    public void setxPos(long xPos) {
        if (xPos != this.xPos) {
            this.xPos = xPos;
            setUpdateDate(EntityUtils.now());
        }
    }

    public long getyPos() {
        return yPos;
    }

    public void setyPos(long yPos) {
        if (yPos != this.yPos) {
            this.yPos = yPos;
            setUpdateDate(EntityUtils.now());
        }
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Float getRotation() {
        return rotation;
    }

    public void setRotation(Float rotation) {
        this.rotation = rotation;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    protected void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    protected void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Timestamp getMoveDate() {
        return moveDate;
    }

    protected void setMoveDate(Timestamp moveDate) {
        this.moveDate = moveDate;
    }

    public Integer getVersion() {
        return version;
    }


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}