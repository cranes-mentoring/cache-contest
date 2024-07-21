package org.ere.contest.orderstarter.model.request;

import java.time.Instant;

public record OrderRequest(
        String uuid,
        String username,
        Instant timestamp
) {
}
