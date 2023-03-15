package com.example.my_store__pet_project.repository;

import com.example.my_store__pet_project.model.ProductDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailsRepository extends CrudRepository<ProductDetails, Integer> {

}
