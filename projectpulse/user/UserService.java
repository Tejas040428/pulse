package com.cds.projectpulse.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cds.projectpulse.constant.ConstantUtil;
import com.cds.projectpulse.permission.Permission;
import com.cds.projectpulse.role.RoleRepository;
import com.cds.projectpulse.role.Roles;
import com.cds.projectpulse.service.EmailService;

@Service
@Transactional
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	private Permission permission;

	/**
	 * Validate if the user exists by first and last name.
	 */
	public boolean userExists(String firstName, String lastName) {
		return userRepository.findByFirstNameAndLastName(firstName, lastName).isPresent();
	}

	// create or save new user
	public Users saveUser(Users newUser) {
		try {
			log.info(ConstantUtil.RECEIEVED_USER + newUser);
            String password = newUser.getPassword();
			// user validation based on first and last name
			if (userExists(newUser.getFirstName(), newUser.getLastName())) {
				throw new IllegalArgumentException("User with this name already exists");
			}

			// user validation based on emailid
			if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
				throw new IllegalArgumentException("Email is already in use");
			}
            
			// encodes the random generated password and sets it
			newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

			// generates externalID
			if (newUser.getExternalId() == null || newUser.getExternalId().isEmpty()) {
				newUser.setExternalId(UUID.randomUUID().toString());
			}

			// role validation
			if (newUser.getRole() != null) {
				Optional<Roles> roleOptional = roleRepository.findByExternalId(newUser.getRole().getExternalId());

				if (roleOptional.isEmpty()) {
					throw new IllegalArgumentException("Role not found.");
				}
				newUser.setRole(roleOptional.get());
			}
			Users savedUser = userRepository.save(newUser);

			// Send email to user
			emailService.sendEmail(newUser.getEmail(), "Welcome to ProjectPulse",
					"Hello " + newUser.getFirstName() + ",\n\n" + "Your account has been successfully created." + "\n\n"
							+ "please find your login credentials below: " + "\n\n" + "userid:" + savedUser.getEmail()
							+ "\n\n" + "password:" + password + "\n\n"
							+ "Best regards,\nThe ProjectPulse Team");

			return savedUser;
		} catch (Exception e) {
			log.error(ConstantUtil.SAVE_USER_ERROR + "{}", e.getMessage());
			throw e;
		}
	}

	// Read All
	public List<Users> getAllUsers() {
		return userRepository.findAll();
	}

	// Read by ID
	public Optional<Users> getUserById(String externalId) {
		return userRepository.findById(externalId);
	}

	// Delete
	public void deleteUser(String externalId) {
		userRepository.deleteById(externalId);
	}

}
