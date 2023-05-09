package ru.kpfu.itis.gnt.registration.data.repository.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.gnt.registration.entity.ArticleEntity;
import ru.kpfu.itis.gnt.registration.exceptions.DBException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Repository
@Transactional
public class ArticleRepositoryImpl implements ArticleRepository {

    private final EntityManager entityManager;


    @Autowired
    public ArticleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ArticleEntity> findAll(int pageNumber, int pageSize) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<ArticleEntity> criteriaQuery = criteriaBuilder
                    .createQuery(ArticleEntity.class);
            Root<ArticleEntity> from = criteriaQuery.from(ArticleEntity.class);
            CriteriaQuery<ArticleEntity> select = criteriaQuery.select(from);
            TypedQuery<ArticleEntity> typedQuery = entityManager.createQuery(select);
            typedQuery.setFirstResult((pageNumber - 1) * pageSize);
            typedQuery.setMaxResults(pageSize);
            return typedQuery.getResultList();
        } catch (Exception ex) {
            throw new DBException("Failed to get articles");
        }
    }

    public ArticleEntity getArticleById(int id) {
        try {
            return getArticle("id", id).get(0);
        } catch (Exception ex) {
            throw new DBException("Article not found");
        }
    }


    public Long getAmountOfPages(int pageSize) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> countQuery = criteriaBuilder
                    .createQuery(Long.class);
            countQuery.select(criteriaBuilder.count(countQuery.from(ArticleEntity.class)));
            return (long) Math.ceil((double) entityManager.createQuery(countQuery).getSingleResult() / pageSize) + 1;
        } catch (Exception e) {
            throw new DBException("Failed to get page count");
        }
    }


    @Transactional
    @Override
    public void saveArticle(ArticleEntity articleEntity) {
        try {
            entityManager.joinTransaction();
            entityManager.getTransaction().begin();
            entityManager.persist(articleEntity);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            throw new DBException("Unable to save the article");
        }
    }

    public boolean slugExists(String slug) {
        return getArticle("slug", slug).size() > 0;
    }



    @Transactional
    public void updateArticle(ArticleEntity entity) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            CriteriaUpdate<ArticleEntity> update = cb.createCriteriaUpdate(ArticleEntity.class);
            Root<ArticleEntity> root = update.from(ArticleEntity.class);
            update.set(root.get("text"), entity.getText());
            update.set(root.get("title"), entity.getTitle());
            Predicate condition = cb.equal(root.get("id"), entity.getId());
            update.where(condition);
            entityManager.createQuery(update).executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            throw new DBException("Failed to update the article.");
        }
    }

    @Override
    @Transactional
    public boolean deleteArticle(int id) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            CriteriaDelete<ArticleEntity> delete = cb.createCriteriaDelete(ArticleEntity.class);
            Root<ArticleEntity> root = delete.from(ArticleEntity.class);
            Predicate condition = cb.equal(root.get("id"), id);
            delete.where(condition);
            int result = entityManager.createQuery(delete).executeUpdate();
            transaction.commit();
            return result > 0;
        } catch (Exception ex) {
            throw new DBException("Failed to delete the article");
        }
    }

    public ArticleEntity getArticleBySlug(String slug) {
        try {
            return getArticle("slug", slug).get(0);
        } catch (Exception ex) {
            throw new DBException("Article not found");
        }
    }

    private List<ArticleEntity> getArticle(String key, Object value) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ArticleEntity> dataQuery = cb.createQuery(ArticleEntity.class);
            Root<ArticleEntity> article = dataQuery.from(ArticleEntity.class);
            dataQuery.select(article);
            List<Predicate> criteria = new ArrayList<>();
            criteria.add(cb.equal(article.get(key), value));
            Predicate[] predicates = new Predicate[criteria.size()];
            predicates = criteria.toArray(predicates);
            dataQuery.where(cb.and(predicates));
            return entityManager.createQuery(dataQuery).getResultList();
        } catch (Exception ex) {
            throw new DBException("Article not found");
        }
    }
}
