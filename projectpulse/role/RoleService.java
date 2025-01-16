package com.cds.projectpulse.role;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cds.projectpulse.constant.ConstantUtil;
import com.cds.projectpulse.permission.Permission;
import com.cds.projectpulse.permission.PermissionRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository; // Properly inject PermissionRepository

	// Get all roles from the repository
	public List<Roles> getAllRoles() {
		return roleRepository.findAll();
	}

	// Get a role by its externalId (not the primary ID)
	public Roles getRoleByExternalId(String externalId) {
		return roleRepository.findByExternalId(externalId).orElse(null);
	}

	public Roles saveRole(Roles role) {
		// Generate and set a new externalId for the role
		role.setExternalId(UUID.randomUUID().toString());

		// Validate that permissions exist for the given externalIds
		List<String> permissionExternalIds = role.getPermissions().stream().map(Permission::getExternalId)
				.collect(Collectors.toList());

		// Fetch permissions from the database
		List<Permission> existingPermissions = permissionRepository.findAllByExternalIdIn(permissionExternalIds);

		// Check if the count of found permissions matches the provided externalIds
		if (existingPermissions.size() != permissionExternalIds.size()) {
			throw new IllegalArgumentException(ConstantUtil.PERMISSION_NOT_FOUND);
		}

		// Set the permissions to the role object
		role.setPermissions(existingPermissions);

		// Save the role
		return roleRepository.save(role);
	}

	// Delete a role by its externalId
	public void deleteRoleByExternalId(String externalId) {
		roleRepository.deleteByExternalId(externalId);
	}
}
