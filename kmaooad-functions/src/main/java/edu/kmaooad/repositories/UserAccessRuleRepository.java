package edu.kmaooad.repositories;

import edu.kmaooad.models.CompositeKey;
import edu.kmaooad.models.UserAccessRule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccessRuleRepository extends MongoRepository<UserAccessRule, CompositeKey> {

}
