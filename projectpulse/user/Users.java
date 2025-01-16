package com.cds.projectpulse.user;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.cds.projectpulse.role.Roles;
import com.cds.projectpulse.profilepicture.ProfilePicture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class Users {
    @Id
    private String id;
    private String externalId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Boolean canLogin;
    //private List<String> actions;
    private String password;
    private Integer loginFailureCount;
    //private ProfilePicture profilePicture;

    @DocumentReference(lazy = true)  // One-to-One relationship with Roles
    private Roles role;
}

