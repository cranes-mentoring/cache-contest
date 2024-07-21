package org.ere.contest.cfdbservice.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = {
                "org.ere.contest.orderstarter.repository",
                "org.ere.contest.cfdbservice.repository"}
)
@EntityScan(basePackages = {
        "org.ere.contest.orderstarter.model.entity",
        "org.ere.contest.cfdbservice.model.entity",
})
public class JpaCustomConfiguration {

}
