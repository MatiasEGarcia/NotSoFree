package com.NotSoFree.service;

import com.NotSoFree.domain.Category;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


public interface CategoryService {
    public List<Category> listCategories();
    
    public List<Category> listByState(byte state);
    
    public void save(Category category,MultipartFile image) throws IOException;
    
    public void delete(Category category);
    
    public Category findCategory(Category category);
    
     public Page<Category> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);
    
}
