package com.shibi.app.usermanagement.controller;

import com.shibi.app.dto.ProductRequest;
import com.shibi.app.dto.Response;
import com.shibi.app.enums.QuantityType;
import com.shibi.app.models.Category;
import com.shibi.app.models.Product;
import com.shibi.app.models.Stores;
import com.shibi.app.services.impl.CategoryService;
import com.shibi.app.services.impl.ProductService;
import com.shibi.app.services.impl.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by User on 05/07/2018.
 */
@RestController
@RequestMapping(value = "/product")
public class ProductController {

    private static final Logger logger  = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private StoreService storeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * create product api
     */
    @PostMapping(value = "/create-product")
    public ResponseEntity createProduct(@Valid @RequestBody ProductRequest request) throws Exception {

        Product product = productService.getProductById(request.getId());

        if(product != null) {
            return new ResponseEntity(new Response("Product already exists"), HttpStatus.BAD_REQUEST);
        }

        Stores store = storeService.findById(request.getStoreId());

        Product newProduct = new Product();

        newProduct.setProductName(request.getProductName());
    //    newProduct.setProductQuantity(request.getProductQuantity());
        newProduct.setQuantityType(QuantityType.valueOf(request.getType()));
    //    newProduct.setList_price(request.getListPrice());
        newProduct.setSalePrice(request.getSalePrice());
//        newProduct.setEnabled(request.isEnabled());
        newProduct.setProductImage(request.getProductImage());
        newProduct.setStores(store);

        Category cat = categoryService.getCategoryById(request.getCategoryId());
        newProduct.setCategory(cat);
        productService.save(newProduct);

        return ResponseEntity.ok(newProduct);



    }


    /**
     * edit product api
     */
    @PutMapping(value = "/update-product")
    public ResponseEntity updateProduct(@Valid @RequestBody ProductRequest update) throws Exception {

        Product currentProduct = productService.getProductById(update.getCategoryId());



        if(currentProduct == null) {
            return new ResponseEntity(new Response("request not found"), HttpStatus.BAD_REQUEST);
        }

                Stores stores = storeService.findById(update.getStoreId());
        if(stores == null) {
            return new ResponseEntity(new Response("store nt found"), HttpStatus.BAD_REQUEST);
        }
        Product product = productService.getPRoductByName(update.getProductName());
        if(product.getProductId() != currentProduct.getProductId()) {
            return new ResponseEntity(new Response("category name not found"), HttpStatus.BAD_REQUEST);
        }

        //Set category details into database
        currentProduct.setProductName(update.getProductName());
        currentProduct.setQuantityType(QuantityType.valueOf(update.getType()));
        currentProduct.setSalePrice(update.getSalePrice());
        currentProduct.setList_price(update.getListPrice());

        Category category = categoryService.getCategoryById(currentProduct.getProductId());
        currentProduct.setCategory(category);

        productService.save(currentProduct);

        return ResponseEntity.ok(currentProduct);

    }

    /**
     * get one product api
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity getProduct(@PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * get all products api
     */
    @GetMapping(value = "/all-products")
    public ResponseEntity getAllProducts(@Valid @NotNull(message = "page number is required") @RequestParam int pageNumber,
                                         @Valid @NotNull(message = "page size is required") @RequestParam int pageSize) {
        return ResponseEntity.ok(productService.getAllProducts(new PageRequest(pageNumber,pageSize)));
    }

    /**
     * toggle product api
     */
    @PutMapping(value = "/{id}/toggle")
    public ResponseEntity toggleProducts(@PathVariable Long id) throws Exception {

        if(id == null) {
            return new ResponseEntity(new Response("Incompatible id"), HttpStatus.BAD_REQUEST);

        }
            boolean toggle = productService.toggle(id);
        if(!toggle) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity(new Response("product is now disabled"), HttpStatus.OK);

    }

    @GetMapping(value = "/{storeId}/get-products")
    public ResponseEntity getProductsFromStores(@PathVariable(name = "storeId") Long storeId) throws Exception {
        if(storeId == null) {
            return new ResponseEntity(new Response("id value is null"),HttpStatus.BAD_REQUEST);
        }

       logger.info(String.valueOf(storeId));
       List<Product> productList =  productService.getProductsByStoreId(storeId);

        if(productService.getProductsByStoreId(storeId) == null) {
            return new ResponseEntity(new Response("the store is empty"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(productList);
    }

    @GetMapping(value = "/{categoryId}/get-category")
    public ResponseEntity getProductsFromCategories(@PathVariable(name = "categoryId") Long categoryId) throws Exception {

        if(categoryId == null) {
            return new ResponseEntity(new Response("id value is null"), HttpStatus.BAD_REQUEST);
        }

        logger.info(String.valueOf(categoryId));
        List<Product> productList = productService.getProductsByCategoryId(categoryId);

        if(productList == null) {
            return new ResponseEntity(new Response("No product in this category"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(productList);
    }

//    public ResponseEntity delete(@PathVariable(name = "id"))

}
