package org.ere.contest.cfkafkaservice.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.ere.contest.cfkafkaservice.service.CacheService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CacheKafkaConsumer {

    private final CacheService cacheService;

    public CacheKafkaConsumer(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @KafkaListener(
            topics = "cache-control",
            groupId = "${spring.kafka.consumer.group-id}",
            autoStartup = "${listen.auto.start:true}",
            concurrency = "${listen.concurrency:1}"
    )
    public void listen(ConsumerRecord<?, ?> record) {
        var key = record.key();
        cacheService.evict((String) key);
    }
}
