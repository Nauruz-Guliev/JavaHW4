package ru.kpfu.itis.gnt.registration.entity;

import java.util.Objects;

public class LocaleEntity {
    private Long id;
    private String language;
    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "LocaleEntity{" +
                "id=" + id +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocaleEntity)) return false;
        LocaleEntity that = (LocaleEntity) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getLanguage(), that.getLanguage()) && Objects.equals(getCountry(), that.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLanguage(), getCountry());
    }

    public LocaleEntity(String language, String country) {
        this.language = language;
        this.country = country;
    }
}
