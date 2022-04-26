package com.projetb3.api.service;

import org.springframework.data.domain.Sort;

import java.util.Optional;

public class Orderer {

    public static Sort.Direction getOrder(Optional<String> orderBy) {
        if (orderBy.isPresent() && "ASC".equals(orderBy)) {
            return Sort.Direction.ASC;
        }
        return Sort.Direction.DESC;
    }
}
