package com.apergot.springbootrestapi.models.services;

import com.apergot.springbootrestapi.models.dao.IProjectDao;
import com.apergot.springbootrestapi.models.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectServiceImp implements IProjectService{

    @Autowired
    private IProjectDao projectDao;

    @Override
    @Transactional(readOnly = true)
    public List<Project> findAll() {
        return (List<Project>)projectDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Project> findAll(Pageable pageable) {
        return projectDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Project findById(Long id) {
        return projectDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Project save(Project project) {
        return projectDao.save(project);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        projectDao.deleteById(id);
    }
}
