package com.profiler.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
