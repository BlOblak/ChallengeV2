package com.challenge.service;

import com.challenge.domain.model.Measurement;
import com.challenge.domain.model.MeasurementByRange;
import com.challenge.repository.EnergyRepository;
import com.challenge.repository.MongoRep;
import com.challenge.repository.db.utils.DbPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Slf4j
@CacheConfig(cacheNames = "energy")
@Service
public class AssetService {

    private MongoRep dbMongoRepository;
    private EnergyRepository dbEnergyRepository;
    private SequenceGeneratorService sequenceGeneratorService;

    public static final String key = "latest";
    ObjectMapper mapper;

    public AssetService(EnergyRepository dbEnergyRepository, MongoRep dbMongoRepository, SequenceGeneratorService sequenceGeneratorService) {
        this.dbEnergyRepository = dbEnergyRepository;
        this.dbMongoRepository = dbMongoRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    public Page getAll(int page, int size){
        return dbMongoRepository.findAll(PageRequest.of(page, size));
    }

    public Measurement get(Long id){
        Optional<Measurement> result = dbMongoRepository.findById(id);
        if (result.isPresent()){
            return result.get();
        }
        else return null;
    }

    @CachePut(key = "#root.target.key")
    public Measurement insert(Measurement measurement){
        measurement.setId(sequenceGeneratorService.getSequenceNumber(Measurement.SEQUENCE_NAME));
        return dbMongoRepository.insert(measurement);
    }

    // Custom service methods

    @Cacheable(key = "#root.target.key")
    public Measurement getLatest (){
        log.info("AssetService -> getLatest invoked");
        return dbEnergyRepository.getLatest();
    }

    public List<Measurement> getMeasurementsByTimeRange (MeasurementByRange measurementByRange){
        List<Measurement> result = dbEnergyRepository.getMeasurementsByTimeRange(measurementByRange);
        return result != null && result.size() != 0 ? result : null;
    }
}
