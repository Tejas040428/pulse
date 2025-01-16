package com.cds.projectpulse.sprint;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.cds.projectpulse.project.Project;

public interface SprintRepository extends MongoRepository<Sprint, ObjectId> {

    List<Sprint> findByProject(Project project);

}
