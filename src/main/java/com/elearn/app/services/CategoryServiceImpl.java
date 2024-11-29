package com.elearn.app.services;

import com.elearn.app.dtos.CategoryDto;
import com.elearn.app.dtos.CustomPageResponse;
import com.elearn.app.entities.Category;
import com.elearn.app.exceptions.ResourceNotFoundException;
import com.elearn.app.repositories.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepo categoryRepo;

    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepo categoryRepo, ModelMapper modelMapper) {
        this.categoryRepo = categoryRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto insert(CategoryDto categoryDto) {
        //id
        String catId = UUID.randomUUID().toString();
        categoryDto.setId(catId);
        //date
        categoryDto.setAddedDate(new Date());
        //convert DTO to entity
        Category category = modelMapper.map(categoryDto, Category.class);

        Category savedCategory = categoryRepo.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CustomPageResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy) {

        Sort sort = Sort.by(sortBy).ascending();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Category> categoryPage = categoryRepo.findAll(pageRequest);

        List<Category> all = categoryPage.getContent();

        List<CategoryDto> categoryDtoList = all.stream().map(category -> modelMapper.map(category, CategoryDto.class)).toList();

        CustomPageResponse<CategoryDto> customPageResponse = new CustomPageResponse<CategoryDto>();
        customPageResponse.setContent(categoryDtoList);
        customPageResponse.setLast(categoryPage.isLast());
        customPageResponse.setTotalElements(categoryPage.getTotalElements());
        customPageResponse.setTotalPages(categoryPage.getTotalPages());
        customPageResponse.setPageNumber(pageNumber);
        customPageResponse.setPageSize(pageSize);

        return customPageResponse;
    }

    @Override
    public CategoryDto get(String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found !!"));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found !!"));
        categoryRepo.delete(category);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found !!"));
        category.setTitle(categoryDto.getTitle());
        category.setDesc(categoryDto.getDesc());
        Category savedCategory = categoryRepo.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }
}