package com.kevin.courserefactor.base.resources;

import com.kevin.courserefactor.base.domain.Category;
import com.kevin.courserefactor.base.dto.form.CategoryForm;
import com.kevin.courserefactor.base.dto.view.CategoryView;
import com.kevin.courserefactor.base.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("categories")
public class CategoryResource {

    @Autowired
    private CategoryService service;


    @GetMapping(value = "/page")
    public ResponseEntity<Page<CategoryView>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")  Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name")  String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")  String direction) {
        Page<Category> list = service.findPage(page, linesPerPage, orderBy, direction);
        Page<CategoryView> listDto = list
                .map(obj -> new CategoryView(obj));
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping
    public ResponseEntity<List<CategoryView>> findAll() {
        List<Category> list = service.findAll();
        List<CategoryView> listDto = list
                .stream()
                .map(obj -> new CategoryView(obj))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> find(@PathVariable Integer id) {
        Category obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoryForm objDto) {
        Category obj = service.fromDto(objDto);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody CategoryForm objDto, @PathVariable Integer id) {
        Category obj = service.fromDto(objDto);
        obj.setId(id);
        service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
