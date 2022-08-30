package com.NotSoFree.service;

import com.NotSoFree.dao.ProdCateDao;
import com.NotSoFree.domain.ProdCate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdCateServiceImpl implements ProdCateService {

    @Autowired
    private ProdCateDao prodCateDao;

    @Override
    @Transactional
    public void findById(Long id) throws Exception {
        try {
            prodCateDao.findById(id);
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }

    @Override
    @Transactional
    public void save(ProdCate prodCate) throws Exception {
        try {
            prodCateDao.save(prodCate);
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }

    @Override
    @Transactional
    public void saveAll(List<ProdCate> prodCate) throws Exception {
        try {
            prodCateDao.saveAll(prodCate);
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }

    @Override
    @Transactional
    public void delete(ProdCate prodCate) throws Exception {
        try {
            prodCateDao.delete(prodCate);
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }

}
