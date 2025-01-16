package com.cds.projectpulse.sprint;

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
@Document(collection = "Sprint")
public class Sprint {
     @Id
    private ObjectId id;

    private String externalId;
    
    @DocumentReference
    private Project project;
    
    private String sprintOrderNumber;
    private Date startDate;
    private Date endDate;
    private String tasks;
    private Status status;

    public SprintDTO toDTO() {
        return SprintDTO.builder()
        .externalId(externalId)
        .sprintOrderNumber(sprintOrderNumber)
        .startDate(startDate)
        .endDate(endDate)
        .tasks(tasks)
        .status(status)
        .build();

    }
}
