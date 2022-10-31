package com.digitalhouse.catalogservice.domain.repository;



import com.digitalhouse.catalogservice.domain.model.document.Catalog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CatalogRepository extends MongoRepository<Catalog, String> {
    Optional<Catalog> findByGenre(String genre);
}
