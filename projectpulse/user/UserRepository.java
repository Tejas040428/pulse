package com.cds.projectpulse.user;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cds.projectpulse.role.Roles;

public interface UserRepository extends MongoRepository<Users, String> {

	Optional<Users> findByExternalId(String externalId);

	void deleteByExternalId(String externalId);

	Optional<Users> findByEmail(String email);

	Optional<Roles> findByFirstNameAndLastName(String firstName, String lastName);
}