package com.NotSoFree.service;

import com.NotSoFree.dao.RolDao;
import com.NotSoFree.domain.Rol;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolDao rolDao;

    @Override
    @Transactional
    public void create(Rol rol) throws Exception {

        try {
            rolDao.save(rol);
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }

    @Override
    @Transactional
    public void createAll(List<Rol> roles) throws Exception {

        try {
            rolDao.saveAll(roles);
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }

    }

}
