package com.cds.projectpulse.role;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.cds.projectpulse.permission.Permission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "roles")
public class Roles {
	@Id
	private String id;
	private String externalId;
	private String name;

	@DocumentReference(lazy = true) // One-to-Many relationship with Permissions
	private List<Permission> permissions;

	@DocumentReference
	private User user;
}
