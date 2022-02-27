package com.challenge.resource.api;

import com.challenge.domain.model.Measurement;
import com.challenge.domain.model.MeasurementByRange;
import com.challenge.resource.api.utils.WebApiResponse;
import com.challenge.service.AssetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(tags = "Measurement resource")
@Slf4j
@RequestMapping(value = "/public/measurement", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class EnergyResource {

    @Autowired
    AssetService serviceAssetService;

    @ApiOperation(value = "Retrieve all measurements")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful retrieval."),
            @ApiResponse(code = 404, message = "Measurements not found."),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @GetMapping
    public WebApiResponse<List<Measurement>> getAllMeasurement(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size){
        log.info("EnergyResource -> getAllMeasurement invoked");
        // Default size
        if(size == null){
            size = 20;
        }
        // Default page
        if(page == null){
            page = 0;
        }

        Page result = serviceAssetService.getAll(page, size);
        return new WebApiResponse<>(result.getContent(), result);
    }

    @ApiOperation(value = "Retrieve one measurement")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful retrieval."),
            @ApiResponse(code = 404, message = "Measurement not found."),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @GetMapping(value = "/{id}")
    public Measurement getMeasurement(@PathVariable Long id){
        log.info("EnergyResource -> getMeasurement invoked");
        return serviceAssetService.get(id);
    }

    @ApiOperation(value = "Inserts a measurement")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful insert."),
            @ApiResponse(code = 400, message = "Insert unsuccessful."),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @PostMapping
    public Measurement insertMeasurement(@RequestBody Measurement measurement){
        log.info("EnergyResource -> createOrUpdateMeasurement invoked");
        return serviceAssetService.insert(measurement);
    }

    // Custom resoureces

    @ApiOperation(value = "Retrieve latest measurement")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful retrieval."),
            @ApiResponse(code = 404, message = "Measurement not found."),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @GetMapping(value = "/latest")
    public Measurement getLatestMeasurement(){
        log.info("EnergyResource -> getLatestMeasurement invoked");
        return serviceAssetService.getLatest();
    }

    @ApiOperation(value = "Retrieve all measurements in given time range")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful retrieval."),
            @ApiResponse(code = 404, message = "Measurement not found."),
            @ApiResponse(code = 500, message = "Internal server error.")}
    )
    @PostMapping(value = "/byRange")
    public WebApiResponse<List<Measurement>> getMeasurementsByTimeRange(@RequestBody MeasurementByRange measurementByRange){
        log.info("EnergyResource -> getMeasurementsByTimeRange invoked");
        return new WebApiResponse<>(serviceAssetService.getMeasurementsByTimeRange(measurementByRange));
    }
}
