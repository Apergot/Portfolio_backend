package com.apergot.springbootrestapi.models.dao;

import com.apergot.springbootrestapi.models.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProjectDao extends JpaRepository<Project, Long> {
}
