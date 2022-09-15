package com.NotSoFree.service;

import com.NotSoFree.domain.Category;
import java.util.List;
import com.NotSoFree.domain.Product;
import com.NotSoFree.dto.PageDto;
import com.NotSoFree.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


public interface ProductService {
    
    public List<Product> listProducts() throws Exception;
    
    public void saveProduct(ProductDto productDto,MultipartFile image) throws Exception;
    
     public void updateProductsStock(List<ProductDto> products) throws Exception;
    
    public void removeProduct(Long idProduct) throws Exception;
    
    public ProductDto findProduct(Long idProduct) throws Exception;
    
    public List<ProductDto> findAllProductsById(List<Long> idProduct) throws Exception;
    
    public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDir) throws Exception;
    
    public Page<Product> findPaginatedByCategory(int pageNo, int pageSize, String sortField, String sortDir,Category category) throws Exception;
    
     public PageDto findPaginatedLike(String name ,int pageNo, int pageSize, String sortField, String sortDir) throws Exception;
}
