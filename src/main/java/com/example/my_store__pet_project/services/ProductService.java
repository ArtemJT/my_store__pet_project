package com.example.my_store__pet_project.services;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.example.my_store__pet_project.dto.CategoryDto;
import com.example.my_store__pet_project.dto.ProductDetailsDto;
import com.example.my_store__pet_project.dto.ProductDto;
import com.example.my_store__pet_project.dto.StockStatusDto;
import com.example.my_store__pet_project.model.Category;
import com.example.my_store__pet_project.model.Product;
import com.example.my_store__pet_project.model.ProductDetails;
import com.example.my_store__pet_project.model.StockStatus;
import com.example.my_store__pet_project.repository.CategoryRepository;
import com.example.my_store__pet_project.repository.ProductDetailsRepository;
import com.example.my_store__pet_project.repository.ProductRepository;
import com.example.my_store__pet_project.repository.StockStatusRepository;
import com.example.my_store__pet_project.utilities.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final StockStatusRepository stockStatusRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final Mapper mapper;
    private final DbxClientV2 dbxClientV2;

    @Value("${upload.path}")
    private String uploadPath;

    public Page<ProductDto> findAll(Pageable pageable) {
        return mapper.mapPageProductToDto(productRepository.findAll(pageable), dbxClientV2);
    }

    public ProductDto findById(Integer id) throws EntityNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapper.productToDtoMapper(product, dbxClientV2);
    }

    public ProductDto deleteById(Integer id) throws EntityNotFoundException, IOException, DbxException {
        ProductDto productDto = null;
        Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (product != null) {
            productDto = mapper.productToDtoMapper(product, dbxClientV2);
            productRepository.removeProductById(id);
            dbxClientV2.files().deleteV2(product.getImage());
            log.info("");
        }
        return productDto;
    }

    public ProductDto addProduct(CategoryDto categoryDto, StockStatusDto stockStatusDto,
                                 ProductDetailsDto productDetailsDto, ProductDto productDto, MultipartFile file) throws IOException, DbxException {

        productDto.setDateAdded(LocalDate.now());

        Product product = mapper.toEntity(productDto, Product.class);

        categoryDto = setCategoryById(product, categoryDto);
        productDto.setCategoryDto(categoryDto);

        String newFile;
        try {
            newFile = uploadFileToDbx(file, categoryDto);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }

        product.setImage(newFile);

        productDto.setStockStatusDto(setStockStatusById(product, stockStatusDto));

        productRepository.save(product);

        productDto.setProductDetailsDto(setProductDetails(product, productDetailsDto));
        productDto.setProductId(product.getProductId());
        productDto.setImageLink(newFile, dbxClientV2);
        log.info("");
        return productDto;
    }

    public String uploadFileToDbx(MultipartFile file, CategoryDto categoryDto) throws DbxException, IOException {
        String categoryName = categoryDto.getName();
        String originalFilename = file.getOriginalFilename();
        String uploadingFilePath = uploadPath + categoryName + '/' + originalFilename;

        InputStream fis = file.getInputStream();
        dbxClientV2.files().uploadBuilder(uploadingFilePath).uploadAndFinish(fis);
        return uploadingFilePath;
    }

    public boolean isProductExists(String productName) {
        return productRepository.existsProductByModel(productName);
    }

    public List<CategoryDto> findAllCategories() {
        return mapper.iterableToDto(categoryRepository.findAll(), CategoryDto.class);
    }

    public List<StockStatusDto> findAllStockStatuses() {
        return mapper.iterableToDto(stockStatusRepository.findAll(), StockStatusDto.class);
    }

    protected CategoryDto setCategoryById(Product product, CategoryDto categoryDto) throws EntityNotFoundException {
        Integer id = categoryDto.getCategoryId();
        Category category = categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        product.setCategory(category);
        return mapper.toDto(category, CategoryDto.class);
    }

    protected StockStatusDto setStockStatusById(Product product, StockStatusDto stockStatusDto) throws EntityNotFoundException {
        Integer id = stockStatusDto.getStatusId();
        StockStatus stockStatus = stockStatusRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        product.setStockStatus(stockStatus);
        return mapper.toDto(stockStatus, StockStatusDto.class);
    }

    protected ProductDetailsDto setProductDetails(Product product, ProductDetailsDto productDetailsDto) throws EntityNotFoundException {
        ProductDetails productDetails = mapper.toEntity(productDetailsDto, ProductDetails.class);
        productDetails.setProduct(product);
        productDetailsRepository.save(productDetails);

        productDetailsDto = mapper.toDto(productDetails, ProductDetailsDto.class);
        productDetailsDto.setId(productDetails.getId());
        return productDetailsDto;
    }
}
