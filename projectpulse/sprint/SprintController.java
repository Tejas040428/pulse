package com.cds.projectpulse.sprint;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cds.projectpulse.project.Project;
import com.cds.projectpulse.project.ProjectRepository;

@RestController
@RequestMapping("/projects/{projectExternalId}/sprints")
public class SprintController {

   @Autowired
   SprintService sprintService;

   @Autowired
   private SprintRepository sprintRepository;

   @Autowired
   private ProjectRepository projectRepository;

    @GetMapping
    public ResponseEntity<List<SprintDTO>> getAllSprintsByProject(@PathVariable String projectExternalId) {
       List<SprintDTO> sprints = sprintService.getAllSprintsAgainstProject(projectExternalId);
       if(sprints.isEmpty()) {
        return new ResponseEntity<>(sprints, HttpStatus.NOT_FOUND);
       }
       return new ResponseEntity<>(sprints, HttpStatus.OK);
    }

    @GetMapping("/{sprintExternalId}")
    public ResponseEntity<SprintDTO> getSprintByExternalId(@PathVariable String projectExternalId, @PathVariable String sprintExternalId) {
        SprintDTO sprint = sprintService.getSprintByExternalId(projectExternalId, sprintExternalId);
        if (sprint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
        return new ResponseEntity<>(sprint, HttpStatus.OK);
    }

    @PutMapping("/{sprintExternalId}")
    public ResponseEntity<Sprint> updateSprint(@PathVariable String projectExternalId, @PathVariable String sprintExternalId, @RequestBody Sprint updatedSprint) {
        Optional<Project> project = projectRepository.findByExternalId(projectExternalId);
        if (project.isPresent()) {
            List<Sprint> sprints = sprintRepository.findByProject(project.get());
            Optional<Sprint> existingSprint = sprints.stream().filter(s -> s.getExternalId().equals(sprintExternalId)).findFirst();
            if(existingSprint.isPresent()){
                updatedSprint.setId(existingSprint.get().getId());
                updatedSprint.setProject(project.get());
                Sprint savedSprint = sprintRepository.save(updatedSprint);
                return new ResponseEntity<>(savedSprint, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{sprintExternalId}")
    public ResponseEntity<Void> deleteSprint(@PathVariable String projectExternalId, @PathVariable String sprintExternalId) {
        Optional<Project> project = projectRepository.findByExternalId(projectExternalId);
        if (project.isPresent()) {
            List<Sprint> sprints = sprintRepository.findByProject(project.get());
            Optional<Sprint> existingSprint = sprints.stream().filter(s -> s.getExternalId().equals(sprintExternalId)).findFirst();
            if(existingSprint.isPresent()){
                sprintRepository.delete(existingSprint.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-sprints")
    public ResponseEntity<List<Sprint>> createMultipleSprints(@PathVariable String projectExternalId, @RequestBody List<Sprint> sprints) {
        Optional<Project> project = projectRepository.findByExternalId(projectExternalId);
        if (project.isPresent()) {
            List<Sprint> savedSprints = sprints.stream()
            .map(sprint -> Sprint.builder()
                    .externalId(sprint.getExternalId())
                    .sprintOrderNumber(sprint.getSprintOrderNumber())
                    .startDate(sprint.getStartDate())
                    .endDate(sprint.getEndDate())
                    .tasks(sprint.getTasks())
                    .status(sprint.getStatus())
                    .project(project.get())
                    .build())
            .map(sprintRepository::save)
            .collect(Collectors.toList());
            return new ResponseEntity<>(savedSprints, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/update-sprints")
    public ResponseEntity<List<Sprint>> updateMultipleSprints(@PathVariable String projectExternalId, @RequestBody List<Sprint> updatedSprints) {
        Optional<Project> project = projectRepository.findByExternalId(projectExternalId);
        if (project.isPresent()) {
            List<Sprint> savedSprints = updatedSprints.stream().map(updatedSprint -> {
                        List<Sprint> sprints = sprintRepository.findByProject(project.get());
                        Optional<Sprint> existingSprint = sprints.stream().filter(s -> s.getExternalId().equals(updatedSprint.getExternalId())).findFirst();
                        if (existingSprint.isPresent()) {
                            // Update existing sprint using ID
                            return Sprint.builder()
                            .id(existingSprint.get().getId())
                            .externalId(updatedSprint.getExternalId())
                            .sprintOrderNumber(updatedSprint.getSprintOrderNumber())
                            .startDate(updatedSprint.getStartDate())
                            .endDate(updatedSprint.getEndDate())
                            .tasks(updatedSprint.getTasks())
                            .status(updatedSprint.getStatus())
                            .project(project.get())
                            .build();
                        } else {
                            //Handle if sprint is not found. I am returning null here which will be filtered out.
                            return null;
                        }
                    }).filter(sprint -> sprint != null)
                    .map(sprintRepository::save)
                    .collect(Collectors.toList());
            if(savedSprints.size() > 0) {
                return new ResponseEntity<>(savedSprints, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
