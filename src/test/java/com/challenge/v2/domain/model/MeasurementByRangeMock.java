package com.challenge.v2.domain.model;

import com.challenge.domain.model.MeasurementByRange;
import java.time.Instant;

public class MeasurementByRangeMock {
    public static MeasurementByRange standardMeasurementByRangeMock(Instant from, Instant to){
        return MeasurementByRange.builder()
                .from(from)
                .to(to)
                .build();
    }
}
