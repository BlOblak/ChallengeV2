package com.challenge.repository;

import com.challenge.domain.model.Measurement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRep extends MongoRepository<Measurement, Long> {
}
