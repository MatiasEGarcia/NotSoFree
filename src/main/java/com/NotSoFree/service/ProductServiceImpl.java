package com.NotSoFree.service;

import com.NotSoFree.domain.Product;
import com.NotSoFree.dao.ProductDao;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService{
    
    @Autowired
    private ProductDao productDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Product> listProducts() {
        return productDao.findAll();
    }

    @Override
    @Transactional
    public void saveProduct(Product product, MultipartFile image) throws IOException { //for the image
        
        if (!image.isEmpty()) {
            product.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
        }
                
        productDao.save(product);
        log.info("Product saved successfully");
    }

    @Override
    @Transactional
    public void removeProduct(Product product) {
        productDao.delete(product);
    }

    @Override
    @Transactional(readOnly= true)
    public Product findProduct(Long idProduct) {
       return productDao.findById(idProduct).orElse(null);
    }
    
}
