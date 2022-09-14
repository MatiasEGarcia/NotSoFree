package com.NotSoFree.dao;

import com.NotSoFree.domain.Favorite;
import com.NotSoFree.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FavoriteDao extends JpaRepository<Favorite, Long> {
    
    Favorite findByProduct(Product product);
}
