package com.cds.projectpulse.milestones;

import java.util.List;
import java.util.Optional;

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
@RequestMapping("/projects/{projectExternalId}/milestones")
public class MilestonesController {

    @Autowired
    private MilestoneRepository milestonesRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public ResponseEntity<List<Milestones>> getAllMilestonesByProject(@PathVariable String projectExternalId) {
        Optional<Project> project = projectRepository.findByExternalId(projectExternalId);
        if (project.isPresent()) {
            List<Milestones> milestones = milestonesRepository.findByProject(project.get());
            return new ResponseEntity<>(milestones, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{milestoneExternalId}")
    public ResponseEntity<Milestones> getMilestoneByExternalId(@PathVariable String projectExternalId, @PathVariable String milestoneExternalId) {
        Optional<Project> project = projectRepository.findByExternalId(projectExternalId);
        if (project.isPresent()) {
            List<Milestones> milestones = milestonesRepository.findByProject(project.get());
            Optional<Milestones> milestone = milestones.stream().filter(m -> m.getExternalId().equals(milestoneExternalId)).findFirst();
            return milestone.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Milestones> createMilestone(@PathVariable String projectExternalId, @RequestBody Milestones milestone) {
        Optional<Project> project = projectRepository.findByExternalId(projectExternalId);
        if (project.isPresent()) {
            milestone.setProject(project.get());
            Milestones savedMilestone = milestonesRepository.save(milestone);
            return new ResponseEntity<>(savedMilestone, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{milestoneExternalId}")
    public ResponseEntity<Milestones> updateMilestone(@PathVariable String projectExternalId, @PathVariable String milestoneExternalId, @RequestBody Milestones updatedMilestone) {
        Optional<Project> project = projectRepository.findByExternalId(projectExternalId);
        if (project.isPresent()) {
            List<Milestones> milestones = milestonesRepository.findByProject(project.get());
            Optional<Milestones> existingMilestone = milestones.stream().filter(m -> m.getExternalId().equals(milestoneExternalId)).findFirst();
            if(existingMilestone.isPresent()){
                updatedMilestone.setId(existingMilestone.get().getId());
                updatedMilestone.setProject(project.get());
                Milestones savedMilestone = milestonesRepository.save(updatedMilestone);
                return new ResponseEntity<>(savedMilestone, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{milestoneExternalId}")
    public ResponseEntity<Void> deleteMilestone(@PathVariable String projectExternalId, @PathVariable String milestoneExternalId) {
        Optional<Project> project = projectRepository.findByExternalId(projectExternalId);
        if (project.isPresent()) {
            List<Milestones> milestones = milestonesRepository.findByProject(project.get());
            Optional<Milestones> existingMilestone = milestones.stream().filter(m -> m.getExternalId().equals(milestoneExternalId)).findFirst();
            if(existingMilestone.isPresent()){
                milestonesRepository.delete(existingMilestone.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
