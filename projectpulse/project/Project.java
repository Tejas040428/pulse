package com.cds.projectpulse.project;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.cds.projectpulse.constant.Status;
import com.cds.projectpulse.members.Members;
import com.cds.projectpulse.milestones.Milestones;
import com.cds.projectpulse.sprint.Sprint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Project")
public class Project {
    @Id
    private ObjectId id;
    private String externalId;
    private String name;
    private String shortDescription;
    private String fullDescription;
    private Status riskStatus;
    private Status scopeStatus;
    private Status dependencyStatus;
    private String dependentProjectExtId;
    private Status timelineStatus;

    @DocumentReference(lazy = true)
    private List<Members> projectMembers;

    @DocumentReference(lazy = true)
    private List<Milestones> projectMilestones;

    @DocumentReference(lazy = true)
    private List<Sprint> sprints;

}
