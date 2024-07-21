package org.ere.contest.orderstarter.model;

import java.time.Instant;

public record Order(
        String uuid,
        String username,
        Instant timestamp) {
}
