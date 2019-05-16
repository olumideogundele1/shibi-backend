package com.shibi.app.services.impl;

import com.shibi.app.models.Category;
import com.shibi.app.models.Product;
import com.shibi.app.repos.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by User on 05/07/2018.
 */
@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    /**
     *  get all product
      **/
    public Page<Product> getAllProducts (PageRequest pageRequest){
        return productRepository.findAll(pageRequest);
    }

    /**
     *  get a product
     **/
    public Product getProductById(Long id){
        return productRepository.findOne(id);
    }

    /**
     *  save a product
     **/
    public Product save(Product product) {
        return productRepository.save(product);
    }

    /**
     *  toggle a product
     **/
    public boolean toggle(Long id) throws Exception {
        Product product = productRepository.findOne(id);

        if(product != null) {
            product.setEnabled(!product.isEnabled());
            productRepository.save(product);
            return true;
        }

        return false;
    }

    /**
     *  get category by name
     **/
    public Product getPRoductByName(String productName) {
        return productRepository.findProductByProductName(productName);
    }

    public List<Product> getProductsByStoreId(Long param) {

//        param = Long.valueOf(param);

        return productRepository.getProductsFromStores(param);

    }

    public List<Product> getProductsByCategoryId(Long param) throws Exception {
        return productRepository.getProductsByCategory(param);
    }

    //delete
    public void deleteProduct(Long id) {
        productRepository.delete(id);
    }
}
