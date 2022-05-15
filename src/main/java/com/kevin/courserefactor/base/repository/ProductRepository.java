package com.kevin.courserefactor.base.repository;

import com.kevin.courserefactor.base.domain.Category;
import com.kevin.courserefactor.base.domain.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

//    @Transactional(readOnly = true)
//    @Query("SELECT DISTINCT obj FROM Product obj INNER JOIN obj.categories cat WHERE obj.name LIKE %:name% AND cat IN :categories")
//    Page<Product> search(@Param("name") String name, @Param("categories") List<Category> categories, Pageable pageRequest);

    @Transactional(readOnly = true)
    Page<ProductEntity> findDistinctByNameContainingAndCategoriesIn(String name, List<Category> categories, Pageable pageRequest);
}
