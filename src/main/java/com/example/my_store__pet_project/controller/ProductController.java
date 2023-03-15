package com.example.my_store__pet_project.controller;

import com.dropbox.core.DbxException;
import com.example.my_store__pet_project.dto.*;
import com.example.my_store__pet_project.services.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final Cart cart;

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "dateAdded") String sort,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "10") int size,
                         @RequestParam(defaultValue = "true") boolean desc,
                         Model model) {
        Sort sorting = desc ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sorting);
        Page<ProductDto> productDtoPage = productService.findAll(pageable);

        List<ProductDto> productDtoList = productDtoPage.toList();
        long totalItems = productDtoPage.getTotalElements();
        int totalPages = productDtoPage.getTotalPages();

        log.info("Products were send");

        model.addAttribute("allProducts", productDtoList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        model.addAttribute("desc", desc);
        return "products";
    }

    @PostMapping
    public String addToCart(@RequestParam Integer id, HttpSession session, Model model) {
        log.info("Adding to cart");
        ProductDto productDto = productService.findById(id);
        cart.put(productDto, 1);
        session.setAttribute("cart", cart);
        model.addAttribute("cart", cart);
        return "redirect:/products";
    }

    @GetMapping("addProduct")
    public String addProduct(Model model) {
        log.info("product add page");
        List<CategoryDto> allCategories = productService.findAllCategories();
        List<StockStatusDto> allStatuses = productService.findAllStockStatuses();
        model.addAttribute("categories", allCategories);
        model.addAttribute("statuses", allStatuses);
        model.addAttribute("categoryDto", new CategoryDto());
        model.addAttribute("stockStatusDto", new StockStatusDto());
        model.addAttribute("productDetailsDto", new ProductDetailsDto());
        model.addAttribute("productDto", new ProductDto());
        log.info("");
        return "addProduct";
    }

    @PostMapping("addProduct")
    public String addProduct(
            RedirectAttributes redirectAttributes,
            @ModelAttribute CategoryDto categoryDto,
            @ModelAttribute StockStatusDto stockStatusDto,
            @ModelAttribute ProductDetailsDto productDetailsDto,
            @ModelAttribute ProductDto productDto,
            @RequestParam MultipartFile file) {
        log.info("product add post page");

        String message;
        String productDtoModel = productDto.getModel();
        if (categoryDto.getCategoryId() == null || stockStatusDto.getStatusId() == null) {
            message = "select";
        } else if (productDtoModel.equals("")) {
            message = "model";
        } else if (productService.isProductExists(productDtoModel)) {
            message = "exists";
        } else if (productDetailsDto.getName().equals("")) {
            message = "details";
        } else if (file.isEmpty() || !Objects.requireNonNull(file.getContentType()).contains("image")) {
            message = "file";
        } else {
            try {
                productDto = productService.addProduct(categoryDto, stockStatusDto, productDetailsDto, productDto, file);
                redirectAttributes.addFlashAttribute("newProductDto", productDto);
                message = "added";
            } catch (IOException | DbxException e) {
                e.printStackTrace();
                message = "exception";
            }
        }
        redirectAttributes.addFlashAttribute("message", message);
        log.info("");
        return "redirect:/products/addProduct";
    }

    @PostMapping("remove")
    public String removeProduct(RedirectAttributes redirectAttributes, @RequestParam Integer id) {
        log.info("product remove page");
        String removeMsg;
        try {
            ProductDto productDto = productService.deleteById(id);
            if (productDto != null) {
                removeMsg = "removed";
                redirectAttributes.addFlashAttribute("model", productDto.getModel());
            } else {
                removeMsg = "not_found";
            }
        } catch (IOException | DbxException e) {
            e.printStackTrace();
            removeMsg = "exception";
        }
        redirectAttributes.addFlashAttribute("removeMsg", removeMsg);
        return "redirect:/products";
    }
}
