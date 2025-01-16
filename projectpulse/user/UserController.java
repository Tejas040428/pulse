package com.cds.projectpulse.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cds.projectpulse.service.EmailService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	// Create
	@PostMapping
	public ResponseEntity<Users> createUser(@RequestBody UserDTO userDTO) {
		Users user = Users.builder().firstName(userDTO.getFirstName()).lastName(userDTO.getLastName())
				.email(userDTO.getEmail()).phone(userDTO.getPhone()).canLogin(userDTO.getCanLogin())
				.password(userDTO.getPassword()).role(userDTO.getRole()).build();

		Users savedUser = userService.saveUser(user);
		return ResponseEntity.ok(savedUser);
	}

	// Read All
	@GetMapping
	public ResponseEntity<List<Users>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	// Read by externalId
	@GetMapping("/{externalId}")
	public ResponseEntity<Users> getUserById(@PathVariable String externalId) {
		return userService.getUserById(externalId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	// Update by externalId
	@PutMapping("/{externalId}")
	public ResponseEntity<Users> updateUser(@PathVariable String externalId, @RequestBody UserDTO userDTO) {
		return userService.getUserById(externalId).map(existingUser -> {
			existingUser.setFirstName(userDTO.getFirstName());
			existingUser.setLastName(userDTO.getLastName());
			existingUser.setEmail(userDTO.getEmail());
			existingUser.setPhone(userDTO.getPhone());
			existingUser.setCanLogin(userDTO.getCanLogin());
			existingUser.setPassword(userDTO.getPassword());
			existingUser.setRole(userDTO.getRole());
			return ResponseEntity.ok(userService.saveUser(existingUser));
		}).orElse(ResponseEntity.notFound().build());
	}

	// Delete by externalId
	@DeleteMapping("/{externalId}")
	public ResponseEntity<Void> deleteUser(@PathVariable String externalId) {
		userService.deleteUser(externalId);
		return ResponseEntity.noContent().build();
	}
}
