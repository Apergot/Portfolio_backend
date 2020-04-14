package com.apergot.springbootrestapi.controllers;

import com.apergot.springbootrestapi.models.entity.Cert;
import com.apergot.springbootrestapi.models.services.AmazonService;
import com.apergot.springbootrestapi.models.services.ICertService;
import com.apergot.springbootrestapi.models.services.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CertRestController {

    @Autowired
    private AmazonService amazonService;

    @Autowired
    private ICertService certService;

    @GetMapping("/certs")
    public List<Cert> index() {
        return certService.findAll();
    }

    @GetMapping("/certs/page/{page}")
    public Page<Cert> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 4);
        return certService.findAll(pageable);
    }

    @GetMapping("/certs/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Cert cert = null;
        Map<String, Object> map = new HashMap<>();

        try {
            cert = certService.findById(id);
        } catch (DataAccessException e) {
            map.put("message", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

        if (cert == null) {
            map.put("message", "Requested cert does not exist");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cert, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/certs")
    public ResponseEntity<?> create(@Valid @RequestPart("cert") Cert cert, @Valid @RequestPart("file") MultipartFile file, BindingResult result){

        Cert createdCert = null;
        String filename = "";
        Map<String, Object> map = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError err: result.getFieldErrors()) {
                errors.add(err.getDefaultMessage());
            }
            map.put("error", errors);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        if (!file.isEmpty()) {
            try {
                filename = this.amazonService.uploadFile(file);
            } catch (IOException e) {
                map.put("message", "error while uploading cert image");
                map.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        try {
            cert.setImage(filename);
            createdCert = certService.save(cert);
        } catch (DataAccessException e){
            map.put("message", "Error inserting cert into database");
            map.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        map.put("message", "Cert has been created successfully! Hell yeah!");
        map.put("client", createdCert);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/certs/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> map = new HashMap<>();
        try {
            certService.delete(id);
        } catch (DataAccessException e) {
            map.put("message", "error deleting certificate");
            map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        map.put("message", "cert deleted successfully!");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/certs/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cert cert, @PathVariable Long id, BindingResult result) {
        Cert currentCert = certService.findById(id);
        Map<String, Object> map = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            map.put("error", errors);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        if (currentCert == null) {
            map.put("message", "Could not edit cert, this does not exist");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

        Cert updatedCert = null;
        try {
            currentCert.setImage(currentCert.getImage());
            currentCert.setCert_id(cert.getCert_id());
            currentCert.setDescription(cert.getDescription());
            currentCert.setLink(cert.getLink());
            currentCert.setName(cert.getName());
            currentCert.setPlatform(cert.getPlatform());
            currentCert.setTeacher(cert.getTeacher());
            updatedCert = certService.save(currentCert);
        } catch (DataAccessException e) {
            map.put("message", "error updating cert data");
            map.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        map.put("message", "client updated successfully!");
        map.put("client", updatedCert);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
}
