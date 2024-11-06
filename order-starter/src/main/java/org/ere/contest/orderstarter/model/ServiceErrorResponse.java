package org.ere.contest.orderstarter.model;

public record ServiceErrorResponse(
        int status,
        String error,
        String details
) {
}
