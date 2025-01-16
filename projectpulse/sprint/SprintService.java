package com.cds.projectpulse.sprint;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cds.projectpulse.constant.ConstantUtil;
import com.cds.projectpulse.project.Project;
import com.cds.projectpulse.project.ProjectRepository;

@Service
public class SprintService {

  @Autowired
  private SprintRepository sprintRepository;

  @Autowired
  private ProjectRepository projectRepository;

  public List<SprintDTO> getAllSprintsAgainstProject(String projectExternalId)
      throws RuntimeException {
    Optional<Project> project = projectRepository.findByExternalId(projectExternalId);
    if (project.isEmpty()) {
      throw new RuntimeException(
          ConstantUtil.PROJECT_AGAINST_EX_ID + projectExternalId + ConstantUtil.NOT_FOUND);
    }
    // Find the sprints on the project
    List<Sprint> sprints = sprintRepository.findByProject(project.get());
    return sprints.stream().map(sprint -> sprint.toDTO()).toList();
  }

  public SprintDTO getSprintByExternalId(String projectExternalId, String sprintExternalId)
      throws RuntimeException {
    Optional<Project> project = projectRepository.findByExternalId(projectExternalId);

    if (project.isEmpty()) {
      throw new RuntimeException(
          ConstantUtil.PROJECT_AGAINST_EX_ID + projectExternalId + ConstantUtil.NOT_FOUND);
    }

    List<Sprint> sprints = sprintRepository.findByProject(project.get());
    Optional<Sprint> sprint = sprints.stream()
        .filter(s -> s.getExternalId().equals(sprintExternalId)).findFirst();
    return sprint.map(value -> value.toDTO()).orElse(null);
  }

  public SprintDTO createSprint(String projectExternalId, SprintDTO newSprint)
      throws RuntimeException {
    Optional<Project> project = projectRepository.findByExternalId(projectExternalId);

    if (project.isEmpty()) {
      throw new RuntimeException(
          ConstantUtil.PROJECT_AGAINST_EX_ID + projectExternalId + ConstantUtil.NOT_FOUND);
    }

    // Create the Sprint
    Sprint sprint = newSprint.toModel();
    sprint.setProject(project.get());
    Sprint savedSprint = sprintRepository.save(sprint);
    return savedSprint.toDTO();

  }

}
