package com.apergot.springbootrestapi.controllers;

import com.apergot.springbootrestapi.models.entity.Post;
import com.apergot.springbootrestapi.models.services.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BlogRestController {

    @Autowired
    private IPostService postService;

    @GetMapping("/posts")
    public List<Post> index(){
        return postService.findAll();
    }

    @GetMapping("/posts/page/{page}")
    public Page<Post> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 4);
        return postService.findAll(pageable);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Post post = null;
        Map<String, Object> map = new HashMap<>();

        try {
            post = postService.findById(id);
        } catch (DataAccessException e) {
            map.put("message", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

        if (post == null) {
            map.put("message", "Requested post does not exist");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/posts")
    public ResponseEntity<?> create(@Valid @RequestBody Post post, BindingResult result){
        Post createdPost = null;
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
            createdPost = postService.save(post);
        } catch (DataAccessException e){
            map.put("message", "Error inserting into database");
            map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        map.put("message", "Post created successfully");
        map.put("post", createdPost);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> map = new HashMap<>();

        try {
            postService.delete(id);
        } catch (DataAccessException e){
            map.put("message", "error deleting post");
            map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        map.put("message", "Post deleted successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/posts/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Post post, @PathVariable Long id, BindingResult result) {
        Post currentPost = postService.findById(id);
        Map<String, Object> map = new HashMap<>();

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            map.put("error", errors);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        if (currentPost == null) {
            map.put("message", "Could not update post, this does not exist");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

        Post updatedPost = null;
        try {
            currentPost.setBody(post.getBody());
            currentPost.setTitle(post.getTitle());
            updatedPost = postService.save(currentPost);
        } catch (DataAccessException e){
            map.put("message", "error updating post data");
            map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        map.put("message", "post updated successfully!");
        map.put("project", updatedPost);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

}
