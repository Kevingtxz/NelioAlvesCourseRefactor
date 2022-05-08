package com.kevin.courserefactor.base.dto.view;

import com.kevin.courserefactor.base.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryView {

    private Integer id;
    private String name;


    public CategoryView(Category obj) {
        this.id = obj.getId();
        this.name = obj.getName();
    }
}
