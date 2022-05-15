package com.kevin.courserefactor.base.resources;

import com.kevin.courserefactor.base.domain.Category;
import com.kevin.courserefactor.base.domain.OrderEntity;
import com.kevin.courserefactor.base.dto.view.CategoryView;
import com.kevin.courserefactor.base.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("orders")
public class OrderResource {

    @Autowired
    private OrderService service;


    @GetMapping
    public ResponseEntity<Page<OrderEntity>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")  Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "instant")  String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC")  String direction) {
        Page<OrderEntity> list = service.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderEntity> find(@PathVariable Integer id) {
        OrderEntity obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody OrderEntity obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
