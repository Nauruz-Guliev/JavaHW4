package ru.kpfu.itis.gnt.registration.data.repository.article;

import ru.kpfu.itis.gnt.registration.entity.ArticleEntity;

import java.util.List;

interface ArticleRepository {
    void saveArticle(ArticleEntity articleEntity);
    ArticleEntity getArticleById(int id);
    List<ArticleEntity> findAll(int pageNumber, int pageSize);
    void updateArticle(ArticleEntity entity);
    boolean deleteArticle(int id);

}
