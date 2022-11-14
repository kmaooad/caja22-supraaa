package edu.kmaooad.repositories;

import edu.kmaooad.models.AccessRule;
import edu.kmaooad.models.AccessRuleCompositeKey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessRuleRepository extends MongoRepository<AccessRule, AccessRuleCompositeKey> {
}
