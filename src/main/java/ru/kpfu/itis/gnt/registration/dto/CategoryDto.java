package ru.kpfu.itis.gnt.registration.dto;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private long id;
    @Size(min = 4, max = 80)
    private String name;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

