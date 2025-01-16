package com.cds.projectpulse.members;

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
@RequestMapping("/projects/{projectExternalId}/members")
public class MembersController {

    @Autowired
    private MemberRepository membersRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public ResponseEntity<List<Members>> getAllMembersByProject(@PathVariable String projectExternalId) {
        Optional<Project> project = projectRepository.findByExternalId(projectExternalId);
        if (project.isPresent()) {
            List<Members> members = membersRepository.findByProject(project.get());
            return new ResponseEntity<>(members, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{memberExternalId}")
    public ResponseEntity<Members> getMemberByExternalId(@PathVariable String projectExternalId, @PathVariable String memberExternalId) {
        Optional<Project> project = projectRepository.findByExternalId(projectExternalId);
        if (project.isPresent()) {
            List<Members> members = membersRepository.findByProject(project.get());
            Optional<Members> member = members.stream().filter(m -> m.getExternalId().equals(memberExternalId)).findFirst();
            return member.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Members> createMember(@PathVariable String projectExternalId, @RequestBody Members member) {
        Optional<Project> project = projectRepository.findByExternalId(projectExternalId);
        if (project.isPresent()) {
            member.setProject(project.get());
            Members savedMember = membersRepository.save(member);
            return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{memberExternalId}")
    public ResponseEntity<Members> updateMember(@PathVariable String projectExternalId, @PathVariable String memberExternalId, @RequestBody Members updatedMember) {
        Optional<Project> project = projectRepository.findByExternalId(projectExternalId);
        if (project.isPresent()) {
            List<Members> members = membersRepository.findByProject(project.get());
            Optional<Members> existingMember = members.stream().filter(m -> m.getExternalId().equals(memberExternalId)).findFirst();
            if(existingMember.isPresent()){
                updatedMember.setId(existingMember.get().getId());
                updatedMember.setProject(project.get());
                Members savedMember = membersRepository.save(updatedMember);
                return new ResponseEntity<>(savedMember, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{memberExternalId}")
    public ResponseEntity<Void> deleteMember(@PathVariable String projectExternalId, @PathVariable String memberExternalId) {
        Optional<Project> project = projectRepository.findByExternalId(projectExternalId);
        if (project.isPresent()) {
            List<Members> members = membersRepository.findByProject(project.get());
            Optional<Members> existingMember = members.stream().filter(m -> m.getExternalId().equals(memberExternalId)).findFirst();
            if(existingMember.isPresent()){
                membersRepository.delete(existingMember.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
