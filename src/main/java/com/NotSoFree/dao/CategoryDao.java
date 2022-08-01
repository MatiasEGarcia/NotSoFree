package com.NotSoFree.dao;
        
import com.NotSoFree.domain.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Long>{
    
    List<Category> findByState(byte state);
}
