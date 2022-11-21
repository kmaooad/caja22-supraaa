package edu.kmaooad.repositories;

import edu.kmaooad.models.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends MongoRepository<Resource,Long> {
}
