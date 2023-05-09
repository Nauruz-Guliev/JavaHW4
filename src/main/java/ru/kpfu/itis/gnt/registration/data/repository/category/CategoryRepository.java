package ru.kpfu.itis.gnt.registration.data.repository.category;

import org.springframework.stereotype.Repository;
import ru.kpfu.itis.gnt.registration.entity.CategoryEntity;
import ru.kpfu.itis.gnt.registration.exceptions.DBException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository <T>{
    List<T> getAll() throws DBException;

    void createNew(CategoryEntity entity) throws DBException;

    Optional<T> findById(int id) throws DBException;
}
