package edu.kmaooad.repositories;

import edu.kmaooad.models.Resource;
import edu.kmaooad.models.ResourceCompositeField;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceRepository extends MongoRepository<Resource,Long> {

    boolean existsByRealResource(ResourceCompositeField resourceCompositeField);

    Optional<Resource> findByRealResource(ResourceCompositeField resourceCompositeField);

}
