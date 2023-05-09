package ru.kpfu.itis.gnt.registration.data.repository.articlecategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.gnt.registration.exceptions.DBException;
import ru.kpfu.itis.gnt.registration.utils.enums.ErrorEnum;

import javax.sql.DataSource;

@Repository
public class ArticleCategoryRepositoryImpl implements ArticleCategoryRepository {

    //language=SQL
    private static final String SQL_CATEGORY_ARTICLE_RELATION = "INSERT INTO article_category(article_id, category_id) VALUES (?, ?)";

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public ArticleCategoryRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public void createRelation(int categoryId, int articleId) {
        try {
            jdbcTemplate.update(
                    SQL_CATEGORY_ARTICLE_RELATION,
                    articleId,
                    categoryId
            );
        } catch (DuplicateKeyException ex) {
            throw new DBException(ErrorEnum.DUPLICATE_USER_FIELDS_EXCEPTION.toString());
        } catch (Exception ex) {
            throw new DBException(ErrorEnum.UNKNOWN_DB_EXCEPTION.toString());
        }
    }
}
