package com.challenge.domain.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MeasurementByRange {

    @ApiModelProperty(value = "Input range from")
    Instant from;
    @ApiModelProperty(value = "Input range to")
    Instant to;
}
