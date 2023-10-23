package com.augusto.user.payload;

public record ProductDto(
    String name,
    Long user,
    String environment
) {
    
}
