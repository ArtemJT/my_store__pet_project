package com.example.my_store__pet_project.controller;

import com.example.my_store__pet_project.dto.Cart;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("cart")
@Slf4j
@RequiredArgsConstructor
public class CartController {

    @GetMapping
    public String cart(HttpSession session, Model model) {
        log.info("Cart called");
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping
    public String remove(HttpSession session, @RequestParam Integer id) {
        log.info("Removing item from cart");
        Cart cart = (Cart) session.getAttribute("cart");
        cart.removeByProductId(id);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clear(HttpSession session) {
        log.info("Cart clear called");
        Cart cart = (Cart) session.getAttribute("cart");
        cart.clear();
        return "redirect:/cart";
    }
}
