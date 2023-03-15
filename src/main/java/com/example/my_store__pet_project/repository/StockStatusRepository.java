package com.example.my_store__pet_project.repository;

import com.example.my_store__pet_project.model.StockStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockStatusRepository extends CrudRepository<StockStatus, Integer> {

}
