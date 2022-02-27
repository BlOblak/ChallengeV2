package com.challenge.v2.repository;

import com.challenge.Application;
import com.challenge.domain.model.Measurement;
import com.challenge.domain.model.MeasurementByRange;
import com.challenge.repository.MongoRep;
import com.challenge.repository.db.DbEnergyRepository;
import com.challenge.service.AssetService;
import com.challenge.v2.domain.model.MeasurementMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = {Application.class})
@ContextConfiguration
public class DbEnergyRepositoryTest {

    @Autowired
    private DbEnergyRepository dbEnergyRepository;

    @Autowired
    private MongoRep mongoRep;

    @BeforeEach
    public void setup() {
        mongoRep.deleteAll();
    }

    @Test
    public void whenGetLatest_thenReturnlatest() {
        // Create some measurements
        Measurement first = mongoRep.insert(MeasurementMock.standardMeasurement(Date.from(Instant.now())));
        Measurement second = mongoRep.insert(MeasurementMock.standardMeasurement(Date.from(Instant.now().plus(5, ChronoUnit.SECONDS))));
        Measurement third = mongoRep.insert(MeasurementMock.standardMeasurement(Date.from(Instant.now().plus(10, ChronoUnit.SECONDS))));
        // Get latest measurement
        Measurement searchResult = dbEnergyRepository.getLatest();
        // Assertions
        Assertions.assertNotNull(searchResult);
        Assertions.assertEquals(searchResult.getId(), third.getId());
    }

    @Test
    public void givenFromAndTo_whengetMeasurementsByTimeRange_thenReturnListMeasurements(){
        // Create some measurements
        Measurement first = mongoRep.insert(MeasurementMock.standardMeasurement(Date.from(Instant.now())));
        Measurement second = mongoRep.insert(MeasurementMock.standardMeasurement(Date.from(Instant.now().plus(5, ChronoUnit.SECONDS))));
        Measurement third = mongoRep.insert(MeasurementMock.standardMeasurement(Date.from(Instant.now().plus(10, ChronoUnit.SECONDS))));
        // Get latest measurement
        List<Measurement> searchResult = dbEnergyRepository.getMeasurementsByTimeRange(MeasurementByRange.builder().from(Instant.now().plus(3, ChronoUnit.SECONDS)).to(Instant.now().plus(12, ChronoUnit.SECONDS)).build());
        // Assertions
        Assertions.assertNotNull(searchResult);
        Assertions.assertEquals(2, searchResult.size());
        Assertions.assertTrue(searchResult.contains(second));
        Assertions.assertTrue(searchResult.contains(third));
    }
}
