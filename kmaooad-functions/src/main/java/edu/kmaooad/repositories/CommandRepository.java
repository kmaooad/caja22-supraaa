package edu.kmaooad.repositories;

import edu.kmaooad.models.Command;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandRepository extends MongoRepository<Command, Long> {
}
