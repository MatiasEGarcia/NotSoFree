
package com.NotSoFree.service;

import com.NotSoFree.dao.CategoryDao;
import com.NotSoFree.domain.Category;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryDao categoryDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Category> listCategories() {
        return categoryDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> listByState(byte state) {
        return categoryDao.findByState(state);
    }

    @Override
    @Transactional
    public void save(Category category, MultipartFile image) throws IOException { //for the image
        if (!image.isEmpty()) {
            category.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
        }
                
        categoryDao.save(category);
        log.info("Product saved successfully");
    }

    @Override
    @Transactional
    public void delete(Category category) {
        categoryDao.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Category findCategory(Category category) {
        return categoryDao.findById(category.getIdCategory()).orElse(null);
    }
    
}
