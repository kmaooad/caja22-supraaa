package edu.kmaooad.repositories;

import edu.kmaooad.models.CompositeKey;
import edu.kmaooad.models.OrganizationAccessRule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationAccessRuleRepository extends MongoRepository<OrganizationAccessRule, CompositeKey> {

}