package com.NotSoFree.service;

import com.NotSoFree.dao.RolDao;
import com.NotSoFree.domain.Rol;
import com.NotSoFree.domain.UserD;
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
    public void save(Rol rol) throws Exception {

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
    public void saveAll(List<Rol> roles) throws Exception {

        try {
            rolDao.saveAll(roles);
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }

    }

    @Override
    @Transactional
    public void deleteByUserAndNameIn(UserD user, List<String> roles) throws Exception {
        try {
            rolDao.deleteByUserAndNameIn(user, roles);
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }

}
