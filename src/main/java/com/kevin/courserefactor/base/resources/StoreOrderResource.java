package com.kevin.courserefactor.base.resources;

import com.kevin.courserefactor.base.domain.StoreOrder;
import com.kevin.courserefactor.base.service.StoreOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("orders")
public class StoreOrderResource {

    @Autowired
    private StoreOrderService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<StoreOrder> find(@PathVariable Integer id) {
        StoreOrder obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody StoreOrder obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
