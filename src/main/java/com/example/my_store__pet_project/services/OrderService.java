package com.example.my_store__pet_project.services;

import com.example.my_store__pet_project.dto.Cart;
import com.example.my_store__pet_project.dto.OrderDto;
import com.example.my_store__pet_project.dto.ProductDto;
import com.example.my_store__pet_project.dto.UsersDto;
import com.example.my_store__pet_project.enums.OrderStatus;
import com.example.my_store__pet_project.model.Order;
import com.example.my_store__pet_project.model.OrderItem;
import com.example.my_store__pet_project.model.Product;
import com.example.my_store__pet_project.model.Users;
import com.example.my_store__pet_project.repository.OrderItemRepository;
import com.example.my_store__pet_project.repository.OrderRepository;
import com.example.my_store__pet_project.utilities.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Artem Kovalov on 28.02.2023
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UsersService usersService;
    private final Mapper mapper;

    public void changeOrderStatusForUser(String username, Integer orderId, String status) throws EntityNotFoundException {
        Order order = orderRepository.findOrderByUserNameAndOrderId(username, orderId).orElseThrow(EntityNotFoundException::new);
        order.setStatus(OrderStatus.valueOf(status));
    }

    public List<OrderDto> findOrderByUserName(String userName, boolean desc) throws EntityNotFoundException {
        Sort sorting = desc ? Sort.by("dateAdded").descending() : Sort.by("dateAdded").ascending();
        List<Order> orderList = orderRepository.findAllByUserName(userName, sorting).orElseThrow(EntityNotFoundException::new);
        return mapper.collectToDto(orderList, OrderDto.class);
    }

    public void createOrder(String userName, Cart cart) {
        UsersDto usersDto = usersService.findUsersByName(userName);
        Users user = mapper.toEntity(usersDto, Users.class);

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.REGISTERED);
        order.setAmount(cart.totalAmount());
        order.setDateAdded(LocalDate.now());

        orderRepository.save(order);

        List<OrderItem> orderItemList = new ArrayList<>();
        for (Map.Entry<ProductDto, Integer> entry : cart.entrySet()) {
            ProductDto productDto = entry.getKey();
            Product product = mapper.toEntity(productDto, Product.class);
            BigDecimal price = product.getPrice();
            Integer count = entry.getValue();
            BigDecimal cost = price.multiply(BigDecimal.valueOf(count));

            OrderItem orderItem = new OrderItem();
            orderItem.setCount(count);
            orderItem.setCost(cost);
            orderItem.setProduct(product);
            orderItem.setOrder(order);

            orderItemRepository.save(orderItem);
            orderItemList.add(orderItem);
        }
        order.setOrderItemList(orderItemList);

        OrderDto orderDto = mapper.toDto(order, OrderDto.class);
        log.info("ORDER ADDED: {}", orderDto);
    }
}
