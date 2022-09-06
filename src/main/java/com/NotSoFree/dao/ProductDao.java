package com.NotSoFree.dao;


import com.NotSoFree.domain.Category;
import com.NotSoFree.domain.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductDao extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p FROM Product p WHERE p.idProduct = ANY (SELECT r.product FROM ProdCate r WHERE r.category = ?1)")
    Page <Product> findProductsByCategory(Category category,Pageable pageable);
    
    @Query(value = "SELECT p FROM Product p WHERE p.idProduct IN(:products)")
     Page <Product> findProductsByIdPaginated(@Param("products")List<Long> products, Pageable pageable);

}
