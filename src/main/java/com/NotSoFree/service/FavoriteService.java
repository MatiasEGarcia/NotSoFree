package com.NotSoFree.service;

import com.NotSoFree.domain.Favorite;
import com.NotSoFree.domain.UserD;
import com.NotSoFree.dto.PageDto;

public interface FavoriteService {
    
    void saveFavorite(Favorite favorite) throws Exception;
    
    Favorite findFavorite(Long idFavorite) throws Exception;
    
    void deleteFavorteById(Long idFavorite) throws Exception;
    
    public PageDto favoriteListByUser(UserD user,int pageNo, int pageSize, String sortField, String sortDir) throws Exception;
    
}
