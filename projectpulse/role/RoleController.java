package com.cds.projectpulse.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	// get all the roles
	@GetMapping
	public List<Roles> getAllRoles() {
		return roleService.getAllRoles();
	}

	// fetch roles by externalID
	@GetMapping("/{externalId}")
	public Roles getRoleByExternalId(@PathVariable String externalId) {
		return roleService.getRoleByExternalId(externalId);
	}

	// save roles
	@PostMapping
	public Roles createRole(@RequestBody Roles role) {
		return roleService.saveRole(role);
	}

	// delete roles by externalID
	@DeleteMapping("/{externalId}")
	public void deleteRoleByExternalId(@PathVariable String externalId) {
		roleService.deleteRoleByExternalId(externalId);
	}
}
