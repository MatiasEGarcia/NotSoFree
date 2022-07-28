package com.NotSoFree.service;

import java.util.List;
import com.NotSoFree.domain.Product;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;


public interface ProductService {
    
    public List<Product> listProducts();
    
    public void saveProduct(Product product,MultipartFile image) throws IOException;
    
    public void removeProduct(Product product);
    
    public Product findProduct(Long idProduct);
    
}