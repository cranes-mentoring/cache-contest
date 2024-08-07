package org.ere.contest.cfkafkaservice.config.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.ere.contest.cfkafkaservice.service.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaRemovalListener implements RemovalListener<Object, Object> {

    private static final String TOPIC = "cache-control";
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaRemovalListener(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onRemoval(@Nullable Object key, @Nullable Object value, RemovalCause cause) {
        logger.info("Removed key: {}, value: {}", key, value);
        try {
            String json = objectMapper.writeValueAsString(value);
            kafkaTemplate.send(TOPIC, (String) key, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }}
}
