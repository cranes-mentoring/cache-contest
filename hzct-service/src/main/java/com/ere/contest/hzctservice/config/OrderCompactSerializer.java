package com.ere.contest.hzctservice.config;

import com.hazelcast.nio.serialization.compact.CompactReader;
import com.hazelcast.nio.serialization.compact.CompactSerializer;
import com.hazelcast.nio.serialization.compact.CompactWriter;
import org.ere.contest.orderstarter.model.Order;

import java.time.Instant;

public class OrderCompactSerializer implements CompactSerializer<Order> {

    @Override
    public void write(CompactWriter writer, Order order) {
        writer.writeString("uuid", order.uuid());
        writer.writeString("username", order.username());
        writer.writeInt32("timestamp", (int) order.timestamp().toEpochMilli());
    }

    @Override
    public Order read(CompactReader reader) {
        String uuid = reader.readString("uuid");
        String username = reader.readString("username");
        Instant timestamp = Instant.ofEpochMilli(reader.readInt32("timestamp"));
        return new Order(uuid, username, timestamp);
    }

    @Override
    public Class<Order> getCompactClass() {
        return Order.class;
    }

    @Override
    public String getTypeName() {
        return "order";
    }
}