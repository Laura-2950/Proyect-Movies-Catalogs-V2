package com.digitalhouse.catalogservice.domain.repository;

import com.digitalhouse.catalogservice.domain.model.document.SeriesHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SeriesHistoryRepository extends MongoRepository<SeriesHistory, String> {
}
