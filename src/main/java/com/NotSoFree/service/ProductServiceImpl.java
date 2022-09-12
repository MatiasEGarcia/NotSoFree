package com.NotSoFree.service;

import com.NotSoFree.domain.Product;
import com.NotSoFree.dao.ProductDao;
import com.NotSoFree.domain.Category;
import com.NotSoFree.domain.ProdCate;
import com.NotSoFree.dto.ProductDto;
import com.NotSoFree.exception.ProductNotFoundById;
import java.io.IOException;
import java.util.ArrayList;
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

    @Autowired
    private CategoryService categoryService;

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
    public void saveProduct(ProductDto productDto, MultipartFile image) throws Exception {
        Product product = new Product(productDto);
        List<Category> activeCategories = categoryService.listByState(new Byte("1"));
        List<ProdCate> listAdd = new ArrayList<>();
        List<Category> listDelete = new ArrayList<>();
        List<Long> newCategories = productDto.getNewCategories().stream().map(cate -> Long.parseLong(cate)).collect(Collectors.toList());

        if (!productDto.getOldCategories().isEmpty()) {
            for (int i = 0; i < activeCategories.size(); i++) {
                if (!productDto.getOldCategories().contains(activeCategories.get(i)) && newCategories.contains(activeCategories.get(i).getIdCategory())) { //If the category is in newCategories and they are not in oldCategories = I have to add it to the database
                    listAdd.add(new ProdCate(product, activeCategories.get(i)));
                } else if (productDto.getOldCategories().contains(activeCategories.get(i)) && !newCategories.contains(activeCategories.get(i).getIdCategory())) { //If the category is not in newCategories and if it is in oldCategories= I have to delete it from the bdd
                    listDelete.add(activeCategories.get(i));
                }
            }
        } else {
            for (Long category : newCategories) {
                listAdd.add(new ProdCate(product, new Category(category)));
            }
        }

        try {
            if (!image.isEmpty()) {
                product.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }
            productDao.save(product);
            if (!listAdd.isEmpty()) {
                prodCateService.saveAll(listAdd);
            }
            if (!listDelete.isEmpty()) {
                prodCateService.deleteAll(prodCateService.findByProdWhereCateIn(product, listDelete));
            }
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
    public void updateProductsStock(List<ProductDto> products) throws Exception {
        
        
        
         try {
            for(int i=0;i<products.size();i++){
                products.get(i).setStock(products.get(i).getStock() - products.get(i).getQuantity());
                productDao.updateProductStock(products.get(i));
            }
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
        try {
            productDao.delete(productDao.findById(idProduct).orElseThrow(() -> new ProductNotFoundById(idProduct)));
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto findProduct(Long idProduct) throws Exception {
        Product product;
        List<Category> oldCategories;

        try {
            product = productDao.findById(idProduct).orElseThrow(() -> new ProductNotFoundById());
            oldCategories = product.getProdCate().stream()
                    .map(prodCate -> prodCate.getCategory())
                    .collect(Collectors.toList());

        } catch (ProductNotFoundById e) {
            throw new Exception("There are not a product with that id");
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }

        return new ProductDto(product, oldCategories);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findAllProductsById(List<Long> idProducts) throws Exception {
        List<Product> products;
        List<ProductDto> productsDto = new ArrayList<>();

        try {
            products = productDao.findAllById(idProducts);
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }

        for (Product product : products) {
            productsDto.add(new ProductDto(product));
        }

        return productsDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDir) throws Exception {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        try {
            return this.productDao.findAll(pageable);
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findPaginatedByCategory(int pageNo, int pageSize, String sortField, String sortDir, Category category) throws Exception {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        try {
            return productDao.findProductsByCategory(category, pageable);
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }
}
