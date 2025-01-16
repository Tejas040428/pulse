package com.cds.projectpulse.profilepicture;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "profilepicture")
public
class ProfilePicture {
	private String location;
	private Boolean validated;
}
