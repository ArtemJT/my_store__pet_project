package com.example.my_store__pet_project.repository;

import com.example.my_store__pet_project.model.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Integer>, CrudRepository<Order, Integer> {

    Optional<List<Order>> findAllByUserName(String userName, Sort sort);

    Optional<Order> findOrderByUserNameAndOrderId(String userName, Integer orderId);
}
