package com.NotSoFree.service;

import java.util.List;
import com.NotSoFree.domain.Product;
import com.NotSoFree.exception.ProductNotFoundById;
import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


public interface ProductService {
    
    public List<Product> listProducts();
    
    public void saveProduct(Product product,MultipartFile image) throws IOException;
    
    public void removeProduct(Long idProduct) throws ProductNotFoundById;
    
    public Product findProduct(Long idProduct) throws ProductNotFoundById;
    
    public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);
    
}
