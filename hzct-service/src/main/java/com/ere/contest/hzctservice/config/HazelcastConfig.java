package com.ere.contest.hzctservice.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.CompactSerializationConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {

    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config();
        SerializationConfig serializationConfig = config.getSerializationConfig();

        CompactSerializationConfig compactSerializationConfig = serializationConfig.getCompactSerializationConfig();
        compactSerializationConfig.addSerializer(new OrderCompactSerializer());

        return Hazelcast.newHazelcastInstance(config);
    }
}
