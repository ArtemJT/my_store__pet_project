package com.example.my_store__pet_project.repository;

import com.example.my_store__pet_project.model.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer> {

    boolean existsProductByModel(String model);

    @Modifying
    @Query("DELETE FROM Product p WHERE p.productId = :id")
    void removeProductById(@Param("id") Integer productId);
}
