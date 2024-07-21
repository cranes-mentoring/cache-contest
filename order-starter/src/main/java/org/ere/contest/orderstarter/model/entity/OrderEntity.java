package org.ere.contest.orderstarter.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    private UUID uuid;
    private String username;
    private Instant timestamp;

    public OrderEntity(
            UUID uuid,
            String username,
            Instant timestamp
    ) {
        this.uuid = uuid;
        this.username = username;
        this.timestamp = timestamp;
    }

    public OrderEntity() {

    }

    @Id
    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (OrderEntity) obj;
        return Objects.equals(this.uuid, that.uuid) &&
                Objects.equals(this.username, that.username) &&
                Objects.equals(this.timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, username, timestamp);
    }

    @Override
    public String toString() {
        return "OrderEntity[" +
                "uuid=" + uuid + ", " +
                "username=" + username + ", " +
                "timestamp=" + timestamp + ']';
    }

}
