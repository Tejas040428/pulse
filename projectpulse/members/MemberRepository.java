package com.cds.projectpulse.members;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.cds.projectpulse.project.Project;

@Repository
public interface MemberRepository extends MongoRepository<Members, ObjectId> {

	List<Members> findByProject(Project project);

}
