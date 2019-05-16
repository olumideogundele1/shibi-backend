package com.shibi.app.repos;

import com.shibi.app.models.Category;
import com.shibi.app.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by User on 05/07/2018.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.productName = :productName")
    Product findProductByProductName(@Param("productName") String productName);

    @Query("select p from Product p where p.productId = :productId")
    Product findOne(@Param("productId") Long productId);

//    @Query(value = "SELECT p.id, p.list_price,p.product_image,p.product_name,p.product_image,p.category_id from products as p where p.store_id = :param",nativeQuery = true)
   @Query(value = "select p from Product p where p.stores.storeId = :param")
    List<Product> getProductsFromStores(@Param("param") Long param);

   @Query(value = "select p from Product p where p.category.categoryId = :param")
   List<Product> getProductsByCategory(@Param("param") Long param);

}
