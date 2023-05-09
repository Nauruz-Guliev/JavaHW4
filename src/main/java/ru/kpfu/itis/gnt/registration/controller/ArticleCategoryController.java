package ru.kpfu.itis.gnt.registration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kpfu.itis.gnt.registration.constants.ViewNameConstants;
import ru.kpfu.itis.gnt.registration.services.ArticleCategoryService;

@Controller
@RequiredArgsConstructor
public class ArticleCategoryController {

    private final ArticleCategoryService articleCategoryService;

    @PostMapping(value = "/new_category")
    public @ResponseBody String handleRequest(@RequestParam(name = "category_id") int categoryId,
                                              @RequestParam(name = "article_id") int articleId) {
        articleCategoryService.createRelation(articleId, categoryId);
        return ViewNameConstants.Article.SINGLE;
    }

}
