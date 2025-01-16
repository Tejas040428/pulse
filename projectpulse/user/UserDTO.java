package com.cds.projectpulse.user;


import com.cds.projectpulse.role.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Boolean canLogin;
    private String password;
    private Roles role;
}
