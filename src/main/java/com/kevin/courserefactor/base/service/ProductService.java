package com.kevin.courserefactor.base.service;

import com.kevin.courserefactor.base.domain.Category;
import com.kevin.courserefactor.base.domain.ProductEntity;
import com.kevin.courserefactor.base.repository.CategoryRepository;
import com.kevin.courserefactor.base.repository.ProductRepository;
import com.kevin.courserefactor.base.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private CategoryRepository categoryRepository;


    public Page<ProductEntity> search(
            String name, List<Integer> categoriesIds,
            Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Category> categories = categoryRepository.findAllById(categoriesIds);
        return repo.findDistinctByNameContainingAndCategoriesIn(name, categories, pageRequest);

    }

    public ProductEntity find(Integer id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Object not found. ID: " + id
                        + ", Type: " + ProductEntity.class.getName()));
    }
}
