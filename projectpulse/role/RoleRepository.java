package com.cds.projectpulse.role;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Roles, String> {
	// Custom query to find by externalId
	Optional<Roles> findByExternalId(String externalId);

	// Custom query to delete by externalId
	void deleteByExternalId(String externalId);

	List<Roles> findAllByExternalIdIn(List<String> roleExternalIds);
}
