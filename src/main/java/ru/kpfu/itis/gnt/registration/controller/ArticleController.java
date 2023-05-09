package ru.kpfu.itis.gnt.registration.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.gnt.registration.constants.PathConstants;
import ru.kpfu.itis.gnt.registration.constants.ViewNameConstants;
import ru.kpfu.itis.gnt.registration.dto.ArticleDto;
import ru.kpfu.itis.gnt.registration.dto.CategoryDto;
import ru.kpfu.itis.gnt.registration.services.ArticleService;
import ru.kpfu.itis.gnt.registration.services.CategoryService;


@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final CategoryService categoryService;

    @GetMapping(PathConstants.Article.LIST)
    public String getArticles(ModelMap map,
                              @RequestParam(
                                      value = "page_size",
                                      required = false,
                                      defaultValue = "5"
                              ) int pageSize,
                              @RequestParam(
                                      value = "page_number",
                                      required = false,
                                      defaultValue = "1"
                              ) int pageNumber
    ) {
        map.put("articles", articleService.getArticles(pageNumber > 0 ? pageNumber : 1, pageSize, pageNumber));
        map.put("pageCount", articleService.getAmountOfPages(pageSize));
        map.put("selectedPage", pageNumber);
        map.put("category", new CategoryDto());
        return ViewNameConstants.Article.LIST;
    }

    @GetMapping(PathConstants.Article.SINGLE + "/{slug}")
    public String editArticle(ModelMap map,
                              @PathVariable("slug") String slug
    ) {
        ArticleDto article = articleService.getArticleBySlug(slug);
        map.put("articleDto", article);
        map.put("categories", categoryService.getAllCategories());
        return ViewNameConstants.Article.SINGLE;
    }

    @PostMapping(PathConstants.Article.DELETE + "/{id}")
    public String deleteArticleById(@PathVariable int id) {
        if (articleService.deleteArticle(id)) {
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("AC#getArticles").build();
        } else {
            return ViewNameConstants.Article.CREATE;
        }
    }

    @PostMapping(PathConstants.Article.SINGLE + "/{id}")
    public String update(@PathVariable int id,
                         RedirectAttributes redirectAttributes,
                         @Valid @ModelAttribute("articleDto") ArticleDto articleDto,
                         BindingResult result) {
        if (result.hasErrors()) {
            return ViewNameConstants.Article.SINGLE;
        } else {
            articleService.updateArticle(articleDto);
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("AC#getArticles").arg(0, articleDto.getSlug()).build();
        }
    }

    @GetMapping(PathConstants.Article.CREATE)
    public String newArticle(ModelMap map) {
        map.put("articleDto", new ArticleDto());
        return ViewNameConstants.Article.CREATE;
    }

    @PostMapping(PathConstants.Article.CREATE)
    public String newArticleValidator(@Valid @ModelAttribute("articleDto") ArticleDto article,
                                      BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            return ViewNameConstants.Article.CREATE;
        } else {
            articleService.save(article);
            return "redirect:" + MvcUriComponentsBuilder.fromMappingName("AC#getArticles").build();
        }

    }
}
