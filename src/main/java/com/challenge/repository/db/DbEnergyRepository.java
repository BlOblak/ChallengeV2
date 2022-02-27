package com.challenge.repository.db;

import com.challenge.domain.model.Measurement;
import com.challenge.domain.model.MeasurementByRange;
import com.challenge.repository.EnergyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Slf4j
@Repository
public class DbEnergyRepository implements EnergyRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Measurement getLatest(){
        log.info("DbEnergyRepository -> getLatest invoked");
        // Sort
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.DESC, "timePoint"));
        // Limit
        LimitOperation limit = Aggregation.limit(1);
        // Aggregation
        Aggregation aggregate = newAggregation(
                sort,
                limit
        );

        List<Measurement> result = null;
        try {
            result = mongoTemplate.aggregate(aggregate, "Measurements", Measurement.class).getMappedResults();
        }
        catch(Exception e){
            log.error("DbEnergyRepository -> getLatest, exception: " + e.getMessage());
        }
        log.info("DbEnergyRepository -> getLatest ended");
        return result != null ? result.get(0) : null;
    }

    @Override
    public List<Measurement> getMeasurementsByTimeRange(MeasurementByRange measurementByRange) {
        log.info("DbEnergyRepository -> getMeasurementsByTimeRange invoked");
        // Match
        Criteria cr = Criteria.where("timePoint").gt(measurementByRange.getFrom()).lt(measurementByRange.getTo());
        MatchOperation match = Aggregation.match(cr);
        // Sort
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.DESC, "timePoint"));
        // Aggregation
        Aggregation aggregate = newAggregation(
                match,
                sort
        );

        List<Measurement> result = null;
        try {
            result = mongoTemplate.aggregate(aggregate, "Measurements", Measurement.class).getMappedResults();
        }
        catch(Exception e){
            log.error("DbEnergyRepository -> getMeasurementsByTimeRange, exception: " + e.getMessage());
        }
        log.info("DbEnergyRepository -> getMeasurementsByTimeRange ended");
        return result;
    }
}
