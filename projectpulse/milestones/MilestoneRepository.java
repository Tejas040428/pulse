package com.cds.projectpulse.milestones;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cds.projectpulse.project.Project;

@Repository
public interface MilestoneRepository extends MongoRepository<Milestones, ObjectId> {

    List<Milestones> findByProject(Project project);
}
