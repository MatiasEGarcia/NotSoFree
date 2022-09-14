package com.NotSoFree.dao;

import com.NotSoFree.domain.Category;
import com.NotSoFree.domain.Favorite;
import com.NotSoFree.domain.Product;
import com.NotSoFree.domain.UserD;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface FavoriteDao extends JpaRepository<Favorite, Long> {
    
    Favorite findByProduct(Product product);
    
    @Query(value = "SELECT p FROM Product p WHERE p = ANY (SELECT f.product FROM Favorite f WHERE f.user =  :user) "
            + "AND p = ANY (SELECT p FROM Product p WHERE p.idProduct = ANY (SELECT r.product FROM ProdCate r WHERE r.category = :cate))")
   Page<Product> findProdInFavByCate(@Param("user")UserD user,@Param("cate")Category category, Pageable pageable);
    
}
