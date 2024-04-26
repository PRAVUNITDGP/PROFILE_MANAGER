package com.profiler.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
