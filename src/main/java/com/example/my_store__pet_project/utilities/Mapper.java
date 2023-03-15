package com.example.my_store__pet_project.utilities;

import com.dropbox.core.v2.DbxClientV2;
import com.example.my_store__pet_project.dto.CategoryDto;
import com.example.my_store__pet_project.dto.ProductDetailsDto;
import com.example.my_store__pet_project.dto.ProductDto;
import com.example.my_store__pet_project.dto.StockStatusDto;
import com.example.my_store__pet_project.model.Product;
import com.google.common.collect.Streams;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public final class Mapper {

    private final ModelMapper modelMapper;

    public <E, D> D toDto(@NonNull final E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public ProductDto productToDtoMapper(@NonNull final Product entity, DbxClientV2 dbxClientV2) {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(entity.getProductId());
        productDto.setModel(entity.getModel());
        productDto.setPrice(entity.getPrice());
        productDto.setDateAdded(entity.getDateAdded());
        productDto.setImageLink(entity.getImage(), dbxClientV2);
        productDto.setStockStatusDto(toDto(entity.getStockStatus(), StockStatusDto.class));
        productDto.setCategoryDto(toDto(entity.getCategory(), CategoryDto.class));
        productDto.setProductDetailsDto(toDto(entity.getProductDetails(), ProductDetailsDto.class));
        return productDto;
    }

    public Page<ProductDto> mapPageProductToDto(@NonNull final Page<Product> entityPage, DbxClientV2 dbxClientV2) {
        return entityPage.map(product -> productToDtoMapper(product, dbxClientV2));
    }

    public <E, D> List<D> collectToDto(@NonNull final Collection<E> entityCollection, Class<D> dtoClass) {
        return entityCollection.stream()
                .map(e -> modelMapper.map(e, dtoClass))
                .toList();
    }

    public <E, D> List<D> iterableToDto(@NonNull final Iterable<E> entityCollection, Class<D> dtoClass) {
        return Streams.stream(entityCollection)
                .map(e -> modelMapper.map(e, dtoClass))
                .toList();
    }

    public <E, D> E toEntity(@NonNull final D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }
}
