package com.cds.projectpulse.sprint;

import java.util.Date;
import java.util.UUID;

import com.cds.projectpulse.constant.Status;

import lombok.Builder;

@Builder
public record SprintDTO(String externalId, String sprintOrderNumber, Date startDate, Date endDate, String tasks, Status status) {
    
    public Sprint toModel() {
       return Sprint.builder()
       .externalId(UUID.randomUUID().toString())
       .sprintOrderNumber(sprintOrderNumber)
       .startDate(startDate)
       .endDate(endDate)
       .tasks(tasks)
       .status(status)
       .build();
    }
}
