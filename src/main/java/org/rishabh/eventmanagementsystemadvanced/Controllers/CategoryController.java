package org.rishabh.eventmanagementsystemadvanced.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ApisResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CategoryDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.CategoryRequest;
import org.rishabh.eventmanagementsystemadvanced.Services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService  categoryService;


    @PostMapping("/create")
    public ResponseEntity<CategoryDto>createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryDto category = categoryService.createCategory(categoryRequest);
        return new  ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto>updateCategory(@Valid @PathVariable Long categoryId  , @RequestBody CategoryRequest categoryRequest) {
        CategoryDto category = categoryService.updateCategory(categoryId, categoryRequest);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/get/{categoryId}")
    public ResponseEntity<CategoryDto>getCategory(@Valid @PathVariable Long categoryId) {
        CategoryDto category = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/delete/{categoryId}")
    public  ResponseEntity<ApisResponse>deleteCategory(@Valid @PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(new ApisResponse("Category deleted successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<List<CategoryDto>>getAllCategories() {
        List<CategoryDto> allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }

}
