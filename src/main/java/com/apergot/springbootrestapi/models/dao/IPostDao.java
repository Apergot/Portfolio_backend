package com.apergot.springbootrestapi.models.dao;

import com.apergot.springbootrestapi.models.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostDao extends JpaRepository<Post, Long> {
}

