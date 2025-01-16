package com.cds.projectpulse.project;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends MongoRepository<Project, ObjectId> {
    // Custom query methods can be defined here if needed

    Optional<Project> findByExternalId(String externalId);

    Boolean existsByExternalId(String externalId);

    void deleteByExternalId(String externalId);
}
