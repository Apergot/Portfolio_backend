package com.apergot.springbootrestapi.models.services;

import com.apergot.springbootrestapi.models.entity.Cert;
import com.apergot.springbootrestapi.models.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProjectService {
    public List<Project> findAll();
    public Page<Project> findAll(Pageable pageable);
    public Project findById(Long id);
    public Project save(Project project);
    public void delete(Long id);
}
