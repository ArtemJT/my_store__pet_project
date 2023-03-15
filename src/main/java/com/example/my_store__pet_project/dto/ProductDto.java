package com.example.my_store__pet_project.dto;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Integer productId;

    private String model;

    private BigDecimal price;

    @EqualsAndHashCode.Exclude
    private LocalDate dateAdded;

    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    private String imageLink;

    private StockStatusDto stockStatusDto;

    private CategoryDto categoryDto;

    private ProductDetailsDto productDetailsDto;

    public void setImageLink(String image, DbxClientV2 dbxClientV2) {
        try {
            this.imageLink = dbxClientV2.files().getTemporaryLink(image).getLink();
        } catch (DbxException e) {
            e.printStackTrace();
        }
    }
}
