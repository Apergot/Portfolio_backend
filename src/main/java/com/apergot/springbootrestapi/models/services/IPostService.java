package com.apergot.springbootrestapi.models.services;

import com.apergot.springbootrestapi.models.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPostService {
    public List<Post> findAll();
    public Page<Post> findAll(Pageable pageable);
    public Post findById(Long id);
    public Post save(Post post);
    public void delete(Long id);
}
