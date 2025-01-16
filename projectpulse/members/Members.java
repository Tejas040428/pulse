package com.cds.projectpulse.members;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import com.cds.projectpulse.project.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "members")
public class Members {
	@Id
	private ObjectId id;

	private String externalId;

	@DocumentReference
	private Project project;

	private String name;
	private int allocationPercentage;
	private String role;
	private String skills;
	private Date actualEndDate;
	private int positionOnRow;
}
