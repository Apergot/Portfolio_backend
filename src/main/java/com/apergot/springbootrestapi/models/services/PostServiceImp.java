package com.apergot.springbootrestapi.models.services;

import com.apergot.springbootrestapi.models.dao.IPostDao;
import com.apergot.springbootrestapi.models.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceImp implements IPostService{

    @Autowired
    private IPostDao postDao;

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> findAll(Pageable pageable) {
        return postDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Post findById(Long id) {
        return postDao.findById(id).orElse(null);
    }

    @Override
    public Post save(Post post) {
        return postDao.save(post);
    }

    @Override
    public void delete(Long id) {
        postDao.deleteById(id);
    }
}
