package com.NotSoFree.dao;

import com.NotSoFree.domain.ProdCate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdCateDao  extends JpaRepository<ProdCate, Long> {
    
}
