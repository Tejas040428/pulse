package com.cds.projectpulse.dto;

import java.util.List;

import com.cds.projectpulse.permission.Permission;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    
	public LoginResponse(String token, String firstName, String lastName, String email,
			List<Permission> permissions) {
		// TODO Auto-generated constructor stub
	}
	private String token;
    private String firstName;
    private String lastName;
    private String email;
    private Permission permissions;
}
