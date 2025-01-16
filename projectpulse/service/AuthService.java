package com.cds.projectpulse.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cds.projectpulse.dto.LoginRequest;
import com.cds.projectpulse.dto.LoginResponse;
import com.cds.projectpulse.project.Project;
import com.cds.projectpulse.user.UserRepository;
import com.cds.projectpulse.user.UserService;
import com.cds.projectpulse.user.Users;
import com.cds.projectpulse.util.JwtUtil;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private JwtUtil jwtUtil;

	// Simulating token blacklist storage (for logout functionality)
	private final TokenStore tokenStore = new TokenStore();

	public LoginResponse login(LoginRequest loginRequest) {
		Optional<Users> userOptional = userRepository.findByEmail(loginRequest.getEmail());

		if (userOptional.isEmpty()) {
			throw new RuntimeException("Invalid email or password");
		}

		Users user = userOptional.get();

		// Validate password
		if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new IllegalArgumentException("Invalid email or password");
		}
		
		Project project;

		// Generate JWT token with required claims
		String token = jwtUtil.generateToken(user.getEmail(), user.getRole().getPermissions(), " ");

		return new LoginResponse(token, user.getFirstName(), user.getLastName(), user.getEmail(),
				user.getRole().getPermissions());
	}

	public void logout(String token) {
		// Add the token to a blacklist
		tokenStore.addTokenToBlacklist(token);
	}

	public boolean isTokenBlacklisted(String token) {
		return tokenStore.isTokenBlacklisted(token);
	}

	public void registerUser(Users user) {
		// TODO Auto-generated method stub
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.saveUser(user);
	}
}
