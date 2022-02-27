package com.challenge.repository;

import com.challenge.domain.model.Measurement;
import com.challenge.domain.model.MeasurementByRange;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnergyRepository {
    Measurement getLatest();
    List<Measurement> getMeasurementsByTimeRange(MeasurementByRange measurementByRange);
}
