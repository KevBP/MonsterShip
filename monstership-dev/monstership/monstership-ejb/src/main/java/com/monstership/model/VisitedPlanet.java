package com.monstership.model;

import com.monstership.model.gameobject.Planet;
import com.monstership.model.gameobject.Starship;
import com.monstership.util.EntityUtils;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(
        uniqueConstraints=
            @UniqueConstraint(columnNames={"planet_id", "starship_id"}))
@Entity
public class VisitedPlanet {
    @Id
    private long id;

    @ManyToOne(optional = false)
    private Planet planet;
    @ManyToOne(optional = false)
    private Starship starship;

    private Timestamp createDate;

    @PrePersist
    void onCreate() {
        Timestamp date = EntityUtils.now();
        if (getCreateDate() == null){
            this.setCreateDate(date);
        }
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public Starship getStarship() {
        return starship;
    }

    public void setStarship(Starship starship) {
        this.starship = starship;
    }
}
