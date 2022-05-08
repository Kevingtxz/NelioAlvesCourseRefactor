package com.kevin.courserefactor.base.resources;

import com.kevin.courserefactor.base.domain.Product;
import com.kevin.courserefactor.base.dto.view.ProductView;
import com.kevin.courserefactor.base.resources.utils.URL;
import com.kevin.courserefactor.base.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductResource {

    @Autowired
    private ProductService service;


    @GetMapping
    public ResponseEntity<Page<ProductView>> search(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "categories", defaultValue = "") String categories,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")  Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name")  String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")  String direction) {
        List<Integer> categoriesIds = URL.decodeIntList(categories);
        String nameDecoded = URL.decodeParam(name);
        Page<Product> list = service.search(
                nameDecoded, categoriesIds,
                page, linesPerPage, orderBy, direction);
        Page<ProductView> listView = list
                .map(obj -> new ProductView(obj));
        return ResponseEntity.ok().body(listView);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> find(@PathVariable Integer id) {
        Product obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }
}
