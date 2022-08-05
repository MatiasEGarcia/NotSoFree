package com.NotSoFree.service;

import com.NotSoFree.domain.Product;
import com.NotSoFree.dao.ProductDao;
import com.NotSoFree.exception.ProductNotFoundById;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Product findProduct(Long idProduct) throws ProductNotFoundById{
       return productDao.findById(idProduct).orElseThrow(() -> new ProductNotFoundById(idProduct) );
    }

    @Override
    @Transactional(readOnly=true)
    public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        //Pageable provides the info for the pagination
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort); // the pagination starts at 1 that's why I subtract 1

        //if you only wanted to use sort, you would have to pass instead of the pageable, just the sort
        return this.productDao.findAll(pageable);
    }
    
}
