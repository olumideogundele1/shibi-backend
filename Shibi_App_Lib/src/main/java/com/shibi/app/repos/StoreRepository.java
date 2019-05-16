package com.shibi.app.repos;

import com.shibi.app.models.Stores;
import com.shibi.app.models.User;
import org.apache.catalina.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by User on 04/07/2018.
 */
@Repository
public interface StoreRepository extends JpaRepository<Stores, Long> {

    Optional<Stores> findByStoreName(String storeName);

    @Query("select s from Stores s where s.storeId = :storeId")
    Stores findOne(@Param("storeId") Long storeId);

    @Query("select s from Stores s where s.storeName = :storename")
    User findStoresByStoreName(@Param("storename") String storename);

    Page<Stores> findAll(Pageable pageable);

    @Query("select s from Stores s where s.user.id = :id")
    Stores findStoresByUserId(@Param("id") Long id);



}
