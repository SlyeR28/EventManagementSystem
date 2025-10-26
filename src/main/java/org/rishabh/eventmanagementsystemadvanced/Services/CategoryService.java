package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CategoryDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.CategoryRequest;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryRequest categoryRequest);
    CategoryDto updateCategory(Long id  ,CategoryRequest categoryRequest);
    List<CategoryDto> getAllCategories();
    CategoryDto getCategory(Long id);
    void deleteCategory(Long id);

}
