package com.cds.projectpulse.milestones;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.cds.projectpulse.constant.Status;
import com.cds.projectpulse.project.Project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Milestones")
public class Milestones {

    @Id
    private ObjectId id;

    private String externalId;
    
    @DocumentReference
    private Project project;
    
    private String milestonePhase;
    private Date plannedStartDate;
    private Date plannedEndDate;
    private Date actualStartDate;
    private Date actualEndDate;
    private Status status;
    private int positionOnRow;
}
