package com.kevin.courserefactor.base.dto.view;

import com.kevin.courserefactor.base.domain.Product;
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

    public ProductView(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
    }
}
