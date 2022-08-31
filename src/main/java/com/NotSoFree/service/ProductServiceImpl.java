package com.NotSoFree.service;

import com.NotSoFree.domain.Product;
import com.NotSoFree.dao.ProductDao;
import com.NotSoFree.domain.Category;
import com.NotSoFree.domain.ProdCate;
import com.NotSoFree.exception.ProductNotFoundById;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private ProdCateService prodCateService;

    @Override
    @Transactional(readOnly = true)
    public List<Product> listProducts() throws Exception {
        try {
            return productDao.findAll();
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }

    @Override
    @Transactional
    public void saveProduct(Product product, MultipartFile image) throws Exception {
        
        List<ProdCate> listProdCate = product.getNewCategories().stream()
                                                        .map(cate -> new ProdCate(product, new Category(Long.parseLong(cate) ) ) )
                                                        .collect(Collectors.toList());
        
        try {
            if (!image.isEmpty()) {
                product.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }
            productDao.save(product);
            prodCateService.saveAll(listProdCate);
        } catch (IOException e) {
            throw new Exception("There was an error with the image");
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }

    @Override
    @Transactional
    public void removeProduct(Long idProduct) throws Exception {
        Product product = this.findProduct(idProduct);
        try {
            productDao.delete(product);
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Product findProduct(Long idProduct) throws ProductNotFoundById {
        return productDao.findById(idProduct).orElseThrow(() -> new ProductNotFoundById(idProduct));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDir) throws Exception {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        //Pageable provides the info for the pagination
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort); // the pagination starts at 1 that's why I subtract 1

        try {
            //if you only wanted to use sort, you would have to pass instead of the pageable, just the sort
            return this.productDao.findAll(pageable);
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }

}
