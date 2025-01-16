package com.cds.projectpulse.milestones;

import java.util.Date;

import com.cds.projectpulse.constant.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MilestoneDTO {

    @JsonIgnore // Ignores the id field during serialization/deserialization
    private String id;

    private String externalId;
    private String milestonePhase;
    private Date plannedStartDate;
    private Date plannedEndDate;
    private Date actualStartDate;
    private Date actualEndDate;
    private Status status;
    private int positionOnRow;
}

