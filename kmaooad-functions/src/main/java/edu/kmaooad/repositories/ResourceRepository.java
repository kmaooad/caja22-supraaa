package edu.kmaooad.repositories;

import edu.kmaooad.models.Resource;
import edu.kmaooad.models.ResourceCompositeField;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends MongoRepository<Resource,Long> {

    boolean existsByRealResource(ResourceCompositeField resourceCompositeField);

    Resource findByRealResource(ResourceCompositeField resourceCompositeField);

}
