package com.NotSoFree.service;

import com.NotSoFree.domain.Category;
import com.NotSoFree.domain.ProdCate;
import com.NotSoFree.domain.Product;
import java.util.List;

public interface ProdCateService {
    
    public void findById(Long id)throws Exception;
    
    public void findByProduct(Product product) throws Exception;
    
    public void save(ProdCate prodCate)throws Exception;
    
     public void saveAll(List<ProdCate> prodCate)throws Exception;
     
     public void delete(ProdCate prodCate)throws Exception;
     
     public void deleteAll(List<ProdCate> listProdCate)throws Exception;
     
     public List<ProdCate> findByProdWhereCateIn(Product product,List<Category> listCategories)throws Exception;
     
}
