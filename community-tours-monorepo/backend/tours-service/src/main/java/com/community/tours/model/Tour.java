package com.community.tours.model;

import java.math.BigDecimal;

public record Tour(Long id, String name, String location, BigDecimal price) {
}
