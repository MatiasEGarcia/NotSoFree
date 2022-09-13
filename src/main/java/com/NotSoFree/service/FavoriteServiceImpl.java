package com.NotSoFree.service;

import com.NotSoFree.dao.FavoriteDao;
import com.NotSoFree.dao.UserDao;
import com.NotSoFree.domain.Favorite;
import com.NotSoFree.domain.Product;
import com.NotSoFree.domain.UserD;
import com.NotSoFree.dto.PageDto;
import com.NotSoFree.dto.ProductDto;
import com.NotSoFree.exception.UserDNotFoundByUsername;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoriteServiceImpl implements FavoriteService{
    
    @Autowired
    private UserDao userDao;
    
     @Autowired
    private FavoriteDao favoriteDao;
    

    @Override
    public void saveFavorite(Favorite favorite) throws Exception { 
         try {
             favoriteDao.save(favorite);
        } catch (DataAccessException e) {
            throw new UserDNotFoundByUsername("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserDNotFoundByUsername("Unknown Error");
        }
    }

    @Override
    public Favorite findFavorite(Long idFavorite) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteFavorteById(Long idFavorite) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

@Override
    @Transactional(readOnly = true)
    public PageDto favoriteListByUser(UserD user, int pageNo, int pageSize, String sortField, String sortDir) throws Exception {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort); // the pagination starts at 1 that's why I subtract 1
        Page<Product> productsPage;
        List<Product> productList = new ArrayList<>();
        List<ProductDto> productDtoList = new ArrayList<>();

        try {
            productsPage = userDao.findFavoriteProducts(user, pageable);
        } catch (DataAccessException e) {
            throw new UserDNotFoundByUsername("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserDNotFoundByUsername("Unknown Error");
        }

        if (!productsPage.isEmpty()) {
            productList = productsPage.getContent();
            for (Product product : productList) {
                productDtoList.add(new ProductDto(product));
            }

            return new PageDto<>(productDtoList, productsPage.getTotalPages(), productsPage.getTotalElements());
        }

        return null;
    }
    
}
