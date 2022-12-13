package edu.kmaooad.repositories;

import edu.kmaooad.models.Command;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommandRepository extends MongoRepository<Command, Long> {

    Optional<Command> findCommandByName(String name);

}
