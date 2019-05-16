package com.shibi.app.services.impl;

import com.shibi.app.models.Category;
import com.shibi.app.repos.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by User on 05/07/2018.
 */
@Service
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     *  get all category
     **/
    public Page<Category> getAll (PageRequest pageRequest) {
        return categoryRepository.findAll(pageRequest);
    }

    /**
     *  get a category
     **/
    public Category getCategoryById (Long id) {
        return categoryRepository.findOne(id);
    }

    /**
     *  save a category
     **/
    public Category save(Category category) {
       return categoryRepository.save(category);
    }

    /**
     *  toggle a category
     **/
    public boolean toggle(Long id) throws Exception {
        Category category  = categoryRepository.findOne(id);

        if(category != null) {
            category.setEnabled(!category.isEnabled());
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    /**
     *  get category by name
     **/
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findCategoryByCategoryName(categoryName);
    }
}
