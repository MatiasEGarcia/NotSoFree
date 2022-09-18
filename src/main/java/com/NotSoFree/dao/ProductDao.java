package com.NotSoFree.dao;

import com.NotSoFree.domain.Category;
import com.NotSoFree.domain.Product;
import com.NotSoFree.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductDao extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p FROM Product p WHERE p.idProduct = ANY (SELECT r.product FROM ProdCate r WHERE r.category = ?1)")
    Page<Product> findProductsByCategory(Category category, Pageable pageable);

    @Modifying
    @Query("UPDATE Product p SET p.stock = :#{#product.stock} WHERE p.idProduct = :#{#product.idProduct} ")
    void updateProductStock(@Param("product")ProductDto product);
    
    Page<Product> findByNameContaining(String name,Pageable pageable);
    
    @Query(value = "SELECT p FROM Product p WHERE p.name LIKE %:name% AND p.idProduct = ANY (SELECT r.product FROM ProdCate r WHERE r.category = :category)" )
    Page<Product> findByNameContainingByCategory(@Param("name")String name,@Param("category")Category category,Pageable pageable);
}
