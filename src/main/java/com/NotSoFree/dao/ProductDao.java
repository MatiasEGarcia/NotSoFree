package com.NotSoFree.dao;

import com.NotSoFree.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Long> {
    
}
