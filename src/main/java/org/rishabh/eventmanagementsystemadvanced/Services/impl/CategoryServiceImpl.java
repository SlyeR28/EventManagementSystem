package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Category;
import org.rishabh.eventmanagementsystemadvanced.Mapper.CategoryMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CategoryDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.CategoryRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.CategoryRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

   private final CategoryRepository categoryRepository;
   private final CategoryMapper  categoryMapper;


    @Override
    public CategoryDto createCategory(CategoryRequest categoryRequest) {
        Category entity = categoryMapper.toEntity(categoryRequest);
        Category saved = categoryRepository.save(entity);
        return categoryMapper.toDto(saved);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Category not found with id " + id));
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        List<Category> all = categoryRepository.findAll();
        return all.stream().map(categoryMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Category not found with id " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Category not found with id " + id));
        categoryRepository.delete(category);
    }
}
