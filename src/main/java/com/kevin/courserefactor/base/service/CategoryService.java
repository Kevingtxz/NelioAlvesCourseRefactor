package com.kevin.courserefactor.base.service;

import com.kevin.courserefactor.base.domain.Category;
import com.kevin.courserefactor.base.dto.form.CategoryForm;
import com.kevin.courserefactor.base.repository.CategoryRepository;
import com.kevin.courserefactor.base.service.exceptions.DataIntegrityException;
import com.kevin.courserefactor.base.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;


    public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public List<Category> findAll() {
        return repo.findAll();
    }

    public Category find(Integer id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Object not found. ID: " + id
                        + ", Type: " + Category.class.getName()));
    }

    public Category insert(Category obj) {
        return repo.save(obj);
    }

    public void update(Category updObj) {
        Category obj = this.find(updObj.getId());
        this.updateData(obj, updObj);
        repo.save(obj);
    }

    public void delete(Integer id) {
        this.find(id);
        try {
            repo.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("It's impossible to delete a category with products");
        }
    }

    public Category fromDto(CategoryForm objDto) {
        return new Category(null, objDto.getName());
    }

    private void updateData(Category obj, Category updObj) {
        obj.setName(updObj.getName());
    }
}
