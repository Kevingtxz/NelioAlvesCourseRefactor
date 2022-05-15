package com.kevin.courserefactor.base.dto.view;

import com.kevin.courserefactor.base.domain.ProductEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductView {

    private Integer id;
    private String name;
    private Double price;

    public ProductView(ProductEntity product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
    }
}
