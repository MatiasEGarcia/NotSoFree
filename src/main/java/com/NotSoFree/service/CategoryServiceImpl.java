package com.NotSoFree.service;

import com.NotSoFree.dao.CategoryDao;
import com.NotSoFree.domain.Category;
import com.NotSoFree.exception.CategoryNotFoundById;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    @Transactional(readOnly = true)
    public List<Category> listCategories() throws Exception {
        try {
            return categoryDao.findAll();
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> listByState(byte state) throws Exception {
        try {
            return categoryDao.findByState(state);
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }

    @Override
    @Transactional
    public void save(Category category, MultipartFile image) throws Exception {
        try {
            if (!image.isEmpty()) {
                category.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }
            categoryDao.save(category);
        } catch (IOException e) {
            throw new Exception("There was an error with the image");
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }

    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {

        try {
            categoryDao.delete(this.findCategory(id));
        } catch (CategoryNotFoundById e) {
            throw new Exception("There is no category with that id");
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Category findCategory(Long id) throws CategoryNotFoundById {
        return categoryDao.findById(id).orElseThrow(() -> new CategoryNotFoundById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Category> findPaginated(int pageNo, int pageSize, String sortField, String sortDir)throws Exception {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        try {
            return this.categoryDao.findAll(pageable);
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }

}
