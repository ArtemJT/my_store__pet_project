package com.example.my_store__pet_project.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Artem Kovalov on 25.02.2023
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SessionScope
@Component
public class Cart extends AbstractMap<ProductDto, Integer> {

    private final Map<ProductDto, Integer> cartItems = new HashMap<>();

    @Override
    public int size() {
        return cartItems.size();
    }

    public BigDecimal totalAmount() {
        BigDecimal amount = BigDecimal.ZERO;
        for (Entry<ProductDto, Integer> entry : cartItems.entrySet()) {
            BigDecimal price = entry.getKey().getPrice();
            Integer count = entry.getValue();
            amount = amount.add(price.multiply(BigDecimal.valueOf(count)));
        }
        return amount;
    }

    public int sizeValues() {
        int size = 0;
        if (!cartItems.isEmpty()) {
            for (Integer value : cartItems.values()) {
                size += value;
            }
        }
        return size;
    }

    public void removeByProductId(Integer id) {
        ProductDto productDto = cartItems.keySet().stream().filter(prod -> prod.getProductId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        remove(productDto);
    }

    @Override
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return cartItems.containsKey(key);
    }

    @Override
    public Integer get(Object key) {
        return cartItems.get(key);
    }

    @Override
    public Integer put(ProductDto key, Integer value) {
        if (cartItems.containsKey(key)) {
            value += cartItems.get(key);
        }
        return cartItems.put(key, value);
    }

    @Override
    public Integer remove(Object key) {
        return cartItems.remove(key);
    }

    @Override
    public void clear() {
        cartItems.clear();
    }

    @Override
    public Set<Entry<ProductDto, Integer>> entrySet() {
        return cartItems.entrySet();
    }
}
