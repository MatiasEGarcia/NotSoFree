package com.NotSoFree.dao;

import com.NotSoFree.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FavoriteDao extends JpaRepository<Favorite, Long> {
    
}
