package com.cds.projectpulse.permission;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/permissions")
public class PermissionsController {
	@Autowired
	private PermissionService permissionService;

	// fetch all the permissions
	@GetMapping
	public List<Permission> getAllPermissions() {
		return permissionService.getAllPermissions();
	}

	// fetch permissions by externalID
	@GetMapping("/{externalId}")
	public Permission getPermissionByExternalId(@PathVariable String externalId) {
		return permissionService.getPermissionByExternalId(externalId);
	}

	// create permissions
	@PostMapping
	public Permission createPermission(@RequestBody Permission permission) {
		return permissionService.savePermission(permission);
	}

	// delete permissions by externalID
	@DeleteMapping("/{externalId}")
	public void deletePermissionByExternalId(@PathVariable String externalId) {
		permissionService.deletePermissionByExternalId(externalId);
	}
}
