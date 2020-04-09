package com.apergot.springbootrestapi.models.services;

import com.apergot.springbootrestapi.models.dao.ICertDao;
import com.apergot.springbootrestapi.models.entity.Cert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CertServiceImp implements ICertService {

    @Autowired
    private ICertDao certDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cert> findAll() {
        return (List<Cert>)certDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cert> findAll(Pageable pageable) {
        return certDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Cert findById(Long id) {
        return certDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Cert save(Cert cert) {
        return certDao.save(cert);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        certDao.deleteById(id);
    }
}
