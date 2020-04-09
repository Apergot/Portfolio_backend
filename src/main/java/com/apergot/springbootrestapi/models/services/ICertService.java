package com.apergot.springbootrestapi.models.services;

import com.apergot.springbootrestapi.models.entity.Cert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICertService {
    public List<Cert> findAll();
    public Page<Cert> findAll(Pageable pageable);
    public Cert findById(Long id);
    public Cert save(Cert cert);
    public void delete(Long id);
}
