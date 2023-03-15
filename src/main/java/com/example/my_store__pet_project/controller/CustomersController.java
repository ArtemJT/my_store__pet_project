package com.example.my_store__pet_project.controller;

import com.example.my_store__pet_project.dto.OrderDto;
import com.example.my_store__pet_project.dto.UsersDto;
import com.example.my_store__pet_project.services.OrderService;
import com.example.my_store__pet_project.services.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("customers")
@Slf4j
@RequiredArgsConstructor
public class CustomersController {

    private final UsersService usersService;
    private final OrderService orderService;

    @GetMapping
    public String customers(Model model) {
        log.info("Customers called");
        List<UsersDto> allUsers = usersService.findAllUsers();
        model.addAttribute("customers", allUsers);
        log.info("");
        return "customers";
    }

    @GetMapping("orders")
    public String customerOrders(@RequestParam String name, @RequestParam(defaultValue = "true") boolean desc, Model model) {
        log.info("customerOrders called");
        List<OrderDto> orderDtoList = orderService.findOrderByUserName(name, desc);
        model.addAttribute("orders", orderDtoList);
        model.addAttribute("name", name);
        return "customers-orders";
    }

    @PostMapping("orders")
    public String changeOrderStatus(@RequestParam String name,
                                    @RequestParam Integer orderId,
                                    @RequestParam String status) {
        log.info("customerOrders called");
        orderService.changeOrderStatusForUser(name, orderId, status);
        return "redirect:/customers/orders?name=" + name;
    }

    @PostMapping
    public String blockUser(@RequestParam Integer id) {
        log.info("Blocking user");
        usersService.blockUserById(id);
        return "redirect:/customers";
    }
}
