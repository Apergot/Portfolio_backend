package com.apergot.springbootrestapi.models.dao;

import com.apergot.springbootrestapi.models.entity.Cert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICertDao extends JpaRepository<Cert, Long> {
}
