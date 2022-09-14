package com.NotSoFree.service;

import com.NotSoFree.domain.Category;
import com.NotSoFree.domain.Favorite;
import com.NotSoFree.domain.Product;
import com.NotSoFree.domain.UserD;
import com.NotSoFree.dto.PageDto;

public interface FavoriteService {
    
    void saveFavorite(Favorite favorite) throws Exception;
    
    Favorite findFavoriteById(Long idFavorite) throws Exception;
    
    Favorite findFavoriteByProduct(Product Product) throws Exception;
   
    void deleteFavoriteById(Long idFavorite) throws Exception;
    
    void deleteFavoriteByProduct(Product product) throws Exception;
    
    public PageDto favoriteList(UserD user,int pageNo, int pageSize, String sortField, String sortDir) throws Exception;
    
    public PageDto favoriteListByCategory(UserD user,Category category,int pageNo, int pageSize, String sortField, String sortDir) throws Exception;
    
}
