package edu.kmaooad.repositories;

import edu.kmaooad.models.BannedUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannedUserRepository extends MongoRepository<BannedUser, Long> {
}
