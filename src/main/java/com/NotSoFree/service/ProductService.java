package com.NotSoFree.service;

import java.util.List;
import com.NotSoFree.domain.Product;
import com.NotSoFree.exception.ProductNotFoundById;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


public interface ProductService {
    
    public List<Product> listProducts() throws Exception;
    
    public void saveProduct(Product product,MultipartFile image) throws Exception;
    
    public void removeProduct(Long idProduct) throws Exception;
    
    public Product findProduct(Long idProduct) throws ProductNotFoundById;
    
    public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDir) throws Exception;
    
}
