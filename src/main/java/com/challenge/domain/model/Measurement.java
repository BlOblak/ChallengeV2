package com.challenge.domain.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.TimeSeries;
import org.springframework.data.mongodb.core.timeseries.Granularity;

@Setter
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TimeSeries(collection="Measurements", timeField = "timePoint", metaField = "assetId", granularity = Granularity.SECONDS)
public class Measurement implements Serializable {

    @Transient
    public static final String SEQUENCE_NAME = "measurements_sequence";

    @Id
    @ApiModelProperty(value = "Id of the measurement")
    private int id;
    @ApiModelProperty(value = "Date and time of measurement")
    private Date timePoint;
    @ApiModelProperty(value = "Id of the measurement asset")
    private UUID assetId;
    @ApiModelProperty(value = "Power measured")
    private BigDecimal activePower;
    @ApiModelProperty(value = "Voltage measured")
    private BigDecimal voltage;
}
