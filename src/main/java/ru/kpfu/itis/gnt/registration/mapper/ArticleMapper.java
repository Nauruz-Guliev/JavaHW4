package ru.kpfu.itis.gnt.registration.mapper;

import org.mapstruct.Mapper;
import ru.kpfu.itis.gnt.registration.dto.ArticleDto;
import ru.kpfu.itis.gnt.registration.dto.UserDto;
import ru.kpfu.itis.gnt.registration.entity.ArticleEntity;
import ru.kpfu.itis.gnt.registration.entity.UserEntity;
import ru.kpfu.itis.gnt.registration.services.ArticleService;

@Mapper(componentModel = "spring", uses = ArticleService.class)
public interface ArticleMapper {

    ArticleEntity articleDtoToArticleEntity(ArticleDto dto);

    ArticleDto articleEntityToArticleDto(ArticleEntity entity);
}