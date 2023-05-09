package ru.kpfu.itis.gnt.registration.mapper;

import org.mapstruct.Mapper;
import ru.kpfu.itis.gnt.registration.dto.ArticleDto;
import ru.kpfu.itis.gnt.registration.dto.CategoryDto;
import ru.kpfu.itis.gnt.registration.entity.ArticleEntity;
import ru.kpfu.itis.gnt.registration.entity.CategoryEntity;
import ru.kpfu.itis.gnt.registration.services.ArticleService;
@Mapper(componentModel = "spring", uses = ArticleService.class)
public interface CategoryMapper {
    CategoryEntity articleDtoToArticleEntity(CategoryDto dto);
    CategoryDto articleEntityToArticleDto(CategoryEntity entity);
}



