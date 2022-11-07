package edu.kmaooad.repositories;

import edu.kmaooad.models.CompositeKey;
import edu.kmaooad.models.DepartmentAccessRule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentAccessRuleRepository extends MongoRepository<DepartmentAccessRule, CompositeKey> {

}