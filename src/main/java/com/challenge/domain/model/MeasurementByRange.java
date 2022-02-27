package com.challenge.domain.model;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MeasurementByRange {

    Instant from;
    Instant to;
}
