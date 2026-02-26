package ua.com.foxmined.carrestservice.service.categoryservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.exception.EntityNotPresentException;
import ua.com.foxmined.carrestservice.model.CarCategory;
import ua.com.foxmined.carrestservice.service.DAOInterface;

import java.util.List;

@Service
public interface CarCategoryService extends DAOInterface<CarCategory> {

    List<CarCategory> findByName(String name);

    CarCategory addCategory(String category);

    CarCategory updateCategory(String oldCategory, String newCategory) throws EntityNotPresentException;

    void deleteCategory(String category) throws EntityNotPresentException;

}
