package com.cds.projectpulse.permission;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.cds.projectpulse.role.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "permissions")
public class Permission {
	@Id
	private String id;
	private String externalId;
	private String name;

	@DocumentReference
	private Roles role;
}
