package com.cds.projectpulse.project;

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

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<Project> getProjectById(@PathVariable String externalId) {
        Optional<Project> project = projectRepository.findByExternalId(externalId);
        return project.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project savedProject = projectRepository.save(project);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<Project> updateProject(@PathVariable String externalId, @RequestBody Project updatedProject) {
        Optional<Project> existingProject = projectRepository.findByExternalId(externalId);
        if (existingProject.isPresent()) {
            updatedProject.setExternalId(externalId); 
            Project savedProject = projectRepository.save(updatedProject);
            return new ResponseEntity<>(savedProject, HttpStatus.OK);
        } 
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> deleteProject(@PathVariable String externalId) {
        if (projectRepository.existsByExternalId(externalId)) {
            projectRepository.deleteByExternalId(externalId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
