package com.shibi.app.repos;

import com.shibi.app.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by User on 05/07/2018.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.categoryName= :categoryName")
    Category findCategoryByCategoryName(@Param("categoryName") String categoryName);

}
