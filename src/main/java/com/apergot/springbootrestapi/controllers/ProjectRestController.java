package com.apergot.springbootrestapi.controllers;

import com.apergot.springbootrestapi.models.entity.Project;
import com.apergot.springbootrestapi.models.services.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProjectRestController {

    @Autowired
    private IProjectService projectService;

    @GetMapping("/projects")
    public List<Project> index() {
        return projectService.findAll();
    }

    @GetMapping("/projects/page/{page}")
    public Page<Project> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        return projectService.findAll(pageable);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Project project = null;
        Map<String, Object> map = new HashMap<>();

        try {
            project = projectService.findById(id);
        }catch (DataAccessException e) {
            map.put("message", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/projects")
    public ResponseEntity<?> create(@Valid @RequestBody Project project, BindingResult result) {
        Project createdProject = null;
        Map<String, Object> map = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError err: result.getFieldErrors()) {
                errors.add(err.getDefaultMessage());
            }
            map.put("error", errors);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        try {
            createdProject = projectService.save(project);
        } catch (DataAccessException e) {
            map.put("message", "Error inserting into database");
            map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        map.put("message", "Project created successfully!");
        map.put("project", createdProject);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> map = new HashMap<>();
        try{
            projectService.delete(id);
        }catch (DataAccessException e) {
            map.put("message", "error deleting project");
            map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        map.put("message", "Project deleted successfully!");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/projects/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Project project, @PathVariable Long id, BindingResult result){
        Project currentProject = projectService.findById(id);
        Map<String, Object> map = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            map.put("error", errors);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        if (currentProject == null) {
            map.put("message", "Could not edit project, this does not exist");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

        Project updatedProject = null;
        try {
            currentProject.setDescription(project.getDescription());
            currentProject.setLink(project.getLink());
            currentProject.setName(project.getName());
            currentProject.setPlatform(project.getPlatform());
            currentProject.setType(project.getType());
            updatedProject = projectService.save(currentProject);
        } catch (DataAccessException e) {
            map.put("message", "error updating project data");
            map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        map.put("message", "project updated successfully!");
        map.put("project", updatedProject);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

}
