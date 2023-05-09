package ru.kpfu.itis.gnt.registration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.gnt.registration.data.repository.article.ArticleRepositoryImpl;
import ru.kpfu.itis.gnt.registration.data.repository.category.CategoryRepository;
import ru.kpfu.itis.gnt.registration.data.repository.category.CategoryRepositoryImpl;
import ru.kpfu.itis.gnt.registration.dto.CategoryDto;
import ru.kpfu.itis.gnt.registration.mapper.ArticleMapper;
import ru.kpfu.itis.gnt.registration.mapper.CategoryMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepositoryImpl repository;
    private final CategoryMapper articleMapper;

    public List<CategoryDto> getAllCategories() {
        return repository.getAll().stream().map(articleMapper::articleEntityToArticleDto).collect(Collectors.toList());
    }
}
