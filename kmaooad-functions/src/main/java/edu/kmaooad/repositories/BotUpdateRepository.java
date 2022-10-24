package edu.kmaooad.repositories;

import edu.kmaooad.models.BotUpdate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotUpdateRepository extends MongoRepository<BotUpdate, Long> {
}
