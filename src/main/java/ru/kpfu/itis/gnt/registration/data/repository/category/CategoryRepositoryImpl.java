package ru.kpfu.itis.gnt.registration.data.repository.category;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.gnt.registration.entity.ArticleEntity;
import ru.kpfu.itis.gnt.registration.entity.CategoryEntity;
import ru.kpfu.itis.gnt.registration.exceptions.DBException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository<CategoryEntity> {

    private final EntityManager entityManager;

    @Override
    public List<CategoryEntity> getAll() throws DBException {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<CategoryEntity> criteriaQuery = criteriaBuilder
                .createQuery(CategoryEntity.class);

        Root<CategoryEntity> from = criteriaQuery.from(CategoryEntity.class);

        CriteriaQuery<CategoryEntity> select = criteriaQuery.select(from);

        TypedQuery<CategoryEntity> typedQuery = entityManager.createQuery(select);

        return typedQuery.getResultList();
    }

    @Override
    public void createNew(CategoryEntity entity) throws DBException {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public Optional<CategoryEntity> findById(int id) throws DBException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CategoryEntity> criteriaQuery = criteriaBuilder.createQuery(CategoryEntity.class);
        Root<CategoryEntity> root = criteriaQuery.from(CategoryEntity.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
        TypedQuery<CategoryEntity> query = entityManager.createQuery(criteriaQuery);
        return Optional.ofNullable(query.getSingleResult());
    }
}
