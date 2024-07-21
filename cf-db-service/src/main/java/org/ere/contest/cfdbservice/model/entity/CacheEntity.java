package org.ere.contest.cfdbservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Objects;

@Table
@Entity(name = "cache_control")
public class CacheEntity {
    @Id private String name;
    @Column(name = "last_update")
    private Instant lastUpdate;

    public CacheEntity(String name, Instant lastUpdate) {
        this.name = name;
        this.lastUpdate = lastUpdate;
    }

    public CacheEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheEntity that = (CacheEntity) o;
        return Objects.equals(name, that.name) && Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastUpdate);
    }
}
