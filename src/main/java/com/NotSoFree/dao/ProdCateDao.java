package com.NotSoFree.dao;

import com.NotSoFree.domain.Category;
import com.NotSoFree.domain.ProdCate;
import com.NotSoFree.domain.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdCateDao  extends JpaRepository<ProdCate, Long> {
    
    Product findByProduct(Product product);
    
    List<ProdCate> findByProductAndCategoryIn(Product product,List<Category> listCategories);
}
