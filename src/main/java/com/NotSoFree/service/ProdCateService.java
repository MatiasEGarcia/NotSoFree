package com.NotSoFree.service;

import com.NotSoFree.domain.ProdCate;
import java.util.List;

public interface ProdCateService {
    
    public void findById(Long id)throws Exception;
    
    public void save(ProdCate prodCate)throws Exception;
    
     public void saveAll(List<ProdCate> prodCate)throws Exception;
     
     public void delete(ProdCate prodCate)throws Exception;
     
     
}
