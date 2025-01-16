package com.cds.projectpulse.permission;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends MongoRepository<Permission, String> {
	Optional<Permission> findByExternalId(String externalId);

	List<Permission> findByExternalIdIn(List<String> externalIds);

	void deleteByExternalId(String externalId);

	List<Permission> findAllByExternalIdIn(List<String> permissionExternalIds);
}
