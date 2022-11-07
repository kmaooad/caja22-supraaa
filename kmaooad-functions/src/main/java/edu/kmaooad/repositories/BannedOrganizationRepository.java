package edu.kmaooad.repositories;

import edu.kmaooad.models.BannedOrganization;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannedOrganizationRepository extends MongoRepository<BannedOrganization, Long> {
}
