package com.NotSoFree.service;

import com.NotSoFree.domain.Category;
import com.NotSoFree.exception.CategoryNotFoundById;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


public interface CategoryService {
    public List<Category> listCategories() throws Exception;
    
    public List<Category> listByState(byte state) throws Exception;
    
    public void save(Category category,MultipartFile image) throws Exception;
    
    public void delete(Long id) throws Exception;
    
    public Category findCategory(Long id) throws CategoryNotFoundById;
    
     public Page<Category> findPaginated(int pageNo, int pageSize, String sortField, String sortDir) throws Exception;
    
}
