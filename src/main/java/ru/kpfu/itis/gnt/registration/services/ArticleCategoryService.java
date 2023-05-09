package ru.kpfu.itis.gnt.registration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.gnt.registration.data.repository.articlecategory.ArticleCategoryRepository;

@Service
@RequiredArgsConstructor
public class ArticleCategoryService {

    private final ArticleCategoryRepository repository;

    public void createRelation(int articleId, int categoryId) {
        repository.createRelation(categoryId, articleId);
    }
}
