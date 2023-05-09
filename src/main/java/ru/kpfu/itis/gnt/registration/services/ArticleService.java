package ru.kpfu.itis.gnt.registration.services;

import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.gnt.registration.data.repository.article.ArticleRepositoryImpl;
import ru.kpfu.itis.gnt.registration.dto.ArticleDto;
import ru.kpfu.itis.gnt.registration.entity.ArticleEntity;
import ru.kpfu.itis.gnt.registration.exceptions.DuplicateResultException;
import ru.kpfu.itis.gnt.registration.mapper.ArticleMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepositoryImpl repository;
    private final ArticleMapper articleMapper;
    private final Slugify slugify;


    public List<ArticleDto> getArticles(int pageNumber, int pageSize, int page) {
        return repository
                .findAll(pageNumber, pageSize)
                .stream()
                .map(articleMapper::articleEntityToArticleDto)
                .collect(Collectors.toList());
    }

    public ArticleDto getArticleById(int id) {
        return articleMapper.articleEntityToArticleDto(repository.getArticleById(id));
    }

    public ArticleDto getArticleBySlug(String slug) {
        return articleMapper.articleEntityToArticleDto(repository.getArticleBySlug(slug));
    }

    public boolean slugExists(String slug) {
        return repository.slugExists(slug);
    }

    public Long getAmountOfPages(int pageSize) {

        return repository.getAmountOfPages(pageSize);
    }

    public void save(ArticleDto articleDto) {
        articleDto.setText(Jsoup.clean(articleDto.getText(), Safelist.basic()));
        ArticleEntity entity = articleMapper.articleDtoToArticleEntity(articleDto);
        entity.setSlug(slugify.slugify(articleDto.getTitle()));
        if (repository.slugExists(entity.getSlug())) {
            throw new DuplicateResultException("Article with such a title already exists!");
        } else {
            repository.saveArticle(entity);
        }

    }

    public boolean deleteArticle(int id) {
        return repository.deleteArticle(id);
    }


    public void updateArticle(ArticleDto articleDto) {
        repository.updateArticle(articleMapper.articleDtoToArticleEntity(articleDto));
    }

}
