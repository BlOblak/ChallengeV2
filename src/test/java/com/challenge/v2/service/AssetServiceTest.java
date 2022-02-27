package com.challenge.v2.service;

import com.challenge.domain.model.Measurement;
import com.challenge.domain.model.MeasurementByRange;
import com.challenge.repository.EnergyRepository;
import com.challenge.repository.MongoRep;
import com.challenge.service.AssetService;
import com.challenge.service.SequenceGeneratorService;
import com.challenge.v2.domain.model.MeasurementMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssetServiceTest {

    @Mock
    private EnergyRepository dbEnergyRepository;
    @Mock
    private MongoRep mongoRep;
    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    private AssetService assetService;

    @BeforeEach
    public void setup() {
        assetService = new AssetService(dbEnergyRepository, mongoRep, sequenceGeneratorService);
    }

    @Test
    public void whenGetAll_thenReturnAll(){
        // Create some measurements
        Measurement first = MeasurementMock.standardMeasurement(Date.from(Instant.now()));
        Measurement second = MeasurementMock.standardMeasurement(Date.from(Instant.now().plus(5, ChronoUnit.SECONDS)));
        Measurement third = MeasurementMock.standardMeasurement(Date.from(Instant.now().plus(10, ChronoUnit.SECONDS)));
        List<Measurement> measurementList = new ArrayList<>();
        measurementList.add(first);
        measurementList.add(second);
        measurementList.add(third);
        // Mock
        when(mongoRep.findAll(PageRequest.of(0, 20))).thenReturn(new PageImpl<>(measurementList));
        // Call
        Page result = assetService.getAll(0, 20);
        // Assertions
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.getContent().size());
        Assertions.assertTrue(result.getContent().contains(first));
        Assertions.assertTrue(result.getContent().contains(second));
        Assertions.assertTrue(result.getContent().contains(third));
        // Verify
        verify(mongoRep, times(1)).findAll(PageRequest.of(0, 20));
    }

    @Test
    public void whenGetById_thenReturnOne(){
        // Create some measurements
        Measurement first = MeasurementMock.standardMeasurement(Date.from(Instant.now()));
        // Mock
        when(mongoRep.findById(Long.valueOf(first.getId()))).thenReturn(Optional.of(first));
        // Call
        Measurement result = assetService.get(Long.valueOf(first.getId()));
        // Assertions
        Assertions.assertNotNull(result);
        Assertions.assertEquals(first, result);
        // Verify
        verify(mongoRep, times(1)).findById(Long.valueOf(first.getId()));
    }

    @Test
    public void whenInsert_thenReturnInserted(){
        // Create some measurements
        Measurement first = MeasurementMock.standardMeasurement(Date.from(Instant.now()));
        // Mock
        when(mongoRep.insert(first)).thenReturn(first);
        // Call
        Measurement result = assetService.insert(first);
        // Assertions
        Assertions.assertNotNull(result);
        Assertions.assertEquals(first, result);
        // Verify
        verify(mongoRep, times(1)).insert(first);
    }

    @Test
    public void whenGetLatest_thenReturnLatest(){
        // Create some measurements
        Measurement first = MeasurementMock.standardMeasurement(Date.from(Instant.now()));
        Measurement second = MeasurementMock.standardMeasurement(Date.from(Instant.now().plus(5, ChronoUnit.SECONDS)));
        Measurement third = MeasurementMock.standardMeasurement(Date.from(Instant.now().plus(10, ChronoUnit.SECONDS)));
        List<Measurement> measurementList = new ArrayList<>();
        measurementList.add(first);
        measurementList.add(second);
        measurementList.add(third);
        // Mock
        when(dbEnergyRepository.getLatest()).thenReturn(third);
        // Call
        Measurement result = assetService.getLatest();
        // Assertions
        Assertions.assertNotNull(result);
        Assertions.assertEquals(third, result);
        // Verify
        verify(dbEnergyRepository, times(1)).getLatest();
    }

    @Test
    public void whenGetByTimeRange_thenReturnList(){
        // Create some measurements
        Measurement second = MeasurementMock.standardMeasurement(Date.from(Instant.now().plus(5, ChronoUnit.SECONDS)));
        Measurement third = MeasurementMock.standardMeasurement(Date.from(Instant.now().plus(10, ChronoUnit.SECONDS)));
        List<Measurement> measurementList = new ArrayList<>();
        measurementList.add(second);
        measurementList.add(third);

        MeasurementByRange measurementByRange = MeasurementByRange.builder()
                .from(Instant.now().plus(3, ChronoUnit.SECONDS))
                .to(Instant.now().plus(12, ChronoUnit.SECONDS)).build();

        // Mock
        when(dbEnergyRepository.getMeasurementsByTimeRange(measurementByRange)).thenReturn(measurementList);
        // Call
        List<Measurement> result = assetService.getMeasurementsByTimeRange(measurementByRange);
        // Assertions
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(second));
        Assertions.assertTrue(result.contains(third));
        // Verify
        verify(dbEnergyRepository, times(1)).getMeasurementsByTimeRange(measurementByRange);
    }
}
