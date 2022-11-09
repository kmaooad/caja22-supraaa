package edu.kmaooad.repositories;

import edu.kmaooad.models.BannedDepartment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannedDepartmentRepository extends MongoRepository<BannedDepartment, Long> {
}
