package com.challenge.v2.domain.model;

import com.challenge.domain.model.Measurement;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class MeasurementMock {
    public static Measurement standardMeasurement(Date timePoint){
        return Measurement.builder()
                .id(1)
                .assetId(UUID.randomUUID())
                .timePoint(timePoint)
                .activePower(BigDecimal.valueOf(100))
                .voltage(BigDecimal.valueOf(230))
                .build();
    }
}
