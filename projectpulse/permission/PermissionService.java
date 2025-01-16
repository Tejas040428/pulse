package com.cds.projectpulse.permission;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
	@Autowired
	private PermissionRepository permissionRepository;

	public List<Permission> getAllPermissions() {
		return permissionRepository.findAll();
	}

	public Permission getPermissionByExternalId(String externalId) {
		return permissionRepository.findByExternalId(externalId).orElse(null);
	}

	public Permission savePermission(Permission permission) {
		if (permission.getExternalId() == null || permission.getExternalId().isEmpty()) {
			permission.setExternalId(UUID.randomUUID().toString());
		}
		return permissionRepository.save(permission);
	}

	public void deletePermissionByExternalId(String externalId) {
		permissionRepository.deleteByExternalId(externalId);
	}
}
