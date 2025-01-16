package com.cds.projectpulse.project;

import java.util.List;

import com.cds.projectpulse.constant.Status;
import com.cds.projectpulse.members.MembersDTO;
import com.cds.projectpulse.milestones.MilestoneDTO;
import com.cds.projectpulse.sprint.SprintDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProjectDTO {
    
    private String externalId;
    private String name;
    private String shortDescription;
    private String fullDescription;
    private String riskStatus;
    private String scopeStatus;
    private String dependencyStatus;
    private String dependentProjectExtId;
    private String timelineStatus;

    
    private List<MembersDTO> projectMembers;
    
    private List<MilestoneDTO> projectMilestones;

    private List<SprintDTO> sprints;

    public Project toProjectEntity() {
        return Project.builder()
        .externalId(this.externalId)
        .name(this.name)
        .shortDescription(this.shortDescription)
        .fullDescription(this.fullDescription)
        .riskStatus(Status.getStatusFromValue(this.riskStatus))
        .scopeStatus(Status.getStatusFromValue(this.scopeStatus))
        .dependencyStatus(Status.getStatusFromValue(this.dependencyStatus))
        .dependentProjectExtId(this.dependentProjectExtId)
        .timelineStatus(Status.getStatusFromValue(this.timelineStatus))
        .build();
    }
}
