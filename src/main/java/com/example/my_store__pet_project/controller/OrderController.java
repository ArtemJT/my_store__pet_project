package com.example.my_store__pet_project.controller;

import com.example.my_store__pet_project.dto.Cart;
import com.example.my_store__pet_project.dto.OrderDto;
import com.example.my_store__pet_project.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String orders(@RequestParam(defaultValue = "true") boolean desc, Model model) {
        log.info("Orders called");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        List<OrderDto> orderDtoList = null;
        try {
            orderDtoList = orderService.findOrderByUserName(userName, desc);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        model.addAttribute("orders", orderDtoList);
        return "orders";
    }

    @PostMapping
    public String createOrder(HttpServletRequest request) {
        log.info("Creating order");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        orderService.createOrder(userName, cart);
        cart.clear();
        return "redirect:/orders";
    }
}
