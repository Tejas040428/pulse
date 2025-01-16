package com.cds.projectpulse.members;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembersDTO {

	@JsonIgnore
	private String id;

	private String externalId;
	private String projectId; // Assuming projectId is used instead of the full Project object
	private String name;
	private int allocationPercentage;
	private String role;
	private String skills;
	private Date actualEndDate;
	private int positionOnRow;
}
