package ru.kpfu.itis.gnt.registration.dto;


import jakarta.validation.constraints.Size;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ArticleDto {

    @Size(max = 100, min= 2)
    private String title;
    @Size(max = 2000, min = 20)
    private String text;
    private Long id;

    private String slug;

    private Set<CategoryDto> categories;

    public Set<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDto> categories) {
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
