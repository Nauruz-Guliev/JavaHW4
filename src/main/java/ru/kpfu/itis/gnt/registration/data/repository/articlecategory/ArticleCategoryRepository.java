package ru.kpfu.itis.gnt.registration.data.repository.articlecategory;

public interface ArticleCategoryRepository {

    void createRelation(int categoryId, int articleId);
}
