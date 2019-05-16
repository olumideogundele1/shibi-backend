package com.shibi.app.usermanagement.controller;

import com.shibi.app.dto.CategoryRequest;
import com.shibi.app.dto.Response;
import com.shibi.app.models.Category;
import com.shibi.app.models.Stores;
import com.shibi.app.services.impl.CategoryService;
import com.shibi.app.services.impl.StoreService;
import com.shibi.app.services.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by User on 05/07/2018.
 */
@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    private static  final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private StoreService storeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    /**
     * create  category api
     */
    @PostMapping(value = "/create-category")
    public ResponseEntity createCategory(@RequestBody CategoryRequest request) throws Exception {

        Category categoryObject = categoryService.getCategoryByName(request.getCategoryName());

        if(categoryObject != null) {
            return new ResponseEntity(new Response("category already exists"), HttpStatus.BAD_REQUEST);
        }

//        //validate storeId
//        Stores stores1 = storeService.findById(request.getStoreId());
//        if(stores1 == null) {
//            logger.info("Store Id is required, store is not mapped to category");
//            return new ResponseEntity(new Response("Store is required"), HttpStatus.BAD_REQUEST);
//        }

        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
//        category.setEnabled(request.isEnabled());


        categoryService.save(category);

        return ResponseEntity.ok(category);
    }


    /**
     * edit category api
     */
    @PutMapping(value = "/update-category")
    public ResponseEntity updateCategory(@Valid @RequestBody CategoryRequest update) throws Exception {
        if(update == null) {
            return new ResponseEntity(new Response("Request not found"), HttpStatus.BAD_REQUEST );
        }
//        Stores stores = storeService.findById(update.getStoreId());
//        if(stores == null) {
//            return new ResponseEntity(new Response("store nt found"), HttpStatus.BAD_REQUEST);
//        }
        Category currentCategory = categoryService.getCategoryById(update.getCategoryId());

        currentCategory.setCategoryName(update.getCategoryName());
        currentCategory.setEnabled(update.isEnabled());


        categoryService.save(currentCategory);

        return ResponseEntity.ok(currentCategory);
    }

    /**
     * get all category api
     */
    @GetMapping(value = "/all-categories", produces = "application/json")
    public ResponseEntity allCategory (@Valid @NotNull(message = "page number is required") @RequestParam int pageNumber,
                                         @Valid @NotNull(message = "page size is required") @RequestParam int pageSize) {

        return ResponseEntity.ok(categoryService.getAll(new PageRequest(pageNumber, pageSize)));
    }

    /**
     * get a category api
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity getCategory(@PathVariable("name = id")Long id) {

        return  ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    /**
     * toggle a category api
     */
    @PutMapping(value = "/{id}/toggle")
    public ResponseEntity toggleCategory(@PathVariable Long id) throws Exception {

    if(id == null) {
        return new ResponseEntity(new Response("Incompatible id"), HttpStatus.BAD_REQUEST);
    }
        boolean toggle = categoryService.toggle(id);

    if(!toggle) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity(new Response("category is now disabled"), HttpStatus.OK);
    }
}
